
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
public class RoundRobinComparator implements Comparator<Job>{

    @Override
    public int compare(Job first, Job second) {
        if(first.getRoundRobin()<=second.getRoundRobin())//if first comes first or it's a tie, return negative
        {
            return -1;
        }
        else if(second.getRoundRobin()<first.getRoundRobin())//if second comes first, return positive
        {
            return 1;
        }
        else//this is just here to shoosh the compiler
        {
            return 0;
        }
    }
    @Override
    public String toString()
    {
        return "Round Robin Scheduling";
    }
}
