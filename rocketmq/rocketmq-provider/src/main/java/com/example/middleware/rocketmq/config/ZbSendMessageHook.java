package com.example.middleware.rocketmq.config;

import com.alibaba.rocketmq.client.hook.SendMessageContext;
import com.alibaba.rocketmq.client.hook.SendMessageHook;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.utils.CatJsonUtils;
import com.zb.cloud.logcenter.dubbo.constants.CatDubboConstants;
import com.zb.cloud.logcenter.utils.JsonUtils;
import com.zb.cloud.logcenter.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongjing on 2017/12/28.
 */
public class ZbSendMessageHook implements SendMessageHook {

    /**
     * 一个字节等于1B  1KB = 1024B
     */
    private final int bodyMaxSize = 1024 * 100;

    @Override
    public String hookName() {
        return null;
    }

    @Override
    public void sendMessageBefore(SendMessageContext sendMessageContext) {
        try {
            String transactionName = sendMessageContext.getProducerGroup() + ":" + sendMessageContext.getBrokerAddr() + ":" + sendMessageContext.getMq().getBrokerName();
            Map<String, String> map = new HashMap<>();
            map.put("cat_TransactionName", transactionName);
            Cat.Context context = RocketMqContextHolder.getContext();
            String _rootId = context.getProperty(Cat.Context.ROOT);
            String _messageId = context.getProperty(Cat.Context.CHILD);
            String _parentId = context.getProperty(Cat.Context.PARENT);
            if (StringUtils.isEmpty(_rootId)) {
                Cat.logRemoteCallClient(context);
            } else {
                context.addProperty(Cat.Context.ROOT, _rootId);
                context.addProperty(Cat.Context.PARENT, _parentId);
                context.addProperty(Cat.Context.CHILD, _messageId);
                Cat.logRemoteCallServer(context);
            }
            if (sendMessageContext != null && sendMessageContext.getMessage() != null) {
                Message message = sendMessageContext.getMessage();
                message.putUserProperty(Cat.Context.PARENT, context.getProperty(Cat.Context.PARENT));
                message.putUserProperty(Cat.Context.ROOT, context.getProperty(Cat.Context.ROOT));
                message.putUserProperty(Cat.Context.CHILD, context.getProperty(Cat.Context.CHILD));
            }
            sendMessageContext.setProps(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageAfter(SendMessageContext sendMessageContext) {
        if (sendMessageContext != null && sendMessageContext.getProps() != null && sendMessageContext.getProps().containsKey("cat_TransactionName") && sendMessageContext.getMessage() != null) {
            String transactionName = sendMessageContext.getProps().get("cat_TransactionName");
            Transaction t = null;
            Message message = sendMessageContext.getMessage();
            if (!StringUtils.isEmpty(transactionName)) {
                try {
                    t = Cat.newTransaction("rocketMq", transactionName);
                    Map<String, Object> objectMap = new HashMap<>();
                    String eventTypeName = sendMessageContext.getBornHost();
                    Cat.logEvent("rocketMq.producer", eventTypeName);
                    if (!StringUtils.isEmpty(message.getTopic()) && !StringUtils.isEmpty(message.getTags())) {
                        String topicTags = message.getTopic() + ":" + message.getTags();
                        Cat.logEvent("rocketMq.producer", topicTags);
                    }
                    if (sendMessageContext.getMq() != null) {
                        MessageQueue mq = sendMessageContext.getMq();
                        objectMap.put("queueId", mq.getQueueId());
                        objectMap.put("brokerName", mq.getBrokerName());
                        objectMap.put("topic", mq.getTopic());
                    }
                    objectMap.put("tags", message.getTags());
                    objectMap.put("bornHost", sendMessageContext.getBornHost());
                    objectMap.put("brokerAddr", sendMessageContext.getBrokerAddr());
                    String body = new String(message.getBody());
                    if (body != null && body.length() > bodyMaxSize) {
                        body.substring(0, bodyMaxSize);
                    }
                    objectMap.put("body", body);
                    t.addData(JsonUtils.toJson(objectMap));
                    t.addLogData(CatJsonUtils.getCatInfo("rocketMq.producer").getJson());
                    t.setStatus(Transaction.SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    t.setStatus(e.getClass().getSimpleName());
                } finally {
                    if (t != null) {
                        t.complete();
                    }
                }
            }
        }
    }
}
