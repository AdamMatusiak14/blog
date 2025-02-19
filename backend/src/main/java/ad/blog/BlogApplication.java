package ad.blog; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "ad.blog.service")
@ComponentScan(basePackages = "ad.java.security")
@ComponentScan(basePackages = "ad.java.controller")
@SpringBootApplication
 @ComponentScan("ad.java.config")
 @ComponentScan("ad.java.controller")
 @ComponentScan("ad.blog.service")

public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
