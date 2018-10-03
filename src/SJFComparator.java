
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
public class SJFComparator implements Comparator<Job>{

    @Override
    public int compare(Job first, Job second) {
        if(first.getLength() <= second.getLength())//if the first one comes first or it's a tie, return negative
        {
            return -1;
        }
        else if(first.getLength() > second.getLength())//if the second one comes first, return positive
        {
            return 1;
        }
        else//this one should never get hit, only here to shoosh the compiler
        {
            return 0;
        }
    }
    @Override
    public String toString()
    {
        return "Shortest Job First Scheduling";
    }
    
}
