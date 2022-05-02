package pt.ua.deti.tqs.hw1.functional_tests.functionaltests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FunctionalTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionalTestsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:63342", "http://localhost:63343")
                        .allowedMethods("GET");
            }
        };
    }
}
