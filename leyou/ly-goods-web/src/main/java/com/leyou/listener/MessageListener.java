package com.leyou.listener;

import com.leyou.service.GoodsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    GoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="item.edit.web.queue", durable = "true"),
    exchange = @Exchange(value = "item.exchange",
            ignoreDeclarationExceptions = "true",
            type = ExchangeTypes.TOPIC),
    key = {"item.insert","item.update"}

    ))
    public void editThyMeLeafData(Long spuId) throws Exception {
        System.out.println("开始监听修改ThyMeLeaf数据，spuId="+spuId);

        if(spuId==null){
            return;
        }
        goodsService.createHtml(spuId);
        System.out.println("结束监听修改ThyMeLeaf数据，spuId="+spuId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="item.delete.web.queue", durable = "true"),
    exchange = @Exchange(value = "item.exchange",
            ignoreDeclarationExceptions = "true",
            type = ExchangeTypes.TOPIC),
    key = {"item.delete"}

    ))
    public void deleteThyMeLeafData(Long spuId) throws Exception {
        System.out.println("开始监听删除ThyMeLeaf数据，spuId="+spuId);

        if(spuId==null){
            return;
        }
        goodsService.deleteHtml(spuId);
        System.out.println("结束监听删除ThyMeLeaf数据，spuId="+spuId);
    }
}
