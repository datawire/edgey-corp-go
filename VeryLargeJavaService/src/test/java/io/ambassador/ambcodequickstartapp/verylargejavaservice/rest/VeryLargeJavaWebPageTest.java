package io.ambassador.ambcodequickstartapp.verylargejavaservice.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"nodeservice.host=localhost"})
public class VeryLargeJavaWebPageTest {

    private static final String TELEPRESENCE_HEADER_KEY = "x-telepresence-intercept-id";
    private static final String TELEPRESENCE_HEADER_VALUE = "test-telepresence";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${nodeservice.host}")
    String nodeServiceHost;

    @Value("${nodeservice.port}")
    Integer nodeServicePort;


    @Test
    public void testTelepresenceIdPropagates() {
        //Arrange -- config mock HTTP server
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(nodeServicePort));
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/color")).willReturn(aResponse().withBody("blue")));
        wireMockServer.stubFor(get(urlEqualTo("/environment")).willReturn(aResponse().withBody("test")));
        wireMockServer.stubFor(get(urlEqualTo("/recordCount")).willReturn(aResponse().withBody("0")));

        //Act -- make request with Telepresence injected headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(TELEPRESENCE_HEADER_KEY, TELEPRESENCE_HEADER_VALUE);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/", HttpMethod.GET, new HttpEntity<Object>(headers),
                String.class);

        //Assert -- valid response and the mock server saw the propagated TP headers
        assertThat(response.getBody().contains("blue"));

        wireMockServer.verify(getRequestedFor(urlEqualTo("/color"))
                .withHeader(TELEPRESENCE_HEADER_KEY, equalTo(TELEPRESENCE_HEADER_VALUE)));
    }
}
