/**
 * Data class to store and calculate metrics for a log storage collection
 * Implements Comparable for natural ordering (by size)
 */
public class LogInfoDTO implements Comparable<LogInfoDTO> {
    // Constants for calculations
    private static final double BYTES_IN_GB = 1000000000.0; // Bytes in a GB
    private static final int OPTIMAL_GB_PER_PARTITION = 30; // Target ratio (1 partition per 30GB)

    // Basic properties
    private String name;
    private long sizeBytes;
    private int partitions;

    // Calculated properties
    private double sizeGB;
    private int balanceRatio;
    private int recommendedPartitions;

    /**
     * Constructor for creating a LogInfoDTO object
     *
     * @param name The name of the collection
     * @param sizeBytes The size of the collection in bytes
     * @param partitions The number of partitions
     */
    public LogInfoDTO(String name, long sizeBytes, int partitions) {
        this.name = name;
        this.sizeBytes = sizeBytes;
        this.partitions = partitions;

        // Calculate derived values
        this.sizeGB = calculateGB(sizeBytes);
        this.balanceRatio = calculateBalanceRatio();
        this.recommendedPartitions = calculateRecommendedPartitions();
    }

    /**
     * Convert bytes to GB and round to 2 decimal places
     *
     * @param bytes Size in bytes
     * @return Size in gigabytes
     */
    private double calculateGB(long bytes) {
        double gb = bytes / BYTES_IN_GB;
        return Math.round(gb * 100.0) / 100.0;
    }

    /**
     * Calculate GB per partition ratio
     *
     * @return The balance ratio
     */
    private int calculateBalanceRatio() {
        if (partitions == 0) return 0;
        return (int) Math.round(sizeGB / partitions);
    }

    /**
     * Calculate recommended partition count based on optimal ratio
     *
     * @return The recommended number of partitions
     */
    private int calculateRecommendedPartitions() {
        return (int) Math.ceil(sizeGB / OPTIMAL_GB_PER_PARTITION);
    }

    // Compare by size (for natural ordering)
    @Override
    public int compareTo(LogInfoDTO other) {
        return Double.compare(this.sizeGB, other.sizeGB);
    }

    // Compare by size descending
    public static int compareBySize(LogInfoDTO c1, LogInfoDTO c2) {
        return Double.compare(c2.sizeGB, c1.sizeGB);
    }

    // Compare by partitions descending
    public static int compareByPartitions(LogInfoDTO c1, LogInfoDTO c2) {
        return Integer.compare(c2.partitions, c1.partitions);
    }

    // Compare by balance ratio descending
    public static int compareByBalanceRatio(LogInfoDTO c1, LogInfoDTO c2) {
        return Integer.compare(c2.balanceRatio, c1.balanceRatio);
    }

    // Getters
    public String getName() {
        return name;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public int getPartitions() {
        return partitions;
    }

    public double getSizeGB() {
        return sizeGB;
    }

    public int getBalanceRatio() {
        return balanceRatio;
    }

    public int getRecommendedPartitions() {
        return recommendedPartitions;
    }
}