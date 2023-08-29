import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    }

    private static HttpURLConnection connect(String finalEndPoint, String key) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) (new URL(finalEndPoint)).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + key);
        return connection;
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
