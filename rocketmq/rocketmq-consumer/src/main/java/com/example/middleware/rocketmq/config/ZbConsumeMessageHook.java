package com.example.middleware.rocketmq.config;

import com.alibaba.rocketmq.client.hook.ConsumeMessageContext;
import com.alibaba.rocketmq.client.hook.ConsumeMessageHook;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.utils.CatJsonUtils;
import com.zb.cloud.logcenter.utils.JsonUtils;
import com.zb.cloud.logcenter.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongjing on 2017/12/29.
 */
public class ZbConsumeMessageHook implements ConsumeMessageHook {

    /**
     * 一个字节等于1B  1KB = 1024B
     */
    private final int bodyMaxSize = 1024 * 100;

    @Override
    public String hookName() {
        return null;
    }

    @Override
    public void consumeMessageBefore(ConsumeMessageContext consumeMessageContext) {
        System.out.println("i=0");
    }

    @Override
    public void consumeMessageAfter(ConsumeMessageContext consumeMessageContext) {
        if (consumeMessageContext != null && consumeMessageContext.getMsgList() != null && consumeMessageContext.getMsgList().size() > 0) {
            StringBuilder transactionName = new StringBuilder("");
            if (!StringUtils.isEmpty(consumeMessageContext.getConsumerGroup())) {
                transactionName.append(consumeMessageContext.getConsumerGroup());
            }
            for (MessageExt ext : consumeMessageContext.getMsgList()) {
                Transaction t = null;
                try {
                    Map<String, Object> objectMap = new HashMap<>();
                    if (ext.getStoreHost() != null) {
                        transactionName.append(":").append(String.valueOf(ext.getStoreHost()));
                    }
                    MessageQueue messageQueue = consumeMessageContext.getMq();
                    if (messageQueue != null && messageQueue.getBrokerName() != null) {
                        transactionName.append(":").append(messageQueue.getBrokerName());
                        objectMap.put("queueId", String.valueOf(messageQueue.getQueueId()));
                        objectMap.put("msgId", ext.getMsgId());
                        objectMap.put("bornHost", ext.getBornHostString());
                        objectMap.put("storeHost", ext.getStoreHost());
                        objectMap.put("brokerName", messageQueue.getBrokerName());
                    }
                    if (!StringUtils.isEmpty(transactionName.toString())) {
                        t = Cat.newTransaction("rocketMq", String.valueOf(transactionName));
                        String eventTypeName = ext.getBornHostString();
                        Cat.logEvent("rocketMq.consumer ", eventTypeName);
                        if (!StringUtils.isEmpty(messageQueue.getTopic()) && !StringUtils.isEmpty(ext.getTags())) {
                            String topicTags = messageQueue.getTopic() + ":" + ext.getTags();
                            objectMap.put("topic", messageQueue.getTopic());
                            objectMap.put("tags", ext.getTags());
                            Cat.logEvent("rocketMq.consumer ", topicTags);
                        }
                        //获取生产者的rootId，作为消费者的父id
                        Cat.Context context = RocketMqContextHolder.getContext();
                        String _messageId = ext.getProperty(Cat.Context.CHILD);
                        String _rootId = ext.getProperty(Cat.Context.ROOT);
                        String _parentId = ext.getProperty(Cat.Context.PARENT);
                        if(StringUtils.isEmpty(_rootId)){
                            _rootId = context.getProperty(Cat.Context.ROOT);
                            _parentId = context.getProperty(Cat.Context.PARENT);
                            _messageId = context.getProperty(Cat.Context.CHILD);
                        }
                        if (StringUtils.isEmpty(_rootId)) {
                            Cat.logRemoteCallClient(context);
                        } else {
                            context.addProperty(Cat.Context.ROOT, _rootId);
                            context.addProperty(Cat.Context.PARENT, _parentId);
                            context.addProperty(Cat.Context.CHILD, _messageId);
                            Cat.logRemoteCallServer(context);
                        }
                        String body = new String(ext.getBody());
                        if (body != null && body.length() > bodyMaxSize) {
                            body.substring(0, bodyMaxSize);
                        }
                        objectMap.put("body", body);
                        t.addData(JsonUtils.toJson(objectMap));
                        t.addLogData(CatJsonUtils.getCatInfo("rocketMq.consumer").getJson());
                        t.setStatus(Transaction.SUCCESS);
                    }
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
