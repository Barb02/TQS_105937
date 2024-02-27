package integration;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolverService;
import geocoding.ConfigUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class AddressResolverIT {


    // buscar key da API
    String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");
    AddressResolverService resolver;

    @BeforeEach
    public void init(){
        resolver = new AddressResolverService(new TqsBasicHttpClient());
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> result = resolver.findAddressForLocation(-361,-361);

        // verify no valid result
        assertFalse(result.isPresent());
    }

}
