import java.io.PrintWriter;

/**
 * @author Matthew Kelley
 * 
 *         Array based implementation of a hashtable.
 */
abstract class Hashtable<T> {
    protected int capacity; // number of unique objects in the table
    protected int tableSize; // the number of slots in the table
    protected HashObject<T>[] table; // the array
    protected String fileName; // file name for output
    protected int probes; // Number of probes for all inserts
    protected int totalInserts; // Number of total objects in the table
    protected int totalDuplicates; // Number of duplicate objects added to the table
    protected StringBuilder insertLogger; // StringBuilder for logging inserts

    @SuppressWarnings("unchecked")
    public Hashtable(int tableSize, String fileName) {
        this.tableSize = tableSize;
        this.table = new HashObject[tableSize];
        this.capacity = 0;
        this.probes = 0;
        this.fileName = fileName;
        this.totalInserts = 0;
        this.insertLogger = new StringBuilder();
    }

    /**
     * Creates a formatted string to be inserted into the insert logger.
     * 
     * @param object    - the object inserted
     * @param index     - the index of the object in the table
     * @param numProbes - the number of probes for the insert
     * @param tableName - the name of the table
     */
    public void addToInsertLog(HashObject<T> object, int index, int numProbes, String tableName) {
        String log = String.format("%s[%d]: HashObject(%s), Number Of Probes: %d\n", tableName, index,
                object.toString(), numProbes);
        this.insertLogger.append(log);
    }

    /**
     * Outputs the insert logger to stdout.
     */
    public void printInsertLog() {
        System.out.println(insertLogger.toString());
    }

    /**
     * Gets the capacity of the table (number of unique objects).
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the number of probes for all inserts.
     */
    public int getProbes() {
        return probes;
    }

    /**
     * Gets the total number of duplicate objects in the table.
     */
    public int getDuplicateInserts() {
        return totalDuplicates;
    }

    /**
     * Sets the total number of objects in the table.
     */
    public void setDuplicateInserts(int totalDuplicates) {
        this.totalDuplicates = totalDuplicates;
    }

    /**
     * Gets the total number of objects in the table.
     */
    public int getTotalInserts() {
        return totalInserts;
    }

    /**
     * Gets the average number of probes for the table.
     */
    public double getAverageProbes() {
        try {
            return (double) probes / (double) capacity;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    /**
     * Outputs the tables content to a file.
     */
    public void outputTableToFile() {
        try {
            PrintWriter writer = new PrintWriter(this.fileName, "UTF-8");
            for (int i = 0; i < tableSize; i++) {
                if (table[i] != null) {
                    String line = String.format("table[%d]: %s", i, table[i].toString());
                    writer.println(line);
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    /**
     * Utility method to find the modulo of dividend and divsor.
     * 
     * @param dividend - the dividend
     * @param divisor  - the divisor
     */
    protected int positiveMod(int dividend, int divisor) {
        int value = dividend % divisor;
        if (value < 0)
            value += divisor;
        return value;
    }

    /**
     * Gets the tables load factor by dividing the number of unique elements by the
     * table size.
     */
    public double getLoadFactor() {
        return (double) capacity / (double) tableSize;
    }

    /**
     * Insert a new object into the table.
     * 
     * @param newObject - the object to be inserted
     */
    abstract void insert(HashObject<T> newObject);
}
