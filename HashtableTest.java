import java.util.Random;
import java.io.*;

public class HashtableTest {

    public static void main(String[] args) {
        // Validate command line arguments, if any arguments fail validation, program
        // will exit with non-zero exit status.
        validateArguments(args);
        // parse command line arguments to their respective data types
        int inputType = Integer.parseInt(args[0]);
        float loadFactor = Float.parseFloat(args[1]);
        int debugLevel = args.length == 3 ? Integer.parseInt(args[2]) : -1;
        // Run driver class for program
        new Run(inputType, loadFactor, debugLevel);
    }

    public static void validateArguments(String[] args) {
        int numArgs = args.length;
        if (numArgs == 2) {
            try {
                int inputType = Integer.parseInt(args[0]);
                float loadFactor = Float.parseFloat(args[1]);
                if (inputType < 1 || inputType > 3) {
                    throw new IllegalArgumentException("Invalid input type");
                }
                if (loadFactor < 0 || loadFactor > 1) {
                    throw new IllegalArgumentException("Invalid load factor");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument type");
                Run.printUsage();
                System.exit(1);
            } catch (IllegalArgumentException e) {
                Run.printUsage();
                System.exit(1);
            }
        } else if (numArgs == 3) {
            try {
                int inputType = Integer.parseInt(args[0]);
                float loadFactor = Float.parseFloat(args[1]);
                int debugLevel = Integer.parseInt(args[2]);
                if (inputType < 1 || inputType > 3) {
                    throw new IllegalArgumentException("Invalid input type");
                }
                if (loadFactor < 0 || loadFactor > 1) {
                    throw new IllegalArgumentException("Invalid load factor");
                }
                if (debugLevel < 0 || debugLevel > 2) {
                    throw new IllegalArgumentException("Invalid debug level");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument type");
                Run.printUsage();
                System.exit(1);
            } catch (IllegalArgumentException e) {
                Run.printUsage();
                System.exit(1);
            }
        } else {
            Run.printUsage();
            throw new IllegalArgumentException("Invalid number of arguments");
        }
    }

}

class Run {
    private final int STARTING_TABLE_RANGE = 95500;
    private final int ENDING_TABLE_RANGE = 96000;
    private int inputType;
    private float loadFactor;
    private int debuglevel;
    private DoubleHashTable doubleHashTable;
    private LinearTable linearTable;
    private int tableSize;

    public Run(int inputType, float loadFactor, int debugLevel) {
        this.inputType = inputType;
        this.loadFactor = loadFactor;
        this.debuglevel = debugLevel;
        this.tableSize = TwinPrimeGenerator.generateSmallestTwinPrimeInRange(STARTING_TABLE_RANGE, ENDING_TABLE_RANGE);

        switch (this.inputType) {
        case 1 -> {
            this.doubleHashTable = new DoubleHashTable<Integer>(tableSize, "linear-dump.txt");
            this.linearTable = new LinearTable<Integer>(tableSize, "double-dump.txt");
        }
        case 2 -> {
            this.doubleHashTable = new DoubleHashTable<Long>(tableSize, "linear-dump.txt");
            this.linearTable = new LinearTable<Long>(tableSize, "double-dump.txt");
        }
        case 3 -> {
            this.doubleHashTable = new DoubleHashTable<String>(tableSize, "linear-dump.txt");
            this.linearTable = new LinearTable<String>(tableSize, "double-dump.txt");
        }
        default -> {
            printUsage();
            System.exit(-1);
        }
        }
        insertHashObjects();
        switch (this.debuglevel) {
        case 0 -> {
            printTablesSummary();
        }
        case 1 -> {
            printTablesSummary();
            outputTablesToFile();
        }
        case 2 -> {
            printTablesSummary();
            outputTableInserts();
        }
        default -> {
            printTablesSummary();
        }
        }
    }

    private void printTablesSummary() {
        String dataSourceString;
        // get data source type from debug level
        if (this.inputType == 1) {
            dataSourceString = "java.util.Random";
        } else if (this.inputType == 2) {
            dataSourceString = "System.currentTimeMillis()";
        } else if (this.inputType == 3) {
            dataSourceString = "word-list.txt";
        } else {
            dataSourceString = "Unknown";
        }

        System.out.printf("\nHashtableTest: Twin prime table size found in the range [%d, %d]: %d\n",
                STARTING_TABLE_RANGE, ENDING_TABLE_RANGE, this.tableSize);
        System.out.printf("\nHashtableTest: Data source type --> %s\n", dataSourceString);

        System.out.printf("\nHashtableTest: Using Linear Hashing....\n");
        System.out.printf("HashtableTest: Input %d elements, of which %d duplicates\n",
                this.linearTable.getTotalInserts(), this.linearTable.getDuplicateInserts());
        System.out.printf("HashtableTest: load factor = %.2f, Avg. no. of probes %f\n",
                this.linearTable.getLoadFactor(), this.linearTable.getAverageProbes());

        System.out.printf("\nHashtableTest: Using Double Hashing....\n");
        System.out.printf("HashtableTest: Input %d elements, of which %d duplicates\n",
                this.doubleHashTable.getTotalInserts(), this.doubleHashTable.getDuplicateInserts());
        System.out.printf("HashtableTest: load factor = %.2f, Avg. no. of probes %f\n",
                this.doubleHashTable.getLoadFactor(), this.doubleHashTable.getAverageProbes());
    }

    private void outputTableInserts() {
        this.linearTable.printInsertLog();
        this.doubleHashTable.printInsertLog();
    }

    private void outputTablesToFile() {
        this.linearTable.outputTableToFile();
        this.doubleHashTable.outputTableToFile();
    }

    private void insertHashObjects() {
        switch (this.inputType) {
        case 1 -> {
            // get random integer
            Random random = new Random();

            for (Integer i = 0; this.doubleHashTable.getLoadFactor() < loadFactor; i++) {
                Integer key = random.nextInt();
                HashObject<Integer> hashObject = new HashObject<Integer>(key);
                this.doubleHashTable.insert(hashObject);
                this.linearTable.insert(hashObject);
            }
        }
        case 2 -> {

            for (Integer i = 0; this.doubleHashTable.getLoadFactor() < loadFactor; i++) {
                Long key = System.currentTimeMillis();
                HashObject<Long> hashObject = new HashObject<Long>(key);
                this.doubleHashTable.insert(hashObject);
                this.linearTable.insert(hashObject);
            }

        }
        case 3 -> {
            try {
                FileReader fileReader = new FileReader("word-list.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String key;
                while (((key = bufferedReader.readLine()) != null)
                        && this.doubleHashTable.getLoadFactor() < loadFactor) {
                    HashObject<String> hashObject = new HashObject<String>(key);
                    this.doubleHashTable.insert(hashObject);
                    this.linearTable.insert(hashObject);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        default -> {

        }
        }
    }

    public static void printUsage() {
        System.out.println("Usage:\n java HashtableTest <input type> <load factor> [<debug level>]");
        System.out.println("input type = 1 for random numbers, 2 for system time, 3 for word list");
        System.out.println("debug = 0 ==> print summary of experiment");
        System.out.println("debug = 1 ==> save the two hash tables to a file at the end");
        System.out.println("debug = 2 ==> print debugging output for each insert");
    }
}
