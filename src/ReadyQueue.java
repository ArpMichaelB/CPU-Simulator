
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
public class ReadyQueue extends Heap<Job>{
    
    public ReadyQueue(Comparator<Job> prior) {
        super(prior);
    }
    /**
     * increase all the wait times of the un-activated jobs, remember to call before putting back in the job we took out
     * 
     */
    public void increaseWaitTime()
    {
        MyLinkedList<Job> jerbs = new MyLinkedList();
        while(!this.isEmpty())//run through the whole list, increasing the wait time on each job and moving it up in the round robin queue
        {
            Job temp = this.remove();
            temp.incrementWait();
            temp.roundRobinDecrease();
            jerbs.addFirst(temp); 
        }
        while(!jerbs.isEmpty())//then, put them all back in the list
        {
            Job temp = jerbs.removeFirst();
            this.add(temp);
        }
    }
    
}
