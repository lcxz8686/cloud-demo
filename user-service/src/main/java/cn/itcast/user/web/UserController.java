package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
//@RefreshScope // 开启 nacos 属性刷新
public class UserController {

    @Autowired
    private UserService userService;

    // 方法一 nacos 统一配置热加载
    // 获取nacos配置文件 - userservice-dev.yaml
    // 用于证明 nacos 统一配置中心生效！
//    @Value("${pattern.dataformat}")
//    private String dateformat;

    // 方法二 nacos 统一配置热加载
    @Autowired
    private PatternProperties patternProperties;

    // 共享配置
    @GetMapping("prop")
    public PatternProperties properties() {
        return patternProperties;
    }


    @GetMapping("now")
    public String now() {
        // 按照我们在nacos上面配置的格式，完成格式化 yyyy-MM-dd HH:mm:ss
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(patternProperties.getDataformat()));
    }


    /**
     * gateway中过滤器添加请求头
     * 路径： /user/110
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id,@RequestHeader(value = "Truth", required = false) String truth) {
        // System.out.println("truth: " + truth);

        // 为了测试熔断，模拟其发生慢调用！
        if (id == 1) {
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return userService.queryById(id);
    }
}
