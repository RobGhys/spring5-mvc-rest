package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestControllerTest {

    // Return back a Json string using Jackson
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}