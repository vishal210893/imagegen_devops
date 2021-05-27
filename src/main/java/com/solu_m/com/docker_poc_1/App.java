package com.solu_m.com.docker_poc_1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
@RestController
@RequestMapping("api/v1")
public class App {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
    }

}
