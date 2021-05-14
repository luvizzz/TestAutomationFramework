package base;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class MockedApp extends GenericContainer<MockedApp> {
    private final static Logger LOG = Logger.getLogger(MockedApp.class.getSimpleName());
    Integer DEFAULT_APPLICATION_PORT = 8080;

    protected void configure(String jdbcUrl) {
        LOG.info(String.format("Configuring %s using jdbcUrl as '%s'", this.getDockerImageName(), jdbcUrl));
        Map<String, String> env = new HashMap<>();
        env.put("SPRING.DATASOURCE.URL", jdbcUrl);

        this.withEnv(env);
        this.withExposedPorts(DEFAULT_APPLICATION_PORT);
    }

    public MockedApp(String imageName) {
        super(imageName);
    }

    public void start() {
        super.start();
    }

    public int getPort() {
        return super.getMappedPort(DEFAULT_APPLICATION_PORT);
    }
}
