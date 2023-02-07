package cn.itcast.order.service;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private UserClient userClient;


    /**
     * 使用OpenFegin 方式进行远程调用
     * @param orderId
     * @return
     */
    public Order queryOrderById(Long orderId) {
        // 查询订单
        Order order = orderMapper.findById(orderId);
        // 用OpenFegin远程调用
        User user = userClient.findById(order.getUserId());
        order.setUser(user);
        return order;
    }

    /**
     * 用于Sentinel的流控
     */
    @SentinelResource("goods")
    public void queryGoods() {
        System.out.println("查询商品");
    }


    /**
     * 使用restTemplate 方式进行远程调用
     * @param orderId
     * @return
     */
//    public Order queryOrderByIdRestTemplate(Long orderId) {
//        // 1.查询订单
//        Order order = orderMapper.findById(orderId);
//        // 2.利用restTemplate发起http请求
//        // 为了负载均衡使用服务名称
//        String url = "http://userservice/user/" + order.getUserId();
//        // 得到的结果是json风格，getForObject()可以将json反序列化！
//        User user = restTemplate.getForObject(url, User.class);
//        // 3.封装user到Order
//        order.setUser(user);
//        // 4.返回
//        return order;
//    }
}
