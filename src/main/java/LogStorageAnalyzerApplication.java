import java.util.List;

/**
 * Main class for the Log Storage Analyzer
 * Handles command line arguments and coordinates the analysis workflow
 */
public class LogStorageAnalyzerApplication {

    public static void main(String[] args) {
        // Parse command line arguments
        CommandLineOptions options = CommandLineParser.parseArguments(args);

        // Get data either from file or server based on the source option
        List<LogInfoDTO> data = null;
        try {
            if (options.getSource().equals("file")) {
                data = DataLoader.getDataFromFile(options.getFilePath());
            } else if (options.getSource().equals("api")) {
                // Use the date parameters instead of days ago
                data = DataLoader.getDataFromServerByDate(
                        options.getEndpoint(),
                        options.getYear(),
                        options.getMonth(),
                        options.getDay()
                );
            } else {
                System.err.println("Error: Invalid source specified. Use file or api.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error reading data: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Process and display results
        ResultsPrinter.printLargestCollections(data);
        ResultsPrinter.printMostPartitions(data);
        ResultsPrinter.printLeastBalanced(data);
    }
}