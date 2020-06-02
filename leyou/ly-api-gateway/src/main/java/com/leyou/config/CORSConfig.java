package com.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSConfig {
    @Bean
    public CorsFilter corsFilter(){

//        1、创建cors配置信息
        CorsConfiguration configuration = new CorsConfiguration();
//        2、添加允许跨域的参数
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("http://manage.leyou.com");
        configuration.addAllowedOrigin("http://www.leyou.com");
        configuration.setAllowCredentials(true);//是否允许携带cookie

//        3、添加路径映射信息
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

//        允许所有路径使用配置
        source.registerCorsConfiguration("/**",configuration);

//        4、返回cors过滤器实例
        return new CorsFilter(source);
    }
}
