import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class JsonAPITest{

    @Test
    void whenGetAllToDos_thenOK(){
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos").
        then().
            statusCode(200);
    }

    @Test
    void whenGetToDo4_thenReturnValidObject(){
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos/4").
        then().
            body("title", equalTo("et porro tempora"));
    }

    @Test
    void whenGetAllToDos_thenIdsArePresent(){
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos").
        then().
            body("id", hasItems(198, 199));
    }

    @Test
    void whenGetAllToDos_thenResponseInLessThan2Secs(){
        given().
        when().
            get("https://jsonplaceholder.typicode.com/todos").
        then().
            time(lessThan(2000L));
    }
}