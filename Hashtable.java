import java.io.PrintWriter;

abstract class Hashtable<T> {
    protected int capacity;
    protected int tableSize;
    protected HashObject<T>[] table;
    protected String fileName;
    protected int probes;
    protected int totalInserts;
    protected int totalDuplicates;
    protected StringBuilder insertLogger;

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

    public void printInsertLog() {
        System.out.println(insertLogger.toString());
    }

    public int getCapacity() {
        return capacity;
    }

    public int getProbes() {
        return probes;
    }

    public int getDuplicateInserts() {
        return totalDuplicates;
    }

    public void setDuplicateInserts(int totalDuplicates) {
        this.totalDuplicates = totalDuplicates;
    }

    public int getTotalInserts() {
        return totalInserts;
    }

    public float getAverageProbes() {
        try {
            return (float) probes / totalInserts;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

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

    protected int positiveMod(int dividend, int divisor) {
        int value = dividend % divisor;
        if (value < 0)
            value += divisor;
        return value;
    }

    public float getLoadFactor() {
        return (float) capacity / tableSize;
    }

    abstract void insert(HashObject<T> newObject);
}
