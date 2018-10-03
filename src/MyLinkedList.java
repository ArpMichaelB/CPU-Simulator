/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 * @param <E> type of object in the linkedlist
 */
public class MyLinkedList<E> extends MyAbstractList<E>{
    
    SNode<E> Head;
    SNode<E> Tail;
    
    public MyLinkedList()
    {
        this((E[]) new Object[0]);
    }
    public MyLinkedList(E[] eArr)
    {
        for(E j : eArr)
        {
            addLast(j);
        }
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        SNode<E> current = Head;
        if(i < 0 || i >= this.size())
        {
            throw new IndexOutOfBoundsException();
        }
        /*if(size == 0)//if the list is empty, you can't get anything, so that's out of bounds
        {
            throw new IndexOutOfBoundsException();
        }*///redundant, so I commented it out
        if(i == 0)
        {
            return current.e;
        }
        else if(i == this.size()-1)
        {
            return Tail.e;
        }
        else
        {
            current = move(i+1);
            return current.e;
        }
    }
    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException {
        SNode temp = new SNode<>(e);
        SNode<E> current = Head;
        if(i<0 || i>this.size())
        {
            throw new IndexOutOfBoundsException();
        }
        if(i == 0)//if addfirst
        {
            temp.next = Head;//link the new node to the head
            Head = temp;//head points to temp, since it's now the beginning 
            if(Tail == null)//if temp is the only thing in the list, tail and head are the same
            {
                Tail = Head;
            }
        }
        else if(i == this.size())//if addlast
        {
            if(Tail==null)//if temp is the only thing in the list, tail and head are the same
            {
                Head = Tail = temp;
            }
            else
            {
                Tail.next = temp;//whatever's at the end, link it to temp
                Tail = Tail.next;//move the tail
            }
        }
        else//otherwise, add at the index i
        {
            for(int j = 1; j<i; j++)
            {
                current = current.next;
            }//move current to the space it needs to be in
            temp = current.next;
            current.next = new SNode<E>(e);
            current.next.next = temp;
        }
        size++;
    }

    @Override
    public E set(int i, E e) throws IndexOutOfBoundsException {
        SNode<E> current = Head;
        E ret = null;
        if(i < 0 || i >= this.size())
        {
            throw new IndexOutOfBoundsException();
        }
        if(isEmpty())//if the list is empty, just say that's out of bounds, because it is
        {
            throw new IndexOutOfBoundsException();
        }
        else if(i==0)//if it's at zero, just save what's in the head for return, then change what's in the head (which is current, since it starts there)
        {
            ret = current.e;
            current.e = e;
        }
        else if(i == this.size())//if we're setting the end of the list, set ret value to Tail.e, then set Tail.e to e
        {
            ret = Tail.e;
            Tail.e = e;
        }
        else//otherwise, move current to the space, then return what's in the space
        {
            current = move(i+1);
            ret = current.e;
            current.e = e;
        }
        return ret;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        if(i<0 || i >= this.size())
        {
            throw new IndexOutOfBoundsException();
        }
        SNode<E> temp = Head;
        SNode<E> current = Head;
        if(isEmpty())
        {
            return null;
        }
        else if(i==0)
        {
           temp = Head;//save head in temp to return the value
           Head = Head.next;//move the head forward
           if(Head == null)
           {
               Tail = null;
           }
           size--;
           return temp.e;
        }
        else if(i==this.size()-1)
        {
            if(size == 1)//if there's only one element left
            {
                Head = Tail = null;
                size = 0;
                return temp.e;
            }
            else{
                current = move(i);
                temp = Tail;
                Tail = current;
                Tail.next = null;
                size--;
                return temp.e;
            }
        }
        else
        {
            temp = move(i);
            current = temp.next;
            temp.next = current.next;
            size--;
            return current.e;
        }
    }
    
    @Override
    public int firstIndexOf(E e)
    {
        if(isEmpty())
        {
            return -1;
        }
        SNode<E> current = Head;
        int count = 0;
	while(current != null)
	{
            if (current.e.equals(e))
            {
		return count;
            }
            else
            {
                current = current.next;
                count++;
            }
        }
        return -1;//if the list is empty, then the while loop doesn’t happen, so it returns -1
        //if it doesn’t find it, then it never returns count, so it returns -1
    }
    
    @Override
    public int lastIndexOf(E e)
    {
        if(isEmpty())
        {
            return -1;
        }
        int lastSeenAt = -1;
	int count = 0;
        SNode<E> current = Head;
	while(current != null)
	{
            if (current.e.equals(e))
            {
		lastSeenAt = count;
            }
            count++;
            current = current.next;
        }
        return lastSeenAt;//if the list is empty, then the while loop doesn’t happen, so it returns -1
        //if it doesn’t see it, lastSeen at doesn’t change so it returns -1
    }
    
    public SNode<E> move(int i)
    {
        SNode<E> current = Head;
        for (int j = 1; j<i; j++)
	{
            if(current.next != null)
                current = current.next;//current is now the one right after itself now, unless that makes it a null
        }//do that until it's right on top where it needs to be
        return current;
    }

    protected static class SNode<E> {

        E e = null;
        SNode<E> next = null;
        public SNode(E e)
        {
            this.e = e;
        }
    }
}
