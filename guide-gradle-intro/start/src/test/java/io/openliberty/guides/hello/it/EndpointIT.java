package io.openliberty.guides.hello.it;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
public class EndpointIT {
    private static String URL;

    @BeforeClass
    public static void init() {
        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.name");
        URL = "http://localhost:" + port + "/" + war + "/" + "servlet";
    }
    @Test
    public void testServlet() throws Exception {
        HttpClient httpClient = new HttpClient();
        GetMethod httpGetMethod = new GetMethod(URL);
        try {
            int actualStatusCode = httpClient.executeMethod(httpGetMethod);
            int expectedStatusCode = HttpStatus.SC_OK;
            assertEquals("HTTP GET failed", expectedStatusCode, actualStatusCode);
            String response = httpGetMethod.getResponseBodyAsString(1000);
            assertTrue("Unexpected response body",
                    response.contains("Hello! Is Gradle working for you?"));
        } finally {
            httpGetMethod.releaseConnection();
        }
    }
}