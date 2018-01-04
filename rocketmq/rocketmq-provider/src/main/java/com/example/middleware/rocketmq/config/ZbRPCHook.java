package com.example.middleware.rocketmq.config;

import com.alibaba.rocketmq.remoting.RPCHook;
import com.alibaba.rocketmq.remoting.protocol.RemotingCommand;
import com.dianping.cat.Cat;
import com.dianping.cat.common.CatInfo;
import com.dianping.cat.common.ContexKeys;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.utils.CatJsonUtils;
import com.zb.cloud.logcenter.dto.CatHttpContext;
import com.zb.cloud.logcenter.dubbo.filter.CatDubboFilter;
import com.zb.cloud.logcenter.redis.CatRedisClient;
import com.zb.cloud.logcenter.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Service
public class ZbRPCHook implements RPCHook {

    private static final ThreadLocal<Cat.Context> CAT_CONTEXT = new ThreadLocal<Cat.Context>();

    @Override
    public void doBeforeRequest(String s, RemotingCommand remotingCommand) {
    }

    @Override
    public void doAfterResponse(String s, RemotingCommand remotingCommand, RemotingCommand remotingCommand1) {
//        HashMap<String, String> map = remotingCommand.getExtFields();
//        if (map != null && map.containsKey("a") && map.containsKey("b") && !StringUtils.isEmpty(s)) {
//            String rocketMqProducer = map.get("a");
//            String topic = map.get("b");
//            String transactionName = rocketMqProducer + ":" + s;
//            String eventTypeName = rocketMqProducer + ":" + topic;
//            Transaction t = Cat.newTransaction("rocketMq.producer", transactionName);
//            try {
//                Cat.Context context = getContext();
//                
//                remotingCommand.addExtField(Cat.Context.ROOT, context.getProperty(Cat.Context.ROOT));
//                remotingCommand.addExtField(Cat.Context.PARENT, context.getProperty(Cat.Context.PARENT));
//                remotingCommand.addExtField(Cat.Context.CHILD, context.getProperty(Cat.Context.CHILD));
//
//                remotingCommand1.addExtField("root","321123");
//
//                Cat.logEvent("rocketMq.producer", eventTypeName);
//                Map<String, Object> objectMap = new HashMap<>();
//                if (remotingCommand1 != null && remotingCommand1.getExtFields() != null && remotingCommand1.getExtFields().size() > 0) {
//                    objectMap.putAll(remotingCommand1.getExtFields());
//                }
//                String body = new String(remotingCommand.getBody());
//                if (body != null && body.length() > 5000) {
//                    body.substring(0, 5000);
//                }
//                objectMap.put("body", body);
//                t.addData(JsonUtils.toJson(objectMap));
//                t.addLogData(CatJsonUtils.getCatInfo("rocketMq.producer").getJson());
//                t.setStatus(Transaction.SUCCESS);
//            } catch (Exception e) {
//                t.setStatus(e.getClass().getSimpleName());
//            } finally {
//                t.complete();
//            }
//        }
    }


    static class CatRocketContext implements Cat.Context {

        private Map<String, String> properties = new HashMap<String, String>();

        @Override
        public void addProperty(String key, String value) {
            properties.put(key, value);
        }

        @Override
        public String getProperty(String key) {
            return properties.get(key);
        }
    }
    
    private Cat.Context getContext() {
        Cat.Context context = CAT_CONTEXT.get();
        if (context == null) {
            context = initContext();
            CAT_CONTEXT.set(context);
        }
        return context;
    }

    private Cat.Context initContext() {
        Cat.Context context = new CatRocketContext();
//        Map<String, String> attachments = RpcContext.getContext().getAttachments();
//        if (attachments != null && attachments.size() > 0) {
//            for (Map.Entry<String, String> entry : attachments.entrySet()) {
//                if (Cat.Context.CHILD.equals(entry.getKey()) || Cat.Context.ROOT.equals(entry.getKey()) || Cat.Context.PARENT.equals(entry.getKey())) {
//                    context.addProperty(entry.getKey(), entry.getValue());
//                }
//            }
//        }
        
        return context;
    }
}
