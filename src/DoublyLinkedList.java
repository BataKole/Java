public class DoublyLinkedList<T> implements Iterable<T> {

    private int size;
    private Node<T> head = null;
    private Node<T> tail = null;

    // Internal node class to represent data
    private class Node <T> {
        T data;
        Node <T> prev, next;
        public Node(T data, Node <T> prev, Node <T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
        @Override public String toString() {
            return data.toString();
        }
    }

    // Empty this linked list O(n)
    public void clear() {
        Node <T> trav = head;
        while (trav != null) {
            Node <T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    // Return the size of this linked list
    public int size() {
        return size;
    }

    // Is this list empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Add an element to the tail of linked list (1)
    public void add(T elem) {
        addLast(elem);
    }

    // Add an element to the beginning of this linked list (1)
    public void addFirst(T elem) {
        // If linked list is empty
        if(isEmpty()) {
            head = tail = new Node <T> (elem, null, null);
        } else {
            head.prev = new Node <T> (elem, null, head);
            head = head.prev;
        }
        size++;
    }

    // Add an element to the end of this linked list (1)
    public void addLast(T elem) {
        // If linked list is empty
        if(isEmpty()) {
            head = tail = new Node <T> (elem, null, null);
        } else {
            tail.next = new Node <T> (elem, tail, null);
            tail = tail.next;
        }
        size++;
    }


    // Check the value of the first Node if it exists O(1)
    public T peekFirst() {
        if(isEmpty()) throw new RuntimeException("Empty List");
        return head.data;
    }

    // Check the value of the last node if it exists O(1)
    public T peekLast() {
        if(isEmpty()) throw new RuntimeException("Empty List");
        return tail.data;
    }

    // Remove the first value at the head of the linked list O(1)
    public T removeFirst() {

        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the head and move
        // the head pointer forwards one node
        T data = head.data;
        head = head.next;
        --size;

        // If the list is empty, set the tail to null as well
        if (isEmpty()) tail = null;

        // Do a memory clean of the previous node
        head.prev = null;

        // Return the data from the first node that we just deleted
        return data;
    }

    // Remove the last value at the tail of the linked list O(1)
    public T removeLast() {

        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the tail and move
        // the tail pointer backwards one node
        T data = tail.data;
        tail = tail.prev;
        --size;

        // If list is empty, set the head to null as well
        if (isEmpty()) head = null;

        // Do a memory clean of the next node
        tail.next = null;

        // Return the data from the last node that we just deleted
        return data;
    }

    // Remove an arbitrary node from the linked list O(1)
    private T remove(Node<T> node) {

        // If the node to remove is either at the
        // head or at the tail handle those independently
        if (node.prev == null) removeFirst();
        if (node.next == null) removeLast();

        // Make pointers of the adjacent nodes skip over the node
        node.prev.next = node.next;
        node.next.prev = node.prev;

        // Temporary store the data we want to return
        T data = node.data;

        // Memory cleanup
        node.data = null;
        node = node.prev = node.next = null;

        --size;

        // Return the data of the node we just removed
        return data;
    }

    // Remove a node at a particular index O(n)
    public T removeAt(int index) {

        // Make sure the index provided is valid
        if(index < 0 || index >= size) throw new IllegalArgumentException();

        int i;
        Node <T> trav;

        // Search from the front of the list
        if (index < size/2) {
            for(i = 0, trav = head; i != index; i++)
                trav = trav.next;

        // Search from the back of the list
        } else
            for(i = size-1, trav = tail; i != index; i--)
                trav = trav.prev;

        return remove(trav);
    }

    // Remove a particular value in the linked list O(n)
    public boolean remove(Object obj) {

        Node <T> trav = head;

        // Support searching for null
        if(obj == null) {
            for(trav = head; trav != null; trav = trav.next) {
                if(trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        // Search for non null object
        } else {
            for(trav = head; trav != null; trav = trav.next) {
                if(obj.equals(trav.data)) {
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    // Find the index of the particular value in the linked list O(n)
    public int indexOf(Object obj) {

        int index = 0;
        Node <T> trav = head;

        // Support searching for null
        if (obj == null) {
            for (trav = head; trav != null; trav = trav.next, index++)
                if(trav.data == null)
                    return index;

        // Search for non null element
        } else {
            for (trav = head; trav != null; trav = trav.next, index++)
                if(obj.equals(trav.data))
                    return index;
        }
        return -1;
    }

    // Check if the value is contained in the linked list O(n)
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override public java.util.Iterator <T> iterator () {
        return new java.util.Iterator<T> () {
            private Node <T> trav = head;
            @Override public boolean hasNext() {
                return trav.next != null;
            }
            @Override public T next () {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node <T> trav = head;
        while (trav != null) {
            System.out.println(trav.toString());
            if(trav.next == null) {
                sb.append(trav.data);
                break;
            }
            sb.append(trav.data + ", ");
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
