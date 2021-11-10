public class HashObject<T> {
    private final T key;
    private int frequency;
    private int probeCount;

    public HashObject(T key) {
        this.key = key;
        this.frequency = 0;
        this.probeCount = 0;
    }

    public String toString() {
        return String.format("%s %d %d", this.getKey().toString(), this.frequency, this.probeCount);
    }

    public Object getKey() {
        return key;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getProbeCount() {
        return probeCount;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setProbeCount(int probeCount) {
        this.probeCount = probeCount;
    }

    @Override
    public boolean equals(Object other) {
        return (this.getClass() == other.getClass()
                && this.getKey().hashCode() == ((HashObject<T>) other).getKey().hashCode());
    }
}
