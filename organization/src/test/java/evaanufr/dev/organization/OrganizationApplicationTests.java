package evaanufr.dev.organization;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:organization",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class OrganizationApplicationTests {

    @Test
    void contextLoads() {
    }
}
