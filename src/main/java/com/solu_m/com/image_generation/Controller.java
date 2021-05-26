package com.solu_m.com.image_generation;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1")
public class Controller {

    private static final String REDIS_ENTITY = "redis";

    public static int i = 1;

    @Value("${imagepoc_env_value:DEFAULT}")
    private String ENV_VALUE;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, String>> welcomeMapping() {
        HashMap<String, String> start = new HashMap<>();
        start.put("name", "IG POC");
        start.put("date", new Date().toString());
        return ResponseEntity.ok().body(start);
    }

    @GetMapping(value = "/getvalue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getValue() {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        int value = (int) stringObjectObjectHashOperations.get(REDIS_ENTITY, "value");
        return ResponseEntity.ok().body(String.valueOf(value));
    }

    @GetMapping(value = "/putvalue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putValue() {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        int i = Controller.i++;
        stringObjectObjectHashOperations.put(REDIS_ENTITY, "value", i);
        return ResponseEntity.ok().body(String.valueOf(i));
    }

    @GetMapping(value = "/readfile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> readFile() throws IOException {
        FileInputStream fis = new FileInputStream("/opt/file/fileTest.txt");
        String data = IOUtils.toString(fis, "UTF-8").concat("-" + ENV_VALUE);
        return ResponseEntity.ok().body(String.valueOf(data));
    }

}
