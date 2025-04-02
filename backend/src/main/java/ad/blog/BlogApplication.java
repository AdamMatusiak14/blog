package ad.blog; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @ComponentScan(basePackages = "ad.blog.service")
// @ComponentScan(basePackages = "ad.blog.security")
// @ComponentScan(basePackages = "ad.blog.controller")
// @ComponentScan(basePackages = "ad.blog.config")
@SpringBootApplication (scanBasePackages = "ad.blog")

public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
