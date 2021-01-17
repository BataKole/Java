public class UnionFind {

    // The number of elements in this union find
    private int size;

    // Track the size of each of the components
    private int[] sz;

    // id[i] points to the parent of i, if id[i]=i then i is the root node
    private int[] id;

    // Track the number of components in the union find
    private int numComponents;

    public UnionFind(int size) {

        if(size  <= 0)
            throw new IllegalArgumentException("Size <= 0 is not allowed");

        this.size = numComponents = size;
        sz = new int[size];
        id = new int[size];

        for(int i=0; i < size; i++) {
            id[i] = i; // Link to itself (self root)
            sz[i] = 1; // Each component is initialy of size 1
        }
    }

    // Find which component/set  'p' belongs to, takes amortized constant time
    public int find(int p) {

        // Find the root of component/set
        int root = p;
        while (root != id[root])
            root = id[root];

        // Compress the path leading back to the root
        // This operation is called "path compression"
        // and this gives us amortized constant time complexity
        while(p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    // Check if elements p and q are in same component/set
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Return the size of the component/set p belongs to
    public int componentSize(int p) {
        return sz[find(p)];
    }

    // Return the number of elements int this UnionFind/Disjoint set
    public int size() {
        return size;
    }

    // Return the number of remaining components/sets
    public int components() {
        return numComponents;
    }

    // Unify the components/sets containing elements p and q
    public void unify(int p, int q) {

        int root1 = find(p);
        int root2 = find(q);

        // Elements are already in same group
        if (root1 == root2) return;

        // Merge two components/sets
        // Merge smaller component/set into larger one
        if(sz[root1] <= sz[root2]) {
            sz[root2] +=sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] +=sz[root2];
            id[root2] = root1;
        }

        // Since the roots we found are different we know that the
        // number of components/sets has decreased by one
        numComponents--;
    }
}
