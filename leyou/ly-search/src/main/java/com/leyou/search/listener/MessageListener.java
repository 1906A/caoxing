package com.leyou.search.listener;

import com.leyou.search.service.GoodsService;
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
            value = @Queue(value="item.edit.search.queue",durable = "true"),
            exchange = @Exchange(value = "item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}

    ))
    public void editEsData(Long spuId) throws Exception {
        System.out.println("开始监听修改es数据，spuId="+spuId);
        if(spuId==null){
            return;
        }
        goodsService.editEsData(spuId);
        System.out.println("结束监听修改es数据，spuId="+spuId);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="item.delete.search.queue",durable = "true"),
            exchange = @Exchange(value = "item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = {"item.delete"}

    ))
    public void deleteEsData(Long spuId) throws Exception {
        System.out.println("开始监听删除es数据，spuId="+spuId);
        if(spuId==null){
            return;
        }
        goodsService.deleteEsData(spuId);
        System.out.println("结束监听删除es数据，spuId="+spuId);
    }
}
