class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Display the string.3
        Niz<Integer> niz = new Niz<>(10);
        DoublyLinkedList<Integer> dvostrukoPovezanaLista = new DoublyLinkedList<Integer>();

        dvostrukoPovezanaLista.addFirst(1);
        dvostrukoPovezanaLista.addFirst(1);
        dvostrukoPovezanaLista.addFirst(1);
        dvostrukoPovezanaLista.addLast(3);


        System.out.println(dvostrukoPovezanaLista.toString());
    }
}