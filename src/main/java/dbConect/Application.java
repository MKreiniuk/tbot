package dbConect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
	 Logger log = LoggerFactory.getLogger(SpringApplication.class);
		
		SpringApplication.run(Application.class, args);
	}

}
