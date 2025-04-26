import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

/**
 * Utility class to process JSON data using Java's built-in JSON library
 * https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html
 */
public class JsonParser {
    /**
     * Parse JSON data into LogInfoDTO objects
     *
     * @param jsonData JSON string to parse
     * @return List of LogInfoDTO objects
     */
    public static List<LogInfoDTO> parseCollectionData(String jsonData) {
        List<LogInfoDTO> collections = new ArrayList<>();

        try (JsonReader reader = Json.createReader(new StringReader(jsonData))) {
            JsonArray jsonArray = reader.readArray();

            for (JsonValue jsonValue : jsonArray) {
                if (jsonValue.getValueType() == JsonValue.ValueType.OBJECT) {
                    JsonObject jsonObject = (JsonObject) jsonValue;

                    // Extract required fields
                    if (!jsonObject.containsKey("index") ||
                            !jsonObject.containsKey("pri.store.size") ||
                            !jsonObject.containsKey("pri")) {
                        continue;
                    }

                    String name = jsonObject.getString("index");
                    String sizeStr = jsonObject.getString("pri.store.size");
                    String partitionsStr = jsonObject.getString("pri");

                    try {
                        long size = Long.parseLong(sizeStr);
                        int partitions = Integer.parseInt(partitionsStr);
                        collections.add(new LogInfoDTO(name, size, partitions));
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid data for collection " + name + ": " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            throw new IllegalArgumentException("Invalid JSON format", e);
        }

        return collections;
    }
}