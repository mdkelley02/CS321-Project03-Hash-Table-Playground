public class LinearTable<T> extends Hashtable<T> {
    public LinearTable(int tableSize, String fileName) {
        super(tableSize, fileName);
    }

    private int primaryHash(T key) {
        return super.positiveMod(key.hashCode(), super.tableSize);
    }

    // linear probe insert into table of type HashObject
    public void insert(HashObject<T> newObject) {
        // insert into hash table
        T key = (T) newObject.getKey();
        int primaryHashValue = this.primaryHash(key);
        int cursor = 0;
        int index = 0;
        while (cursor < super.tableSize) {
            index = super.positiveMod(primaryHashValue + cursor, super.tableSize);
            int numProbesForInsert = cursor + 1;
            if (super.table[index] == null) {
                newObject.setProbeCount(numProbesForInsert);
                super.table[index] = newObject;
                super.totalInserts++;
                super.capacity++;
                super.probes += numProbesForInsert;
                return;
            } else if (super.table[index].equals(newObject)) {
                super.setDuplicateInserts(super.getDuplicateInserts() + 1);
                super.table[index].setFrequency(super.table[index].getFrequency() + 1);
                super.totalInserts++;
                super.probes += numProbesForInsert;
                return;
            } else {
                cursor++;
            }
        }
    }
}
