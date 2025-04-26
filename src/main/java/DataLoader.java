import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Utility class to load data from file or server
 */
public class DataLoader {
    /**
     * Read and parse data from a JSON file
     *
     * @param filename Path to the JSON file
     * @return List of LogInfoDTO objects
     * @throws IOException If file reading fails
     */
    public static List<LogInfoDTO> getDataFromFile(String filename) throws IOException {
        StringBuilder jsonContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }

        return JsonParser.parseCollectionData(jsonContent.toString());
    }

    /**
     * Fetch and parse data from the API using specific date
     *
     * @param endpoint API endpoint URL
     * @param year Year (YYYY format)
     * @param month Month (MM format)
     * @param day Day (DD format)
     * @return List of LogInfoDTO objects
     * @throws IOException If the API request fails
     * @throws InterruptedException If the HTTP request is interrupted
     */
    public static List<LogInfoDTO> getDataFromServerByDate(String endpoint, String year, String month, String day)
            throws IOException, InterruptedException {
        // Get API response
        String jsonData = fetchDataFromApiByDate(endpoint, year, month, day);

        // Parse the JSON data
        return JsonParser.parseCollectionData(jsonData);
    }

    /**
     * Fetch data from the API for a specific date
     *
     * @param endpoint API endpoint URL
     * @param year Year (YYYY format)
     * @param month Month (MM format)
     * @param day Day (DD format)
     * @return JSON response as string
     * @throws IOException If the API request fails
     * @throws InterruptedException If the HTTP request is interrupted
     */
    private static String fetchDataFromApiByDate(String endpoint, String year, String month, String day)
            throws IOException, InterruptedException {
        // Construct the URL with date parameters
        String url = endpoint + "/_cat/indices/*" + year + "*" + month + "*" + day +
                "?v&h=index,pri.store.size,pri&format=json&bytes=b";

        // Create and send HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API request failed with status: " + response.statusCode());
        }

        return response.body();
    }
}