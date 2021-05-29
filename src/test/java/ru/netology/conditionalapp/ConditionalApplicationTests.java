package ru.netology.conditionalapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

    private final static String HOST = "http://localhost:";
    private final static String DEV_PROFILE = "Current profile is dev";
    private final static String PROD_PROFILE = "Current profile is production";

    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity(HOST + devApp.getMappedPort(8080) + "/profile"
                , String.class);
        assertThat(forEntityDev.getBody(), is(equalTo(DEV_PROFILE)));

        ResponseEntity<String> forEntityProd = restTemplate.getForEntity(HOST + prodApp.getMappedPort(8081) + "/profile"
                , String.class);
        assertThat(forEntityProd.getBody(), is(equalTo(PROD_PROFILE)));
    }

}
