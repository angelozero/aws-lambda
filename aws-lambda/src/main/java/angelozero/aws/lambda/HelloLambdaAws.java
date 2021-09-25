package angelozero.aws.lambda;

import angelozero.aws.lambda.model.Person;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Hello world lambda!
 */
public class HelloLambdaAws implements RequestStreamHandler {


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        JSONObject responseJson = new JSONObject();

        JSONObject headerJson = new JSONObject();
        headerJson.put("x-custom-header", "my custom header value");

        responseJson.put("statusCode", 200);
        responseJson.put("headers", headerJson);
        responseJson.put("body", new ObjectMapper().writeValueAsString(readJson()));

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }

    private Person readJson() {

        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/json/data.json"))) {
            Person person = new Gson().fromJson(reader, Person.class);
            person.setId(UUID.randomUUID().toString());
            return person;

        } catch (IOException e) {
            throw new RuntimeException("Error Json File");
        }
    }
}
