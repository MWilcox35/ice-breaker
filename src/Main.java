import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    final static String targetURL = "https://courses.ianapplebaum.com";
    final static String apiKey = "BF9gfMQBoFjEjQaQlloohsxeCRg2fWRniHhUnUMb";


    public static void main(String[] args) throws Exception {
	// write your code here
        String finalEndPoint = targetURL + "/api/user";
        HttpURLConnection userConnection = connect(finalEndPoint, apiKey);

        String userResponse = getResponse(userConnection);
        if(userResponse != null){
            System.out.println("User Response: " + userResponse);
        } else {
            System.out.println("Failed to get response");
        }

        SyllabusResponse syllabusResponseObject = getSyllabi();
    }

    public static SyllabusResponse getSyllabi() throws Exception {
        String finalEndPoint = targetURL + "/api/syllabus/";
        HttpURLConnection connectionPoint = connect(finalEndPoint, apiKey);
        String syllabiResponse = getResponse(connectionPoint);

        SyllabusResponse syllabusResponseObject = JsonToObject(syllabiResponse);

        for (Syllabus syllabus : syllabusResponseObject.syllabi) {
            System.out.println(syllabus.id);
            System.out.println(syllabus.course_name);
        }

        return syllabusResponseObject;

    }

    private static HttpURLConnection connect(String finalEndPoint, String key) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) (new URL(finalEndPoint)).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + key);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }

    public static SyllabusResponse JsonToObject(String jsonInput) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        SyllabusResponse syllabusResponse = objectMapper.readValue(jsonInput, SyllabusResponse.class);
        return syllabusResponse;
    }

    private static String getResponse(HttpURLConnection connection) throws Exception {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }
        return null;
    }
}
