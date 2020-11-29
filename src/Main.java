import java.util.Iterator;

class Main {


    public static void main(String[] args) {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(1);
        bst.add(2);
        bst.add(3);



        bst.traverse(TreeTraversalOrder.PRE_ORDER);
    }

}