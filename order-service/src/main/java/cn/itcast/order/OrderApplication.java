package cn.itcast.order;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.config.DefaultFeignConfiguration;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * http://localhost:8080/order/101
 * @EnableFeignClients  扫描和注册feign客户端bean定义.告诉框架扫描所有使用注解@FeignClient定义的feign客户端
 */
@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
// Feign 的自定义配置 表示全局有效
@EnableFeignClients(clients = UserClient.class, defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * @LoadBalanced: 表示restTemplate发起的请求要被 Ribbon处理
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    /**
//     * 全局配置文件！
//     * 整个 orderservice 里面不管调用哪一个微服务都是“随机“的！
//     * @return
//     */
//    @Bean
//    public IRule randomRule() {
//        // 随机负载均衡规则
//        return new RandomRule();
//        //return new ZoneAvoidanceRule();
//    }
}

