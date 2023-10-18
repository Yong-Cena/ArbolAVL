public class main {

    public static void main(String[] args) {
        LinkedBinarySearchTree arbol= new LinkedBinarySearchTree();
        arbol.inserta(8);
        arbol.inserta(3);
        arbol.inserta(17);
        arbol.inserta(370);
        arbol.inserta(13);
        arbol.inserta(111);
        arbol.inserta(5);
        arbol.inserta(18);
        arbol.inserta(48);
        arbol.inserta(26);
        arbol.inserta(500);
        arbol.inserta(-1);
            
        
        arbol.borra(17);
        
        System.out.println("----------------");
        arbol.imprimirNivel();
//        NodoBin prueba= arbol.encuentra(370.0);
//        System.out.println(prueba.getIzq());
    }
    
}
