package com.solu_m.com.docker_poc_1;

import lombok.extern.slf4j.Slf4j;
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

import javax.management.Attribute;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class Controller {

    private static final String REDIS_ENTITY = "redis";

    public static int i = 1;

    @Value("${env_value:DEFAULT}")
    private String ENV_VALUE;

    @Value("${HOSTNAME:LOCAL}")
    private String hostName;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Long size;


    @GetMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> welcomeMapping() {
        HashMap<String, Object> start = new LinkedHashMap<>();
        start.put("microservice name", "DockerPoc-1");
        start.put("version", 1.0);
        start.put("docker instance", hostName);
        start.put("java version", System.getProperty("java.version"));
        start.put("spring boot version", "2.3.4.RELEASE");
        HashMap<String, String> osInfo = new HashMap<>();
        String[] cmd = {"/bin/sh", "-c", "cat /etc/*-release"};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("=");
                if (split.length > 1) {
                    osInfo.put(split[0], split[1].replace("\"", ""));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        start.put("os info", osInfo);

        HashMap<String, Object> systemInfo = new LinkedHashMap<>();
        systemInfo.put("total no. of thread", Runtime.getRuntime().availableProcessors());
        int mb = 1024 * 1024;
        String[] attr = {"TotalPhysicalMemorySize", "FreePhysicalMemorySize"};
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        List<Attribute> al;
        try {
            al = ManagementFactory.getPlatformMBeanServer()
                    .getAttributes(op.getObjectName(), attr).asList();
        } catch (Exception ex) {
            al = Collections.emptyList();
        }
        for (Attribute a : al) {
            Long size = Long.parseLong(String.valueOf(a.getValue())) / mb;
            systemInfo.put(a.getName().toLowerCase(), size);
        }
        start.put("system info", systemInfo);

        HashMap<String, Object> jvmInfo = new LinkedHashMap<>();
        jvmInfo.put("max jvm memory", Runtime.getRuntime().maxMemory() / mb);
        jvmInfo.put("total jvm memory", Runtime.getRuntime().totalMemory() / mb);
        jvmInfo.put("free jvm memory", Runtime.getRuntime().freeMemory() / mb);
        start.put("jvm stats", jvmInfo);

        log.debug("version endpoint called {}", start);
        return ResponseEntity.ok().body(start);
    }

    @GetMapping(value = "/getvalue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getValue() {
        try {
            HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
            int value = (int) stringObjectObjectHashOperations.get(REDIS_ENTITY, "value");
            return ResponseEntity.ok().body(String.valueOf(value));
        } catch (Exception e) {
            return ResponseEntity.ok().body("No value in Redis cache");
        }
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
        String data = IOUtils.toString(fis, "UTF-8").concat("-" + hostName);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping(path = "/service")
    public String helloWorld() {
        return "Environment Name " + " - " + ENV_VALUE + " - " + hostName;
    }

}
