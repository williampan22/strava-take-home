import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to display analysis results
 */
public class ResultsPrinter {
    /**
     * Print the top 5 largest collections by size
     *
     * @param collections List of LogInfoDTO objects
     */
    public static void printLargestCollections(List<LogInfoDTO> collections) {
        System.out.println();
        System.out.println("Printing largest data collections by storage size");

        // Create a copy of the list to avoid modifying the original
        List<LogInfoDTO> sortedCollections = new ArrayList<>(collections);

        // Sort by size (descending) using the static comparison method
        Collections.sort(sortedCollections, LogInfoDTO::compareBySize);

        // Print top 5
        printTopCollectionsBySize(sortedCollections, 5);
    }

    /**
     * Print the top 5 collections with most partitions
     *
     * @param collections List of LogInfoDTO objects
     */
    public static void printMostPartitions(List<LogInfoDTO> collections) {
        System.out.println();
        System.out.println("Printing largest data collections by partition count");

        // Create a copy of the list to avoid modifying the original
        List<LogInfoDTO> sortedCollections = new ArrayList<>(collections);

        // Sort by partition count (descending) using the static comparison method
        Collections.sort(sortedCollections, LogInfoDTO::compareByPartitions);

        // Print top 5
        printTopCollectionsByPartitions(sortedCollections, 5);
    }

    /**
     * Print the top 5 least balanced collections
     *
     * @param collections List of LogInfoDTO objects
     */
    public static void printLeastBalanced(List<LogInfoDTO> collections) {
        System.out.println();
        System.out.println("Printing least balanced data collections");

        // Create a copy of the list to avoid modifying the original
        List<LogInfoDTO> sortedCollections = new ArrayList<>(collections);

        // Sort by balance ratio (descending) using the static comparison method
        Collections.sort(sortedCollections, LogInfoDTO::compareByBalanceRatio);

        // Print top 5
        printTopCollectionsByBalanceRatio(sortedCollections, 5);
    }

    // The rest of the methods remain the same
    private static void printTopCollectionsBySize(List<LogInfoDTO> sortedCollections, int limit) {
        int count = 0;
        for (LogInfoDTO collection : sortedCollections) {
            if (count >= limit) break;

            System.out.println("Collection: " + collection.getName());
            System.out.println("Size: " + collection.getSizeGB() + " GB");
            count++;
        }
    }

    private static void printTopCollectionsByPartitions(List<LogInfoDTO> sortedCollections, int limit) {
        int count = 0;
        for (LogInfoDTO collection : sortedCollections) {
            if (count >= limit) break;

            System.out.println("Collection: " + collection.getName());
            System.out.println("Partitions: " + collection.getPartitions());
            count++;
        }
    }

    private static void printTopCollectionsByBalanceRatio(List<LogInfoDTO> sortedCollections, int limit) {
        int count = 0;
        for (LogInfoDTO collection : sortedCollections) {
            if (count >= limit) break;

            System.out.println("Collection: " + collection.getName());
            System.out.println("Size: " + collection.getSizeGB() + " GB");
            System.out.println("Partitions: " + collection.getPartitions());
            System.out.println("Balance Ratio: " + collection.getBalanceRatio());
            System.out.println("Recommended partition count is " + collection.getRecommendedPartitions());
            count++;
        }
    }
}