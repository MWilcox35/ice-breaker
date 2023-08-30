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

        User userObject = getUserInfo();

        System.out.println(userObject.toString() + "\n");

        SyllabusResponse syllabusResponseObject = getSyllabi();
        System.out.println("SYLLABI LIST : ");
        for (Syllabus syllabus : syllabusResponseObject.syllabi) {
            System.out.println(syllabus.toString());
        }

        EventResponse eventResponseObject = getTargetSyllabi("1");
        System.out.println("\n\n EVENT LIST : ");

        for (Event targetEvent : eventResponseObject.events) {
            System.out.println(targetEvent.toString());
        }
    }

    /**
     * Retrieves User details.
     *
     * @return A User object containing User details.
     */
    public static User getUserInfo() throws Exception {
        String finalEndPoint = targetURL + "/api/user";
        HttpURLConnection userConnection = connect(finalEndPoint, apiKey);
        String userResponse = getResponse(userConnection);

        return JsonToUserObject(userResponse);
    };


    /**
     * Retrieves event details based on a target ID.
     *
     * @param target The target ID for which event details are to be retrieved.
     * @return       An EventResponse object containing event details.
     */
    public static EventResponse getTargetSyllabi(String Target) throws Exception {
        String finalEndPoint = targetURL + "/api/syllabus/" + Target;
        HttpURLConnection connectionPoint = connect(finalEndPoint, apiKey);
        String eventResult = getResponse(connectionPoint);

        EventResponse eventResponseObject = JsonToEventObject(eventResult);


        return eventResponseObject;
    }

    /**
     * Retrieves syllabus details.
     *
     * @return A SyllabusResponse object containing syllabus details.
     */
    public static SyllabusResponse getSyllabi() throws Exception {
        String finalEndPoint = targetURL + "/api/syllabus/";
        HttpURLConnection connectionPoint = connect(finalEndPoint, apiKey);
        String syllabiResponse = getResponse(connectionPoint);

        SyllabusResponse syllabusResponseObject = JsonToSyllabusObject(syllabiResponse);


        return syllabusResponseObject;

    }



    public static User JsonToUserObject(String jsonInput) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        User userObject = objectMapper.readValue(jsonInput,User.class);
        return userObject;
    }

    /**
     * Converts JSON input to an EventResponse object.
     *
     * @param jsonInput The JSON input to be converted.
     * @return          An EventResponse object.
     */
    public static EventResponse JsonToEventObject(String jsonInput) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        EventResponse eventResponse = objectMapper.readValue(jsonInput,EventResponse.class);
        return eventResponse;
    }

    /**
     * Converts JSON input to a SyllabusResponse object.
     *
     * @param jsonInput The JSON input to be converted.
     * @return          A SyllabusResponse object.
     */
    public static SyllabusResponse JsonToSyllabusObject(String jsonInput) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        SyllabusResponse syllabusResponse = objectMapper.readValue(jsonInput, SyllabusResponse.class);
        return syllabusResponse;
    }


    /**
     * Establishes a connection to a given URL with the provided API key.
     *
     * @param finalEndPoint The URL to connect to.
     * @param key           The API key used for authorization.
     * @return              A HttpURLConnection object representing the connection.
     */
    private static HttpURLConnection connect(String finalEndPoint, String key) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) (new URL(finalEndPoint)).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + key);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        return connection;
    }


    /**
     * Reads the response from an HttpURLConnection and returns it as a string.
     *
     * @param connection The HttpURLConnection object from which to read the response.
     * @return           The response as a string.
     */
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
