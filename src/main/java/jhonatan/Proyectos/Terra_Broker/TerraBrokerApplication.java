package jhonatan.Proyectos.Terra_Broker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TerraBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerraBrokerApplication.class, args);
	}

}
