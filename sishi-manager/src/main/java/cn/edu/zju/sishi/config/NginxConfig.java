package cn.edu.zju.sishi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myconfig.nginx")
@Data
public class NginxConfig {

    private String host;

    private int port;

    private String httpHead;

    private String winRoot;

    private String linuxRoot;

    private String picPath;

    private String audioPath;

    private String videoPath;

    private String workspacePath;

}
