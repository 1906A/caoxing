package com.leyou.controller;

import com.leyou.Sku;
import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.SkuVo;
import com.leyou.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.plugin.javascript.navig.LinkArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api("购物车服务接口")
public class CartController {

    /*
    *操作购物车数据放到redis hash类型
    * 1：加入购物车
    * 2：修改购物车
    * 3：删除购物车
    * 4：查询购物车
    * */

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    JwtProperties jwtProperties;

    public String prefix = "ly_carts_";

    public String prefixSelectedSku = "ly_carts_SelectedSku";

    @PostMapping("add")
    @ApiOperation(value = "购物车添加保存到redis", notes = "购物车添加")
    @ApiImplicitParam(name = "skuVo", required = true, value = "结算页选择的sku串")
    public void add(@CookieValue("token") String token, @RequestBody SkuVo skuVo){
        UserInfo userInfo = this.getUserInfoByToken(token);

        //添加购物车
        if(userInfo!=null) {
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix + userInfo.getId());

            //判断redis中的信息
            if(hashOps.hasKey(skuVo.getId()+"")){
                //从redis中获取已存在的购物车消息
                SkuVo redisSkuVo = JsonUtils.parse(hashOps.get(skuVo.getId()+"").toString(), SkuVo.class);
                //修改购物车信息中的数量
                redisSkuVo.setNum(redisSkuVo.getNum()+skuVo.getNum());

                //重新存放redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(redisSkuVo));

                stringRedisTemplate.boundValueOps(prefixSelectedSku+userInfo.getId()).set(JsonUtils.serialize(redisSkuVo));
            }else{
                //不存在
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(skuVo));

                stringRedisTemplate.boundValueOps(prefixSelectedSku+userInfo.getId()).set(JsonUtils.serialize(skuVo));
            }
        }
    }
    /*
    * 去redis获取最新操作的sku信息
    * */
    @PostMapping("selectedSku")
    public SkuVo selectedSku(@CookieValue("token") String token){
        UserInfo userInfo = this.getUserInfoByToken(token);

        String s = stringRedisTemplate.boundValueOps(prefixSelectedSku + userInfo.getId()).get();

        SkuVo skuVo = JsonUtils.parse(s, SkuVo.class);

        return skuVo;
    }

    /*
    * 修改购物车
    * */
    @PostMapping("update")
    public void update(@CookieValue("token") String token, @RequestBody SkuVo skuVo){
        UserInfo userInfo = this.getUserInfoByToken(token);

        //添加购物车
        if(userInfo!=null) {
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix + userInfo.getId());

            //判断redis中的信息
            if(hashOps.hasKey(skuVo.getId()+"")){
                //从redis中获取已存在的购物车消息
                SkuVo redisSkuVo = JsonUtils.parse(hashOps.get(skuVo.getId()+"").toString(), SkuVo.class);
                //修改购物车信息中的数量
                redisSkuVo.setNum(skuVo.getNum());

                //重新存放redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(redisSkuVo));
            }else{
                //不存在
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(skuVo));
            }
        }

    }
    /*
    * 删除购物车
    * */
    @PostMapping("delete")
    public void delete(@CookieValue("token") String token,@RequestParam("id") Long id){
        UserInfo userInfo = this.getUserInfoByToken(token);

        if(userInfo!=null) {
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate
                    .boundHashOps(prefix + userInfo.getId());
            hashOps.delete(id+"");
        }
    }
    /*
    * 查询购物车
    * */
    @PostMapping("query")
    public List<SkuVo> query(@CookieValue("token") String token){
        UserInfo userInfo = this.getUserInfoByToken(token);
        List<SkuVo> list = new ArrayList<>();

        if(userInfo!=null) {
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate
                    .boundHashOps(prefix + userInfo.getId());
            Map<Object, Object> map = hashOps.entries();

            map.keySet().forEach(key->{
                SkuVo skuVo = JsonUtils.parse(map.get(key).toString(), SkuVo.class);
                list.add(skuVo);
            });
        }
        return list;
    }
    /*
    * 登录后根据token解析用户信息
    * */
    public UserInfo getUserInfoByToken(String token){
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}
