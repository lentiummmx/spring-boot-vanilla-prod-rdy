package mx.nuniez.xoco.spring.boot.vanilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        AbstractCacheIT.RedisContextInitializer.class
})
@SpringBootTest(classes = SpringBootVanillaApplication.class, webEnvironment = RANDOM_PORT)
public class AbstractCacheIT {

    @Autowired
    protected CacheManager cacheManager;

    private static final GenericContainer<?> REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer<>("redis:6.2.3").withExposedPorts(6379);
        REDIS_CONTAINER.start();
    }

    public static class RedisContextInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.redis.host=" + REDIS_CONTAINER.getHost(),
                    "spring.redis.port=" + REDIS_CONTAINER.getFirstMappedPort()
            ).applyTo(context.getEnvironment());
        }

    }

}
