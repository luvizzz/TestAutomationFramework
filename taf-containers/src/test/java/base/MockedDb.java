package base;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.logging.Logger;

@Component
public class MockedDb extends PostgreSQLContainer<MockedDb> {
    private final static Logger LOG = Logger.getLogger(MockedDb.class.getSimpleName());

    protected void configure(String db, String user, String password) {
        LOG.info(String.format("Configuring %s using db='%s', user='%s', password='%s'", this.getDockerImageName(), db, user, password));
        this.withUrlParam("loggerLevel", "INFO")
            .withDatabaseName(db)
            .withUsername(user)
            .withPassword(password)
            .configure();
    }

    public MockedDb(String dbImage) {
        super(dbImage);
    }

    public void start() {
        super.start();
    }

    public int getPort() {
        return super.getMappedPort(POSTGRESQL_PORT);
    }
}
