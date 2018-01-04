package com.example.middleware.rocketmq.config;

import com.dianping.cat.Cat;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhongjing on 2018/01/02.
 */
public class RocketMqContextHolder {

    private static final ThreadLocal<Cat.Context> ROCKETMQ_CAT_CONTEXT = new ThreadLocal<Cat.Context>();

    public static Cat.Context getContext() {
        Cat.Context context = ROCKETMQ_CAT_CONTEXT.get();
        if (context == null) {
            context = new RocketMqContext();
            ROCKETMQ_CAT_CONTEXT.set(context);
        }
        return context;
    }

    public static void setContext(Cat.Context context) {
        ROCKETMQ_CAT_CONTEXT.set(context);
    }

    private RocketMqContextHolder() {
    }


    public static void clearRpcContext() {
        ROCKETMQ_CAT_CONTEXT.remove();
    }


    private static class RocketMqContext implements Cat.Context {

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
}
