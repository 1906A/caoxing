package cn.jiyun.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "item.test.queue",durable = "true"),
            exchange = @Exchange(value = "item.test.exchange",ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"*.*"}
    ))
    public void listen(String msg){
        System.out.println("接收到的消息:"+msg);
    }
}
