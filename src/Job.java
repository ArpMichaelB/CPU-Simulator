/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class Job {
    private String name = "default";
    private int length = -1;//total time required
    private int startTime = -1;//when it was entered
    private int finishTime = -1;//when it was finished
    private int executionTime = 0;//time spent being executed
    private int waitTime = 0;//how long has it been waiting
    private int roundRobinCount = 0;
    public Job(int length, int startTime, String name)
    {
        this.length = length;
        this.startTime = startTime;
        this.name = name;
    }
    public int getLength()
    {
        return length;
    }
    public int getStartTime()
    {
        return startTime;
    }
    public int getFinishTime()
    {
        return finishTime;
    }
    public int getExecutionTime()
    {
        return executionTime;
    }
    public int getWaitTime()
    {
        return waitTime;
    }
    public int getRoundRobin()
    {
        return roundRobinCount;
    }
    public void setLength(int l)
    {
        length = l;
    }
    public void incrementWait()
    {
        waitTime++;
    }
    public void incrementExecution()
    {
        executionTime++;
    }
    public void setStart(int in)
    {
        startTime = in;
    }
    public void setFinish(int in)
    {
        finishTime = in;
    }
    public void setRoundRobin(int in)
    {
        roundRobinCount = in;
    }
    public void roundRobinDecrease()
    {
        roundRobinCount--;
    }

    /**
     * Spits out the information required of each job at an output
     * @return a string with all the output stuffs
     */
    @Override
    public String toString()
    {
        String ret;
        ret = "[Name: " + name + " Length: " + length + " Execution: " + executionTime + " Remaining: " + remainingTime() + " Wait: " + waitTime + "]";
        return ret;
    }
    public String toStringFin()
    {
        String ret;
        ret = "Job " + this.name + "\n\t Length: " + this.length + "\n\t Start Time: " + this.startTime + "\n\t Finish Time: " + this.getFinishTime() + "\n\t Execution Time: " + this.executionTime + "\n\t Wait Time: " + this.waitTime +"\n\t Response Ratio: " + responseRatString();
        return ret;
    }
    public int remainingTime()
    {
        return length-executionTime;
    }
    public double responseRatio()
    {
        return ((double)(waitTime+length)/length);
    }
    private String responseRatString()
    {
        String temp = Double.toString(this.responseRatio());
        if(temp.length() > 3)
        {
            temp = temp.substring(0, temp.indexOf(".")+3);
        }
        return temp;
    }
}
