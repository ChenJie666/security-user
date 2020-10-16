package oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/9/16 11:12
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = "oauth2.dao")
@EnableTransactionManagement
public class UserApplication9600 {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication9600.class, args);
    }
}
