package com.leyou.client;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.vo.SpuVo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("spu")
public interface SpuClientServer {

    @RequestMapping("page")
    public PageResult<SpuVo> page(@RequestParam("key") String key,
                                  @RequestParam("saleable") Integer saleable,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows);

    @RequestMapping("detail/{id}")
    public SpuDetail findSpuDetailbySpuId(@PathVariable("id")Long spuId);

    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("spuId") Long spuId);

    /*
     * 根据spuId查询spu封装进spuVo
     * */
    @RequestMapping("findSpuBySpuId")
    public SpuVo findSpuBySpuId(@RequestParam("spuId") Long spuId);
}
