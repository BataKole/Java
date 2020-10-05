class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Display the string.3
        Niz<Integer> niz = new Niz<>(10);

        niz.add(5);
        niz.add(5);
        niz.add(5);
        niz.add(5);

        System.out.println(niz.toString());
    }
}