package com.leyou.client;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("spec")
public interface SpecClientServer {
    @RequestMapping("specParamsBycid")
    public List<SpecParam> findSpecParamByCidAndSearch(@RequestParam Long cid);
}
