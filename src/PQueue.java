/**
 * A min priority queue implementation using  a binary heap
 * **/



import java.util.*;

public class PQueue <T extends Comparable<T>> {

    // The number of elements curently inside the heap
    private int heapSize = 0;

    // The internal capacity of the heap
    private int heapCapacity = 0;

    // A dynamic list to track the elements inside the heap
    private List <T> heap = null;

    // This map keeps track of the possible indecies a particular
    // node value is found in the heap. Having this mapping lets
    // us have O(log(n)) removals and O(1) element containment check
    // at the cost of some additional space and minor overhead
    private Map <T, TreeSet<Integer>> map = new HashMap<>();

    // Construct an initially empty priority queue
    public PQueue() {
        this(1);
    }

    // Construct a priority queue with an initial capacity
    public PQueue(int sz) {
        heap = new ArrayList<>(sz);
    }

    // Construct a priority queue using heapify in O(n) time
    public PQueue (T[] elems) {

        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<T>(heapCapacity);

        // Place all elements in heap
        for(int i = 0; i < heapSize; i++) {
            mapAdd(elems[i], i);
            heap.add(elems[i]);
        }

        // Heapify process, O(n)
        for(int i = Math.max(0, (heapSize/2)-1); i >= 0; i--)
            sink(i);
    }

    // Priority queue construction, O(nlog(n))
    public PQueue (Collection <T> elems) {
        this(elems.size());
        for(T elem: elems) add(elem);
    }

    // Returns true/false depending on if the priority queue is empty
    public boolean isEmpty() {
        return heapSize == 0;
    }

    // Clears everything inside the heap, O(n)
    public void clear() {
        for(int i=0; i < heapCapacity; i++)
            heap.set(i, null);
        heapSize = 0;
        map.clear();
    }

    // Return the size of the heap
    public int size() {
        return heapSize;
    }

    // Returns the value of the element with the lowest
    // priority int this priority queue. If the priority
    // queue is empty null is returned
    public T peek() {
        if (isEmpty()) return null;
        return heap.get(0);
    }

    // Removes the root of the heap
    public T poll() {
        return removeAt(0);
    }

    // Test if an element is the heap, O(1)
    public boolean contains(T elem) {

        // Map lookup to check containment, O(1)
        if(elem == null) return false;
        return map.containsKey(elem);

        // Linear scan to check containment, O(n)
        // for(int i=0; i < heapSize; i++)
        //      if(heap.get(i).equals(elem))
        //          return true;
        // return false;
    }


    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    public void add(T elem) {

        if(elem == null) throw new IllegalArgumentException();

        if(heapSize < heapCapacity)
            heap.set(heapSize, elem);
        else {
            heap.add(elem);
            heapCapacity++;
        }

        mapAdd(elem, heapSize);

        swim(heapSize);
        heapSize++;
    }

    // Test if the value of node i <= node j
    // This method assumes that i & j are valid indecies, O(1)
    private boolean less(int i, int j) {

        T node1 = heap.get(i);
        T node2 = heap.get(j);

        return node1.compareTo(node2) <= 0;
    }

    // Bottom up node swim, O(log(n))
    private void swim(int k) {

        // Grab the index of the next parent node of k
        int parent = (k-1) / 2;

        // Keep swimming until we have not reached the
        // root and while we are less than our parent
        while(k > 0 && less(k, parent)) {

            // Exchange k with parent
            swap( parent, k);
            k = parent;

            // Grab the index of the next parent
            parent = (k-1) / 2;
        }
    }

    // Top down node sink, O(log(n))
    private void sink(int k) {

        while(true) {
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left; // Assume left is the smallest node of two children

            // Find which is smaller, left or right
            // If right is smallest, set smallest to right
            if (right < heapSize && less(right, smallest))
                smallest = right;

            if (left >= heapSize || less(k, smallest))
                break;

            swap(k, smallest);
            k = smallest;
        }
    }

    // Swap two nodes, Assumes i & j are valid, O(1)
    private void swap(int i, int j) {

        T i_elem = heap.get(i);
        T j_elem = heap.get(j);

        heap.set(i, j_elem);
        heap.set(j, i_elem);
        mapSwap(i_elem, j_elem, j, i);
    }


    // Removes a particular element in the heap, O(log(n))
    public boolean remove(T element) {
        if (element == null) return false;

        // Linear removal via search
        // for(int i=0; i < heapSize; i++)
        //      if(element.equals(heap.get(i))) {
        //          removeAt(i);
        //          return true;
        //      }
        // }

        // Logarithmic removal with map, O(log(n))
        Integer index = mapGet(element);
        System.out.println(index);
        if(index != null) removeAt(index);
        return index != null;
    }

    // Remove a node at particular index, O(log(n))
    private T removeAt(int i) {

        if(isEmpty()) return null;

        heapSize--;
        T removed_data = heap.get(i);
        swap(i, heapSize);

        // Obliterate the value
        heap.set(heapSize, null);
        mapRemove(removed_data, heapSize);

        // Removed last element
        if(i == heapSize) return removed_data;

        T elem = heap.get(i);

        // Try sinking element
        sink(i);

        // If sinking did not work, try swimming
        if ( heap.get(i).equals(elem))
            swim(i);

        return removed_data;
    }

    // Recursively checks if the heap is a min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is being maintained
    // Call this method with k = 0 to start at root
    public boolean isMinHeap(int k) {

        // If we are outside the bounds of the heap, return true
        if (k >= heapSize) return true;

        int left = k*2 +1;
        int right = k*2 + 2;

        // Make sure that node k is less then both it's
        // children if they exist
        // otherwise return false to indicate that the heap is invalid
        if(left < heapSize && !less(k, left)) return false;
        if(right < heapSize && !less(k, right)) return false;

        // Recurse on both children to make sure they are also valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }

    // Add a node value and it's index to the map
    private void mapAdd(T value, int index) {

        TreeSet <Integer> set = map.get(value);         // Set of indecies

        // New value being inserted into the map
        if (set == null) {

            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);        // Check this line

        // Value already exists in map
        } else set.add(index);     // I think it should go below
    }

    // Removes the index at a given value, O(log(n))
    private void mapRemove(T value, int index) {
        TreeSet <Integer> set = map.get(value);
        set.remove(index); // TreeSets take O(log(n)) time for removal
        if (set.size() == 0) map.remove(value);
    }

    // Extract an index position for the given value
    // NOTE: If a value exists multiple times in the heap the highest
    // index is returned (this is arbitrarily been chosen)
    private Integer mapGet(T value) {
        TreeSet<Integer> set = map.get(value);
        if (set != null) return set.last();
        return null;
    }

    // Exchange index of two nodes internally within the map
    private void mapSwap(T val1, T val2, int val1Index, int val2Index) {

        Set <Integer> set1 = map.get(val1);
        Set <Integer> set2 = map.get(val2);

        set1.remove(val1Index);
        set2.remove(val2Index);

        set1.add(val2Index);
        set2.add(val1Index);
    }

    @Override public String toString() {
        return heap.toString();
    }


}
