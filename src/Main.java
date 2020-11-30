import java.util.Iterator;

class Main {


    public static void main(String[] args) {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        bst.add(3);
        bst.add(1);
        bst.add(2);
        bst.add(4);
        bst.add(6);
        bst.add(5);
        bst.add(7);



        bst.traverse(TreeTraversalOrder.PRE_ORDER);

        bst.traverse(TreeTraversalOrder.IN_ORDER);

        bst.traverse(TreeTraversalOrder.POST_ORDER);

        bst.traverse(TreeTraversalOrder.LEVEL_ORDER);
    }

}