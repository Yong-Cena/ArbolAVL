public class main {

    public static void main(String[] args) {
        LinkedBinarySearchTree arbol= new LinkedBinarySearchTree();
        arbol.inserta(8.0);
        arbol.inserta(17.0);
        arbol.inserta(3.14);
        arbol.inserta(-4.0);
        arbol.inserta(5.0);
        arbol.inserta(370.0);
        
        arbol.borra(17.0);
        System.out.println("----------------");
        System.out.println(arbol.toStringNivel());
        NodoBin prueba= arbol.encuentra(370.0);
        System.out.println(prueba.getIzq());
    }
    
}
