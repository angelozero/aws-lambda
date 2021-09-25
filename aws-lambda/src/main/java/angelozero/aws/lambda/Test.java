package angelozero.aws.lambda;

import angelozero.aws.lambda.model.Person;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {

        System.out.println(new Test().readJson());
    }

    private String readJson() {

        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/json/data.json"))) {
            Person result = new Gson().fromJson(reader, Person.class);
            result.setId(UUID.randomUUID().toString());
            return result.toString();

        } catch (IOException e) {
            return "JSON File Error";
        }
    }
}
