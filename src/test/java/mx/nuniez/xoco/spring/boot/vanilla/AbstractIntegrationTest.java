package mx.nuniez.xoco.spring.boot.vanilla;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.YugabyteYSQLContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@ContextConfiguration(initializers = AbstractIntegrationTest.DatabaseContextInitializer.class)
@SpringBootTest(classes = SpringBootVanillaApplication.class, webEnvironment = RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    //private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;
    private static final YugabyteYSQLContainer YUGABYTE_CONTAINER;

    static {
        //POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:13.3");
        //POSTGRESQL_CONTAINER.start();
        YUGABYTE_CONTAINER = new YugabyteYSQLContainer("yugabytedb/yugabyte:latest");
        YUGABYTE_CONTAINER.start();
    }

    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected ObjectMapper objectMapper;
    @LocalServerPort
    private int port;

    protected String url(String path) {
        return "http://localhost:" + port + path;
    }

    public static class DatabaseContextInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    //"spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    //"spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    //"spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword()
                    "spring.datasource.url=" + YUGABYTE_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + YUGABYTE_CONTAINER.getUsername(),
                    "spring.datasource.password=" + YUGABYTE_CONTAINER.getPassword()
            ).applyTo(context.getEnvironment());
        }

    }

}
