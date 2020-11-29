import java.util.HashSet;
import java.util.Set;

public class BinarySearchTree <T extends Comparable<T>> {

    // Number of nodes in this tree
    private int nodeCount = 0;

    // Root of the tree
    private Node root = null;

    // Internal node containing node references
    // and actual node data
    private class Node {
        T data;
        Node left, right;
        public Node (Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    // Check if tree is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Get the number of nodes in this tree
    public int size() {
        return nodeCount;
    }

    public boolean add(T elem) {

        // Check if elem already exists in tree,
        // if it does, ignore adding it
        if(contains(elem)) return false;
        // Otherwise add the element to the binary tree
        else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    // private method to recursively add Node to the tree
    private Node add(Node node, T elem) {

        // Base case: found the leaf Node
        if(node == null) {
            node = new Node(null, null, elem);
        } else {
            if(elem.compareTo(node.data) < 0) return add(node.left, elem);
            else return add(node.right, elem);
        }

        return node;
    }

    // Remove the value from tree if it exist
    public boolean remove(T elem) {

        // Make sure the node exist before we remove it
        if(contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }

        return false;
    }

    private Node remove(Node node, T elem) {

        if(node == null) return null;

        int cmp = elem.compareTo(node.data);

        // Dig left subtree
        if(cmp < 0) {
            node.left = remove(node.left, elem);
        } else if(cmp > 0) {
            node.right = remove(node.right, elem);
        } else {
            if(node.left == null) {
                Node rightChild = node.right;
                node.data = null;
                node = null;
                return rightChild;
            } else if(node.right == null) {
                Node leftChild = node.left;
                node.data = null;
                node = null;
                return leftChild;
            } else {
                Node tmp = digLeft(node.right);

                node.data = tmp.data;

                node.right = remove(node.right, tmp.data);
            }
        }
        return node;
    }

    // Helper method to find leftmost node
    private Node digLeft(Node node) {
        Node cur = node;
        while(cur.left != null)
            cur = cur.left;
        return cur;
    }

    // Helper method to find rightmost node
    private Node digRight(Node node) {
        Node cur = node;
        while(cur.right != null)
            cur = cur.right;
        return cur;
    }

    public boolean contains(T elem) {
        return contains(root, elem);
    }

    // private recursive method to find if the node is in the tree
    private boolean contains(Node node, T elem) {

        // Base case: reached bottom, node not found
        if(node == null) return false;

        int cmp = elem.compareTo(node.data);

        if(cmp < 0) return contains(node.left, elem);
        else if (cmp > 0) return contains(node.right, elem);
        else return true;
    }

    // Returns the height of the tree
    public int height() {
        return height(root);
    }

    // Recursive helper method to compute the height of the tree
    private int height(Node node) {
        if(node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }


}
