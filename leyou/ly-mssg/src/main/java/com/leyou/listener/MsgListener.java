package com.leyou.listener;

import com.leyou.utils.SmsUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MsgListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="sms.send.queue", durable = "true"),
            exchange = @Exchange(value = "sms.exchanges",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"sms.send"}

    ))
    public void sendData(Map<String,String> map) throws Exception {
        System.out.println("开始监听发送数据，phone="+map.get("phone")+"======="+map.get("code"));

        if(map.size()>0 && map!=null){
            SmsUtils.sendMsg(map.get("phone"),map.get("code"));
        }
        System.out.println("结束监听");
    }
}
