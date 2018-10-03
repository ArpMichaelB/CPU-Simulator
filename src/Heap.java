
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class Heap<E> {
    Comparator<E> priority;
    protected E[] arr = (E[]) new Object[10];
    private int size = 1;//size is always 1 more than the index of where the last thing is because using the zero goofs up the nice neat numbering of the parents and children
    public Heap(Comparator<E> prior)
    {
        priority = prior;
    }
    /**
     * Adds element e to the heap, based on priority cf the provided comparator
     * @param e The element to add to the heap
     */
    public void add(E e)
    {
        int addplace = size;//we're adding it at the end of the filled spaces
        E holder = null;
        if(arr.length-1 < size)
        {
            //if there's not room in the array to add at the rightmost place, make it have it
            E[] temp = (E[]) new Object[size+1];
            System.arraycopy(arr,0,temp,0,arr.length);//copy arr into temp
            arr = temp;//make arr the new, larger array
        }
        arr[addplace] = e;//make the rightmost child the value being added
        if(addplace!=1)
        {
            //if we're not adding the first thing, we have to bubble up
            while(addplace != 1 && (priority.compare(arr[addplace], arr[getParent(addplace)])<0))
            {
                //while the thing we're inserting is greater than it's parent,
                //and we've not swapped it all the way to the root, swap it with it's parent
                holder = arr[getParent(addplace)];
                arr[getParent(addplace)] = arr[addplace];
                arr[addplace] = holder;
                addplace = getParent(addplace);
            }
        }
        //after we've added it to the list, increase size by 1
        size++;
    }
    /**
     * Returns and removes the element with the highest priority
     * @return the element with the highest priority
     */
    public E remove()
    {
        E ret = arr[1];//save the highest priority element to return
        /*int rempoint = 1;//the index of the parent we're swapping into
        //bubble things down
        while(rempoint*2 < size)
        {
            //System.out.println(rempoint + " is parent " + arr[rempoint]);
            //System.out.println(rempoint*2 + " is left child " + arr[rempoint*2]);
            //System.out.println(((rempoint*2)+1) + " is right child " + arr[(rempoint*2)+1]);
            //while there are still children to swap from, swap accordingly
            if((priority.compare(arr[rempoint*2],arr[(rempoint*2)+1]) > 0))//if the second child is greater
            {
                arr[rempoint] = arr[rempoint*2];//move the first child to it's parent's space
                rempoint = rempoint*2;//make rempoint the index of the child we swapped from
            }
            else if((priority.compare(arr[rempoint*2],arr[(rempoint*2)+1]) < 0))//if the first child is greater
            {
                arr[rempoint] = arr[(rempoint*2)+1];//move the second child to it's parent's space
                rempoint = (rempoint*2)+1;//make rempoint the index of the child we swapped from
            }
        }
        rempoint = rempoint/2;
        //once we're on the bottom layer, swap the last element with the last thing we swapped from
        //then remove the last item and decrease size
        arr[rempoint] = arr[size-1];
        arr[size-1] = null;
        size--;*/
        //all that above was my original code, but it only returned certain values, so i adapted the book's code a bit
        //helps that I learned how bubbling down works through the book since I didn't take good enough notes in class
        arr[1] = arr[size-1];
        size--;
        bubbleDown(1);
        return ret;//return the highest priority element since it's removed
        
    }
    /**
     * Returns the number of elements in the heap
     * @return the number of elements in the heap
     */
    public int size()
    {
        return size;
    }
    /**
     * Returns true if the heap is empty and returns false otherwise
     * @return true if it's empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (size == 1);
    }
    /**
     * returns the array index for the parent of the passed index
     * @param addIndex the index of where we're adding the element in the array 
     * @return 
     */
    private int getParent(int addIndex)
    {
        return addIndex/2;
    }
    /**
    * Internal method to percolate down in the heap.
    * @param hole the index at which the percolate begins.
    */
    private void bubbleDown( int hole )
    {
        int child;
        E tmp = arr[ hole ];
        while(hole * 2 <= size)
        {
            child = hole * 2;
            if( child != size && (priority.compare(arr[ child + 1 ],arr[ child ]) ) < 0 )
                 child++;
            if( (priority.compare(arr[ child ],tmp) ) < 0 )
                arr[ hole ] = arr[ child ];
            else
                break;
            hole = child;
        }
        arr[ hole ] = tmp;
    }
}
