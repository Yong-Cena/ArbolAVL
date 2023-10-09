
 public interface BinaryTreeADT <T extends Comparable <T>>{
    public boolean isEmpty();
    
    public int size();
    
    public NodoBin<T> encuentra(T elem);
    
    public void inserta(T elem);
    
    public T borra(T elem);
    
    public T findMin();
    
    public T findMax();
}
