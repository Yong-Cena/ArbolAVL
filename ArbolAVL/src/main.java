public class main {

    public static void main(String[] args) {
        LinkedBinarySearchTree arbol= new LinkedBinarySearchTree();
        arbol.inserta(8);
        arbol.inserta(17);
        arbol.inserta(3);
        arbol.inserta(-4);
        arbol.inserta(5);
        arbol.inserta(370);
        arbol.inserta(13);
        arbol.inserta(111);
        //----------------------
/*        
        arbol.inserta(18.0);
        arbol.inserta(48.0);
        arbol.inserta(26.0);
        arbol.inserta(500.0);
        arbol.inserta(-1.0);
        arbol.inserta(-1.2);
        arbol.inserta(47.0);
        arbol.inserta(538.0);
*/        
//        arbol.borra(17.0);
        System.out.println("----------------");
        System.out.println(arbol.toStringNivel());
//        NodoBin prueba= arbol.encuentra(370.0);
//        System.out.println(prueba.getIzq());
    }
    
}
