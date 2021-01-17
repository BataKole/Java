import java.io.IOException;
import java.util.concurrent.ExecutionException;

class Main {

    static int[] a= {1,2,3,4,5};


    public static void main(String[] args)  throws IOException{

        UnionFind uf = new UnionFind(5);

        broj();



    }

    public static void broj()throws IOException {
        System.out.println(a[5]);

    }

    public static void handle() throws IOException{

        try {
            broj();
        } catch ( IOException e){
            System.out.println("Exception");
        }
    }
}