package geocoding;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks
    AddressResolverService resolver;

    @Mock
    ISimpleHttpClient client;
    
    // buscar key da API
    String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        // preparar uri
        URIBuilder uriBuilder = new URIBuilder("https://www.mapquestapi.com/geocoding/v1/reverse");
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.5f,%.5f", 40.63436, -8.65616).toString());
        uriBuilder.addParameter("outFormat", "json");
        uriBuilder.addParameter("thumbMaps", "false");
        String uri = uriBuilder.build().toString();

        String jsonString = "{ \"info\": { \"statuscode\": 0, \"copyright\": { \"text\": \"© 2024 MapQuest, Inc.\", \"imageUrl\": \"https://api.mqcdn.com/res/mqlogo.gif\", \"imageAltText\": \"© 2024 MapQuest, Inc.\" }, \"messages\": [] }, \"options\": { \"maxResults\": 1, \"thumbMaps\": false, \"ignoreLatLngInput\": false }, \"results\": [ { \"providedLocation\": { \"latLng\": { \"lat\": 40.63436, \"lng\": -8.65616 } }, \"locations\": [ { \"street\": \"Avenida da Universidade\", \"adminArea6\": \"\", \"adminArea6Type\": \"Neighborhood\", \"adminArea5\": \"Aveiro\", \"adminArea5Type\": \"City\", \"adminArea4\": \"Aveiro\", \"adminArea4Type\": \"County\", \"adminArea3\": \"\", \"adminArea3Type\": \"State\", \"adminArea1\": \"PT\", \"adminArea1Type\": \"Country\", \"postalCode\": \"3810-489\", \"geocodeQualityCode\": \"L1AAA\", \"geocodeQuality\": \"ADDRESS\", \"dragPoint\": false, \"sideOfStreet\": \"R\", \"linkId\": \"0\", \"unknownInput\": \"\", \"type\": \"s\", \"latLng\": { \"lat\": 40.63436, \"lng\": -8.65616 }, \"displayLatLng\": { \"lat\": 40.63436, \"lng\": -8.65616 }, \"mapUrl\": \"https://www.mapquestapi.com/staticmap/v4/getmap?key=KEY&type=map&size=225,160&pois=purple-1,30.3334721,-81.4704483,0,0,|&center=30.3334721,-81.4704483&zoom=15&rand=-553163060\" } ] } ] }";

        when(client.doHttpGet(uri)).thenReturn(jsonString);

        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        // return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        // preparar uri
        URIBuilder uriBuilder = new URIBuilder("https://www.mapquestapi.com/geocoding/v1/reverse");
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.5f,%.5f", -361.0, -361.0).toString());
        uriBuilder.addParameter("outFormat", "json");
        uriBuilder.addParameter("thumbMaps", "false");
        String uri = uriBuilder.build().toString();

        String jsonString = "{ \"info\": { \"statuscode\": 400, \"copyright\": { \"text\": \"© 2024 MapQuest, Inc.\", \"imageUrl\": \"http://api.mqcdn.com/res/mqlogo.gif\", \"imageAltText\": \"© 2024 MapQuest, Inc.\" }, \"messages\": [ \"Illegal argument from request: Invalid LatLng specified.\" ] }, \"options\": { \"maxResults\": 1, \"ignoreLatLngInput\": false }, \"results\": [ { \"providedLocation\": {}, \"locations\": [] } ] }";

        when(client.doHttpGet(uri)).thenReturn(jsonString);
        
        Optional<Address> result = resolver.findAddressForLocation(-361,-361);
        // verify no valid result
        assertFalse(result.isPresent());

    }
}