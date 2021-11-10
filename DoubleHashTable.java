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
            index = (primaryHashValue + (cursor * secondaryHashValue)) % super.tableSize;
            if (index < 0) {
                index += super.tableSize;
            }
            if (super.table[index] == null) {
                int numProbesForInsert = cursor + 1;
                newObject.setProbeCount(numProbesForInsert);
                super.table[index] = newObject;
                super.totalInserts++;
                super.capacity++;
                super.probes += numProbesForInsert;
                return;
            } else if (super.table[index].equals(newObject)) {
                super.setDuplicateInserts(super.getDuplicateInserts() + 1);
                int numProbesForInsert = cursor + 1;
                super.table[index].setFrequency(super.table[index].getFrequency() + 1);
                super.totalInserts++;
                super.probes += numProbesForInsert;
                return;
            }
            cursor++;
        }
    }

}
