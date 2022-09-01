package mx.nuniez.xoco.spring.boot.vanilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringBootVanillaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootVanillaApplication.class, args);
    }

}
