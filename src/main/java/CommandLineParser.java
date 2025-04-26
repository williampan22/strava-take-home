/**
 * Utility class to parse command line arguments
 */
public class CommandLineParser {
    /**
     * Parse command line arguments into CommandLineOptions object
     *
     * @param args Command line arguments
     * @return CommandLineOptions object with parsed values
     */
    public static CommandLineOptions parseArguments(String[] args) {
        CommandLineOptions options = new CommandLineOptions();

        // Simple command line argument parsing
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                if (args[i].equals("--source")) {
                    String source = args[i+1];
                    if (source.equals("file") || source.equals("api")) {
                        options.setSource(source);
                    } else {
                        System.err.println("Invalid source. Using default file.");
                    }
                    i++;
                } else if (args[i].equals("--endpoint")) {
                    options.setEndpoint(args[i+1]);
                    // Auto-set source to api if endpoint is provided
                    if (options.getSource().equals("file")) {
                        options.setSource("api");
                    }
                    i++;
                } else if (args[i].equals("--file")) {
                    options.setFilePath(args[i+1]);
                    // Auto-set source to file if file is provided
                    if (options.getSource().equals("api")) {
                        options.setSource("file");
                    }
                    i++;
                } else if (args[i].equals("--year")) {
                    options.setYear(args[i+1]);
                    i++;
                } else if (args[i].equals("--month")) {
                    options.setMonth(args[i+1]);
                    i++;
                } else if (args[i].equals("--day")) {
                    options.setDay(args[i+1]);
                    i++;
                }
            }
        }

        // Validate options
        validateOptions(options);

        return options;
    }

    /**
     * Validate the parsed options to ensure they make sense
     *
     * @param options The options to validate
     */
    private static void validateOptions(CommandLineOptions options) {
        // If source is api, ensure endpoint is provided
        if (options.getSource().equals("api") && options.getEndpoint().isEmpty()) {
            System.err.println("Error: API source specified but no endpoint provided. Use --endpoint <url>");
            System.exit(1);
        }

        // Validate date parameters format if provided
        validateDateFormat(options.getYear(), "year", "YYYY");
        validateDateFormat(options.getMonth(), "month", "MM");
        validateDateFormat(options.getDay(), "day", "DD");
    }

    /**
     * Validate the format of a date parameter
     *
     * @param value The value to validate
     * @param paramName The name of the parameter
     * @param format The expected format description
     */
    // https://www.freecodecamp.org/news/regex-for-date-formats-what-is-the-regular-expression-for-matching-dates/
    private static void validateDateFormat(String value, String paramName, String format) {
        if (paramName.equals("year")) {
            if (!value.matches("\\d{4}")) {
                System.err.println("Warning: Invalid " + paramName + " format: " + value +
                        ". Expected format is " + format);
            }
        } else if (paramName.equals("month") || paramName.equals("day")) {
            if (!value.matches("\\d{2}")) {
                System.err.println("Warning: Invalid " + paramName + " format: " + value +
                        ". Expected format is " + format);
            }
        }
    }
}