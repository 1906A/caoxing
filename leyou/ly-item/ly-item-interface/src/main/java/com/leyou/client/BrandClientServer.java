package com.leyou.client;

import com.leyou.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("brand")
public interface BrandClientServer {
    @RequestMapping("findBrandByid")
    public Brand findBrandByid(@RequestParam("id") long id);
}
