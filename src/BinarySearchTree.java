public class BinarySearchTree <T extends Comparable<T>>{

    // Tracks the number of nodes in this BST
    private int nodeCount = 0;

    // This BST is rooted tree so we maintain the handle on the root node
    private Node root = null;

    // Internal Node containting node references
    // and the actual Node data
    private class Node {
        T data;
        Node left, right;
        public Node (Node left, Node right, T elem) {
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }

    // Check if binary tree is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Get the number of nodes in this binary tree
    public int size() {
        return nodeCount;
    }

    // Add an element to this binary tree. Returns  true
    // if insertion was successful
    public boolean add(T elem) {

        // Check if the value already exists int
        // this binary tree, if it does ignore adding it
        if(contains(elem)) {
            return false;

            // Otherwise add this element to the binary tree
        } else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    // Private method to recursively add a value in the binary tree
    private Node add(Node node, T elem) {

        // Base case: found a leaf node
        if(node == null) {
            node = new Node(null,null, elem);
        } else {
            // Place nodes with lower values to left subtree
            if(elem.compareTo(node.data) < 0) {
                node.left = add(node.left, elem);
            // Place ndoes with higher or equal values to right subtree
            } else {
                node.right = add(node.right, elem);
            }
        }
        return node;
    }

    // Remove a value from this binary tree, if it exists
    public boolean remove(T elem) {

        // Make sure the node that we want to remove
        // actually exists before we remove it
        if (contains(elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T elem) {

        if (node == null) return null;

        int cmp = elem.compareTo(node.data);

        // Search left subtree if the value we want to remove
        // is smaller then the current node value
        if (cmp < 0) {
            node.left = remove(node.left, elem);

            // Search right subtree if the value we want to removce
            // is greater then the current node value
        } else if (cmp > 0) {
            node.right = remove(node.right, elem);

            // Found the node we want to remove
        } else {

            // This is the case with only  a right subtree or
            // not subtree at all. In this situation we just swap
            // the node we wish to remove with it's right child
            if (node.left == null) {

                Node rightChild = node.right;

                node.data = null;
                node = null;

                return rightChild;

                // This is the case with only a left subtree or
                // no subtree at all. In this situtation we just swap
                // the node we wish to remove with it's left child
            } else if (node.right == null) {

                Node leftChild = node.left;

                node.data = null;
                node = null;

                return leftChild;

                // When removing a node from a binary tree with two left and right children
                // the successor of the node can either be the greatest from the left subtree
                // or the smallest from the right subtree. This implementation will use smallest
                // node of the right subtree by traversing as far left as possibly in the right subtree
            } else {

                // Find the leftmost node in the right subtree
                Node tmp = digLeft(node.right);

                // Swap the data
                node.data = tmp.data;

                // Go into the right subtree and remove the leftmost node we found and swapped
                // data with. This prevents us from having two nodes in our tree with same value.
                node.right = remove(node.right, tmp.data);

                // If we wanted to find greatest node in the left subtree we would do the following:
                // Node tmp = digRight(node.left);
                // node.data = tmp.data;
                // node.left = remove(node.left, tmp.data);
            }
        }
        return node;
    }

    // Helper method to find leftmost node in right subtree
    private Node digLeft(Node node) {
        Node cur = node;
        while(cur.left != null)
            cur = cur.left;
        return cur;
    }

    // Helper method to find rightmost node in left subtree
    private Node digRight(Node node) {
        Node cur = node;
        while(cur.right != null)
            cur = cur.right;
        return cur;
    }

    // returns true if element exists in tree
    private boolean contains(T elem) {
        return contains(root, elem);
    }

    // Check if the element exists in the tree
    private boolean contains(Node node, T elem) {

        // Base case: reached bottom, element not found
        if (node == null) return false;

        int cmp = elem.compareTo(node.data);

        // Search left subtree because the element we seek is smaller
        // than the current node value
        if( cmp < 0) return contains(node.left, elem);

        // Search right subtree because the element we seek is greater
        // than the current node value
        else if( cmp > 0) return contains(node.right, elem);

        // We found the value we are looking for
        else return true;
    }

    // Computes the height of the tree
    public int height() {
        return height(root);
    }

    // Recursive helper method to compute the height of the tree
    private int height(Node node) {
        if (node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    // This method returns an iterator for a given TreeTraversalOrder.
    // The ways you can traverse the tree are these four:
    // preorder, inorder, postorder and levelorder.
    public java.util.Iterator <T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER: return preOrderTraversal();
            case IN_ORDER: return inOrderTraversal();
            case POST_ORDER: return postOrderTraversal();
            case LEVEL_ORDER: return levelOrderTraversal();
            default: return null;
        }
    }

    // Returns as iterator to traverse the tree in pre order
    private java.util.Iterator <T> preOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in pre order
    private java.util.Iterator <T> inOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                // Dig left
                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                // Try moving down right once
                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }

                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in pre order
    private java.util.Iterator <T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack1 = new java.util.Stack<>();
        final java.util.Stack<Node> stack2 = new java.util.Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.left != null) stack1.push(node.left);
                if (node.right != null) stack1.push(node.right);
            }
        }
        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return stack2.pop().data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns as iterator to traverse the tree in pre order
    private java.util.Iterator <T> levelOrderTraversal() {

        final int expectedNodeCount = nodeCount;
        final java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
