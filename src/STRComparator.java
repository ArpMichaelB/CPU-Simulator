
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
public class STRComparator implements Comparator<Job>{

    @Override
    public int compare(Job first, Job second) {
        if(first.remainingTime() <= second.remainingTime())//if the first one comes first or it's a tie, return negative
        {
            return -1;
        }
        else if(first.remainingTime() > second.remainingTime())//if the second one comes first, return positive
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
        return "Shortest Time Remaining Scheduling";
    }
}
