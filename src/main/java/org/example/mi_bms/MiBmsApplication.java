package org.example.mi_bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.mybatis.spring.annotation.MapperScan;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan("org.example.mi_bms.mapper")
@Slf4j
@EnableCaching
public class MiBmsApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MiBmsApplication.class);
        Environment environment = application.run(args).getEnvironment();
        log.info("启动成功！！！");
        log.info("地址：\thttp://127.0.0.1:{}",environment.getProperty("server.port"));
    }

}
