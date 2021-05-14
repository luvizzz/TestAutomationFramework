package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;
import steps.CarSteps;
import steps.CountrySteps;
import steps.ManufacturerSteps;
import steps.ShopSteps;
import steps.StockSteps;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BaseTest {
    String DB_IMAGE = "postgres:9.6.12";
    String DB_NAME = "postgres";
    String DB_USER = "postgres";
    String DB_PASSWORD = "postgres";
    String JDBC_URL = "jdbc:postgresql://host.docker.internal:%d/%s";
    //String JDBC_URL = "jdbc:tc:postgresql://host.docker.internal:%d/%s";
    String APP_IMAGE = "sut-1.0";
    String APP_URI;

    protected MockedDb database = new MockedDb(DB_IMAGE);

    protected MockedApp application = new MockedApp(APP_IMAGE);

    protected CountrySteps countrySteps = new CountrySteps();

    protected CarSteps carSteps = new CarSteps();

    protected ManufacturerSteps manufacturerSteps = new ManufacturerSteps();

    protected ShopSteps shopSteps = new ShopSteps();

    protected StockSteps stockSteps = new StockSteps();

    @BeforeEach
    protected void init() {
        startDb();
        startApp();
    }

    @AfterEach
    protected void tearDown() {
        stopApp();
        stopDb();
    }

    private void startDb() {
        database.configure(DB_NAME, DB_USER, DB_PASSWORD);
        database.start();
    }

    private void stopDb() {
        database.stop();
    }

    private void startApp() {
        application.configure(setupJdbcUrl());
        application.start();

        APP_URI = String.format("http://localhost:%d", application.getPort());
        countrySteps.setRootUri(APP_URI);
        carSteps.setRootUri(APP_URI);
        manufacturerSteps.setRootUri(APP_URI);
        shopSteps.setRootUri(APP_URI);
        stockSteps.setRootUri(APP_URI);
    }

    private void stopApp() {
        application.stop();
    }

    private String setupJdbcUrl() {
        return String.format(JDBC_URL, database.getPort(), DB_NAME);
    }
}
