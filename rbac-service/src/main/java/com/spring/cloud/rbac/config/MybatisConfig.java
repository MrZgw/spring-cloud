package com.spring.cloud.rbac.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @ClassName MybatisConfig
 * @Description mybatis-plus配置
 * @Author zgw
 * @Date 2020/1/17 15:08
 **/
@Configuration
@MapperScan("com.spring.cloud.**.*Mapper")
public class MybatisConfig {

    /**
     * 分页插件配置
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * sql打印配置
     *
     * @return PerformanceMonitorInterceptor
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor();
    }
}
