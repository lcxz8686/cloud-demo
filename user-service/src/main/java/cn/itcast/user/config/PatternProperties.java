package cn.itcast.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ConfigurationProperties: 这个注解可以完成配置的自动加载！
 * 前缀名+变量名和配置文件一致，就可以完成属性的自动注入！！！
 */
@Data
@Component
@ConfigurationProperties(prefix = "pattern")
public class PatternProperties {

    private String dataformat;

    private String envShareValue;
}
