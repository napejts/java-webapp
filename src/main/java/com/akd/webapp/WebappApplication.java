package com.akd.webapp;
import java.io.File;
import com.akd.webapp.controller.PersonController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class WebappApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebappApplication.class, args);

	}





}
