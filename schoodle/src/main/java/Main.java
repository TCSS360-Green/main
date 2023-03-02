

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"java"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        String url = "http://localhost:8080/login.html";

        System.out.println("Application started successfully.");
        System.out.println("Please open a web browser and navigate to:");
        System.out.println(url);
    }
}
