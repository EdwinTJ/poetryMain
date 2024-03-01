public class HashTable<K, O> {
    private static class HashEntryWithRecord<K, O> {
        public K key;
        public O record;
        public boolean isActive;

        public HashEntryWithRecord(K k, O r, boolean i) {
            key = k;
            record = r;
            isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private HashEntryWithRecord<K, O>[] array;
    private int occupiedCt;
    private int currentActiveEntries;

    public HashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public HashTable(int size) {
        allocateArray(size);
        doClear();
    }

    public boolean insert(K key, O record) {
        int currentPos = findPos(key);
        if (isActive(currentPos))
            return false;

        array[currentPos] = new HashEntryWithRecord<>(key, record, true);
        currentActiveEntries++;

        if (++occupiedCt > array.length / 2)
            rehash();

        return true;
    }

    public boolean contains(K key) {
        int currentPos = findPos(key);
        return isActive(currentPos);
    }

    public O find(K key) {
        int currentPos = findPos(key);
        if (!isActive(currentPos))
            return null;
        else
            return array[currentPos].record;
    }

    public boolean remove(K key) {
        int currentPos = findPos(key);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
            currentActiveEntries--;
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return currentActiveEntries;
    }

    private int findPos(K key) {
        int offset = 1;
        int currentPos = myhash(key);

        while (array[currentPos] != null && !array[currentPos].key.equals(key)) {
            currentPos += offset;
            offset += myhash2(key);
            if (currentPos >= array.length)
                currentPos -= array.length;
        }

        return currentPos;
    }

    private void rehash() {
        HashEntryWithRecord<K, O>[] oldArray = array;
        allocateArray(2 * oldArray.length);
        occupiedCt = 0;
        currentActiveEntries = 0;
        for (HashEntryWithRecord<K, O> entry : oldArray)
            if (entry != null && entry.isActive)
                insert(entry.key, entry.record);
    }

    private void allocateArray(int arraySize) {
        array = (HashEntryWithRecord<K, O>[]) new HashEntryWithRecord[nextPrime(arraySize)];
    }

    private int myhash(K key) {
        int hashVal = key.hashCode();
        hashVal %= array.length;
        if (hashVal < 0)
            hashVal += array.length;
        return hashVal;
    }

    private int myhash2(K key) {
        return 7 - (key.hashCode() % 7);
    }

    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    private void doClear() {
        occupiedCt = 0;
        for (int i = 0; i < array.length; i++)
            array[i] = null;
    }

    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;
        for (; !isPrime(n); n += 2)
            ;
        return n;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;
        if (n == 1 || n % 2 == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }

    public String toString (int limit){
        StringBuilder sb = new StringBuilder();
        int ct=0;
        for (int i=0; i < array.length && ct < limit; i++){
            if (array[i]!=null && array[i].isActive) {
                sb.append( i + ": " + array[i].record.toString() + "\n" );
                ct++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();

        // Insert some elements
        table.insert("one", 1);
        table.insert("two", 2);
        table.insert("three", 3);
        table.insert("four", 4);
        table.insert("five", 5);

        // Check size
        System.out.println("Size after insertions: " + table.size());

        // Test contains
        System.out.println("Contains 'two': " + table.contains("two"));
        System.out.println("Contains 'six': " + table.contains("six"));

        // Test find
        System.out.println("Find 'three': " + table.find("three"));
        System.out.println("Find 'six': " + table.find("six"));

        // Test remove
        System.out.println("Removing 'four': " + table.remove("four"));
        System.out.println("Removing 'six': " + table.remove("six"));

        // Check size after removal
        System.out.println("Size after removals: " + table.size());
    }
}
