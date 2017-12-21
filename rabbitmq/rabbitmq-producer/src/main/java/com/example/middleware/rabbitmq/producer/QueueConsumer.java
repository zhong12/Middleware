package com.example.middleware.rabbitmq.producer;

import com.rabbitmq.client.*;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by zhongjing on 2017/03/27.
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {
    public QueueConsumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    @Override
    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(body);
        System.out.println("Message Number "+ map.get("message number") + " received.");

    }
    
    @Override
    public void handleCancel(String consumerTag) {}

    @Override
    public void handleCancelOk(String consumerTag) {}

    @Override
    public void handleRecoverOk(String consumerTag) {}

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {

    }
    
    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}
