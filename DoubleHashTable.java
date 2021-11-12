/**
 * @author Matthew Kelley
 * 
 *         Array based implementation of a hashtable. Using a primary and
 *         secondary hashing function for generating the hash.
 */
public class DoubleHashTable<T> extends Hashtable<T> {

    public DoubleHashTable(int tableSize, String fileName) {
        super(tableSize, fileName);
    }

    private int primaryHash(T key) {
        return super.positiveMod(key.hashCode(), super.tableSize);
    }

    private int secondaryHash(T key) {
        return 1 + super.positiveMod(key.hashCode(), super.tableSize - 2);
    }

    @Override
    void insert(HashObject<T> newObject) {
        // insert into hash table
        T key = (T) newObject.getKey();
        int primaryHashValue = this.primaryHash(key);
        int secondaryHashValue = this.secondaryHash(key);
        int cursor = 0;
        int index = 0;
        while (cursor < super.tableSize) {
            index = super.positiveMod(primaryHashValue + (cursor * secondaryHashValue), super.tableSize);
            int numProbesForInsert = cursor + 1;
            if (super.table[index] == null) {
                newObject.setProbeCount(numProbesForInsert);
                super.table[index] = newObject;
                super.totalInserts++;
                super.capacity++;
                super.probes += numProbesForInsert;
                super.addToInsertLog(newObject, index, numProbesForInsert, "DoubleHashTable");
                return;
            } else if (super.table[index].equals(newObject)) {
                super.setDuplicateInserts(super.getDuplicateInserts() + 1);
                super.table[index].setFrequency(super.table[index].getFrequency() + 1);
                super.totalInserts++;
                super.addToInsertLog(newObject, index, numProbesForInsert, "DoubleHashTable");
                return;
            } else {
                cursor++;
            }
        }
    }

}
