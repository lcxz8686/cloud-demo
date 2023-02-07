package cn.itcast.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:10010/order/101
 * http://localhost:10010/user/1
 * 1.2. 路由过滤器 和 默认过滤器 是同一类！
 * 3. 全局过滤器 通过适配器 适配成 GateFilter
 * 默认过滤器 > (局部)路由过滤器 > 全局过滤器
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
