package sample.cafekiosk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA AuditingEntityListener 사용하기 위함
@SpringBootApplication
public class CafeKioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeKioskApplication.class, args);
    }

}
