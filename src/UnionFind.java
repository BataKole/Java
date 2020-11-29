public class UnionFind {

    // The number of elements of this union find
    private int size;

    // Track sizes of each of the components
    private int[] sz;

    // id[i] points to the parrent of i, if[i] = i then i is the root node
    private int[] id;

    // Tracks the number of components in the union find
    private int numComponents;

    public UnionFind(int size) {

        if(size <= 0)  throw new IllegalArgumentException("Size <= 0 is not allowed");

        this.size = numComponents = size;
        sz = new int[size];
        id = new int[size];

        for(int i=0; i<size; i++) {
            sz[i] = 1;  // Each Component is size 1 at the beginning
            id[i] = i;  // Each element is it's own root
        }
    }

    // Find which component/set p belongs to. Takes amortized constant time
    public int find(int p) {

        // Find the root of component
        int root = p;
        while(root != id[root]){
            root = id[root];
        }

        // Compress the path
        while(p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    // Are p and q in same component/set
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Size of component p belongs to
    public int componentSize(int p) {
        return sz[find(p)];
    }

    // Number of elements in this UnionFind/Disjoint Set
    public int size() {
        return size;
    }

    public int components() {
        return numComponents;
    }

    // Unify components/sets containing elements p and q
    public void unify(int p, int q){

        int root1 = find(p);
        int root2 = find(q);

        if(root1 == root2) return;

        if(sz[root1] < sz[root2]){
            sz[root2] += sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }

        // Since the roots were different, unification will decrease
        // component number by 1
        numComponents--;
    }


}
