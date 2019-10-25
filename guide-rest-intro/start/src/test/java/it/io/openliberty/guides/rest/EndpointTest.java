package it.io.openliberty.guides.rest;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class EndpointTest {

    private static final Jsonb jsonb = JsonbBuilder.create();

    @Test
    public void testGetProperties() {
        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.name");
        String url = "http://localhost:" + port + "/" + war + "/";

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(url + "System/properties");
        Response response = target.request().get();

        assertEquals("Incorrect response code from " + url,
                Response.Status.OK.getStatusCode(), response.getStatus());

        String json = response.readEntity(String.class);
        Properties sysProps = jsonb.fromJson(json, Properties.class);

        assertEquals("The system property for the local and remote JVM should match",
                System.getProperty("os.name"),
                sysProps.getProperty("os.name"));
        response.close();
    }
}