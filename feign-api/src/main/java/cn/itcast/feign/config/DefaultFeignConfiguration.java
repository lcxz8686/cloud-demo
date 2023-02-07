package cn.itcast.feign.config;

import cn.itcast.feign.clients.fallback.UserClientFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * Feign 的配置类
 * 全局有效，在启动类上：@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class)
 * 对某一个服务有效：@FeignClient(value = "userservice",configuration = FeignClientsConfiguration.class)
 */
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }

    /**
     * sentinel-失败降级
     * 将UserClientFallbackFactory注册为一个Bean
     */
    @Bean
    public UserClientFallbackFactory userClientFallbackFactory() {
        return new UserClientFallbackFactory();
    }
}
