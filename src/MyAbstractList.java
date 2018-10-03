/**
 * Implements those methods that have a common implementation across all 
 * lists (array, singly linked, and doubly linked).
 * @author Dr. Lillis
 * @param <E> type of elements stored in this list
 */
public abstract class MyAbstractList<E> implements MyList<E> {
    /**
     * Number of elements currently in this list.
     */
    protected int size = 0;
        
    @Override
    public int size(){
        return size;
    }
    
    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public E getFirst() throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot get first.");
        }
        return get(0);
    }

    @Override
    public E getLast() throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot get last.");
        }
        
        return get(size() - 1);
    }
    
    @Override
    public void add(E e){
        addLast(e);
    }
    
    @Override
    public void addFirst(E e){
        add(0, e);
    }

    @Override
    public void addLast(E e){
        add(size, e);
    }
    
    
    @Override
    public E removeFirst() throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot remove first.");
        }
        
        return remove(0);
    }

    @Override
    public E removeLast() throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot remove last.");
        }
        
        return remove(size() - 1);
    }
    
    @Override
    public E setFirst(E e) throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot set first.");
        }
        return set(0, e);
    }

    @Override
    public E setLast(E e) throws IllegalStateException {
        if(isEmpty()){
            throw new IllegalStateException("List is empty, cannot set last.");
        }
        return set(size() - 1, e);
    }
    
    @Override
    public int firstIndexOf(E e){
        if(isEmpty()){
            return -1;
        }
        
        for(int i = 0; i < size(); i++){
            if(get(i).equals(e)){
                return i;
            }
        }
        
        return -1;
    }
    
    @Override
    public int lastIndexOf(E e){
        if(isEmpty()){
            return -1;
        }
        
        for(int i = size() - 1; i >= 0; i--){
            if(get(i).equals(e)){
                return i;
            }
        }
        
        return -1;
    }
    
    @Override
    public boolean contains(E e){
        return firstIndexOf(e) != -1;
    }
    
    
    @Override
    public void clear(){
        while(!isEmpty()){
            removeFirst();
        }
    }
    
    @Override
    public String toString(){
        String s = "[";
        boolean first = true;
        for(int i = 0; i < this.size(); i++){
            if(first){
                s += this.get(i).toString();
                first = false;
            } else {
                s += ", " + this.get(i).toString();
            }
        }
        s += "]";
        return s;
    }
}
