
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
public class FCFSComparator implements Comparator<Job>{

    @Override
    public int compare(Job first, Job second) {
        if(first.getStartTime()<=second.getStartTime())//if the first one comes first, return negative (and ties are given to first Job
        {
            return -1;
        }
        else if(first.getStartTime()>second.getStartTime())//if the first one comes second, return positive
        {
            return 1;
        }
        else//this else should never get hit, it's just here to soothe the compiler
        {
            return 0;
        }
    }
    @Override
    public String toString()
    {
        return "First Come First Served Scheduling";
    }
    
}
