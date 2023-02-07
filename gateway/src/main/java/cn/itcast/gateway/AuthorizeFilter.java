package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @Order(): 过滤器的优先级，越小越高！！！
 * http://localhost:10010/user/1?authorization=admin     -> 可
 * http://localhost:10010/user/1?authorization=admin401  -> 不行！！！
 */
// @Order(-1)  // 顺序注解，用接口Ordered也是可以的
// @Component  // 如果打开了，就要：http://localhost:10010/user/1?authorization=admin
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();

        // 2. 获取参数中的 authorizeFilter 参数
        String auth = params.getFirst("authorization");

        // 3. 判断参数中是否有admin
        if ("admin".equals(auth)) {
            // 4. 是，放行
            return chain.filter(exchange);
        }

        // 5. 否，拦截
        // HttpStatus 是 org.springframework.http
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED); // 未登入
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
