package com.leyou.client;

import com.leyou.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("sku")
public interface SkuClientServer {

    @RequestMapping("list")
    public List<Sku> findSkuBySpuId(@RequestParam Long id);
}
