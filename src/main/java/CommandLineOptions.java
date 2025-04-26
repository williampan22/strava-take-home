import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class to store command line options
 */
public class CommandLineOptions {
    private String source = "file";  // Default source is file
    private String endpoint = "";
    private String filePath = "example-in.json";

    // Replace days with explicit date parameters
    private String year;   // Format: YYYY
    private String month;  // Format: MM
    private String day;    // Format: DD

    // Constructor to set default date to yesterday
    public CommandLineOptions() {
        // Default to yesterday's date
        LocalDate yesterday = LocalDate.now().minusDays(1);
        this.year = yesterday.format(DateTimeFormatter.ofPattern("yyyy"));
        this.month = yesterday.format(DateTimeFormatter.ofPattern("MM"));
        this.day = yesterday.format(DateTimeFormatter.ofPattern("dd"));
    }

    // Getters
    public String getSource() {
        return source;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    // Setters
    public void setSource(String source) {
        this.source = source;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }
}