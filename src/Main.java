class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        PQueue<Integer> kju = new PQueue<>();

        kju.add(1);
        kju.add(1);
        kju.add(2);
        kju.add(2);
        kju.add(3);
        kju.add(0);
        kju.add(-1);

        while(!kju.isEmpty())
            System.out.println(kju.poll());

    }
}