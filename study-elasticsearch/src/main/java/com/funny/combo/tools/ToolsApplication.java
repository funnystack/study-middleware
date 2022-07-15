package com.funny.combo.tools;

import com.funny.combo.tools.config.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.funny.combo.tools"})
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@EnableTransactionManagement
public class ToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CorsFilter());
        // 过滤顺序，从小到大依次过滤
        return bean;
    }

}
