import Exception.ADTsException;
public class ArrayQueue<T>{
    private final static int DEFAULT_CAPACITY = 100;
    private int front, rear, count;
    private T[] queue; 
  
    /** Creates an empty queue using the specified capacity.
     * @param initialCapacity
     */
    public ArrayQueue(int initialCapacity) {
        front = rear = count = 0;
        queue = ( (T[])(new Object[initialCapacity]) );
    }
  
    /** Creates an empty queue using the default capacity.
     */
    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }
  
    /** Adds the specified element to the rear of this queue, expanding
     * the capacity of the queue array if necessary.
     * @param element
     */
    public void enqueue(T element) {
    if ( element == null )
            throw new ADTsException("(enqueue) element null.\n");
    
        if (size() == queue.length)
            expandCapacity();   //Cola llena.

        queue[rear]= element;
        rear= (rear+1)%queue.length;
        count++;
    }
  
    /** *  Removes the element at the front of this queue and returns a
     * reference to it.Throws an EmptyCollectionException if the
 queue is empty.
     * @return 
     */
    public T dequeue() throws ADTsException {
        T result=null;

        if ( isEmpty() )
            throw new ADTsException("Q-VACIA-dequeue.\n");

        result= queue[front];
        queue[front]= null;
        front= (front+1) % queue.length;
        count--;

        return result;
    }
  
    /** *  Returns a reference to the element at the front of this queue.The element is not removed from the queue.
     * Throws an
 EmptyCollectionException if the queue is empty.
     * @return 
     */
    public T first() throws ADTsException {
        if ( this.isEmpty() )
            throw new ADTsException("Q-VACIA-first.\n");

        return queue[front];
    }
  
    /** Returns true if this queue is empty and false otherwise.
     * @return 
     */
    public boolean isEmpty() {
        return (count == 0);
    }
   
    /**  Returns the number of elements in this queue.
     * @return 
     */
    public int size() {
        return count;
    }
  
    /**  Returns a string representation of this queue.
     *   ONLY TO DO PRINT's, NO OTHER OPERATIONS.
     */
    @Override
    public String toString() {
        String cade = "CArrayQue(toString) is EMPTY.";
        int f, s;
        
        if( !isEmpty() ) {
            s = size();
            f = front;
            cade = "QUEUE{#f=" + f + "/r=" + rear + "/s=" + s +
                    "/l=" + length() +"::" + "\n       ";
            while( s > 0 ) {
                cade = cade + queue[f] + ", ";
                f = (f+1) % queue.length;
                s--;
            }
            cade = cade + "} ";
        }
        
        return cade;
    }

    /** Creates a new array to store the contents of this queue with
     * twice the capacity of the old one.
     */
    private void expandCapacity() {
        T[] larger = (T[])(new Object[queue.length *2]);
    
        for(int scan=0; scan < count; scan++) {
            larger[scan] = queue[front];
            front = (front+1) % queue.length;
        }
    
        front = 0;
        rear = count;
        queue = larger;
    }
    
    /**  Returns the length of this array implementing the queue.
     * @return 
     */
    public int length() {
        return queue.length;
    }
    
    /**  Returns the last element added to the queue.
     * @return 
     */
    public T last() {
        if ( this.isEmpty() )
            throw new ADTsException("Q-VACIA-last().\n");
        
        // rear-1: indice del último elemento, conceptualmente
        int ultimo;    // el predecesor de "rear", indica el ultimo elemento
        ultimo= (rear-1 < 0) ? queue.length-1 : rear-1;
        //  ultimo = (rear - 1 + queue.length) % queue.length;
        if( rear==0 )
            ultimo= queue.length-1;
        else
            ultimo= rear-1;
        // ultimo= (rear==0) ? queue.length-1 : rear - 1;        
        return queue[ultimo];                
    }
    
}
