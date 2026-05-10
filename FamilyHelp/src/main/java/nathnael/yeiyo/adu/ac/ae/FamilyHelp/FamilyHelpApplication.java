package nathnael.yeiyo.adu.ac.ae.FamilyHelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "nathnael.yeiyo.adu.ac.ae")
@EnableJpaRepositories(basePackages = "nathnael.yeiyo.adu.ac.ae.repository")
@EntityScan(basePackages = "nathnael.yeiyo.adu.ac.ae.model")
public class FamilyHelpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyHelpApplication.class, args);
    }
}
