
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class CPUSimulator {
    private final int MAXIMUM_IDLE_TIME = 50;
    private final String DEFAULT_COMMAND = "no new job this time slice";
    private PrintWriter out;
    private ArrayList<String> commands = new ArrayList();
    private ArrayList<Job> finishedJobs = new ArrayList();
    ReadyQueue readyQueue;
    int currentTimeSlice;
    int idleTimeSlice;
    int idleTotal;
    int busyTotal;
    public CPUSimulator()
    {
        out = new PrintWriter(System.out);
        setupDefaultCommands();
        readyQueue = new ReadyQueue(new STRComparator());
        currentTimeSlice = 1;
    }
    public CPUSimulator(Comparator<Job> comparator)
    {
        out = new PrintWriter(System.out);
        setupDefaultCommands();
        readyQueue = new ReadyQueue(comparator);
        currentTimeSlice = 1;
    }
    public CPUSimulator(String inFileName, Comparator<Job> comparator) throws FileNotFoundException
    {
        out = new PrintWriter(System.out);
        setupCommandsFile(new File(inFileName));
        readyQueue = new ReadyQueue(comparator);
        currentTimeSlice = 1;
    }
    public CPUSimulator(String inFileName, String outFileName, Comparator<Job> comparator) throws FileNotFoundException
    {
        out = new PrintWriter(new File(outFileName));
        setupCommandsFile(new File(inFileName));
        readyQueue = new ReadyQueue(comparator);
        currentTimeSlice = 1;
    }
    public void start()
    {
        out.println("Starting CPU Simulation with " + readyQueue.priority.toString());//spit out what type of scheduling is being used
        out.flush();
        while(idleTimeSlice < MAXIMUM_IDLE_TIME)
        {
            out.println("Time Slice: " + currentTimeSlice);
            out.flush();
            String currentCommand;
            if(commands.isEmpty())
            {
                currentCommand = DEFAULT_COMMAND;
                out.println("command read: " + currentCommand);
                out.flush();
            }
            else
            {
                currentCommand = commands.remove(0);
                out.println("command read: " + currentCommand);
                out.flush();
            }
            if(parseCom(currentCommand))//if it's the default command (no new job)
            {
                if(!runCycle())
                {
                    idleTimeSlice++;//if we run cycle and we didn't do anything, increase idle counter then say so in the trace
                    idleTotal++;
                    out.println("Ready queue empty, nothing to execute, CPU idle for " + idleTimeSlice + " continuous time slices");
                    out.flush();
                }
                else
                {
                    idleTimeSlice = 0;//otherwise, we did something so idle counter must be 0
                    busyTotal++;
                }
            }
            else//if we're adding a job
            {
                Job temp = createJobFromCommand(currentCommand,currentTimeSlice);
                temp.setRoundRobin(readyQueue.size()+1);//if we're adding something, set it at the end of the round robin
                readyQueue.add(temp);//add a job to the queue then say so
                out.println("Adding Job to Ready Queue: " + temp);
                out.flush();
                runCycle();
                idleTimeSlice = 0;
                busyTotal++;
            }
            currentTimeSlice++;
        }
        out.println("CPU idle for 50 time slices. Shutting down.");
        out.flush();
        closingReport();//builds and outputs the closing report
        //note: Still have to set up round robin somehow, and test things out
    }
    private void setupDefaultCommands()
    {
        /*
        no new job this slice
        no new job this slice
        add job Job_A with length 2
        no new job this slice
        no new job this slice
        no new job this slice
        no new job this slice
        add job Job_B with length 7
        no new job this slice
        add job Job_C with length 4
        */
        commands.add("no new job this slice");
        commands.add("no new job this slice");
        commands.add("add job Job_A with length 2");
        commands.add("no new job this slice");
        commands.add("no new job this slice");
        commands.add("no new job this slice");
        commands.add("no new job this slice");
        commands.add("add job Job_B with length 7");
        commands.add("no new job this slice");
        commands.add("add job Job_C with length 4");
    }
    private void setupCommandsFile(File x) throws FileNotFoundException
    {
        Scanner fromFile = new Scanner(x);
        while(fromFile.hasNextLine())//while there are commands from the file, read them into the array list
        {
            commands.add(fromFile.nextLine());
        }
    }
    private boolean parseCom(String command)
    {
        return command.startsWith("n");
    }
    private Job createJobFromCommand(String command, int current)
    {
        String name = command.substring(8,command.indexOf(" ",8));//the name of the job
        String length  = command.substring(command.lastIndexOf(" ")+1); //the length as a string of numbers
        int jobLength = Integer.parseInt(length);//parse the length string as an integer
        Job ret = new Job(jobLength,current,name);
        return ret;
    }
    /**
     * does all the things we need to do regardless of command, then returns if it did anything
     * @return true if things took place, false if idle
     */
    private boolean runCycle()
    {
        boolean ret = false;
        if(!readyQueue.isEmpty())
        {
            Job running = readyQueue.remove();
            out.println("Retrieved Job from Ready Queue: " + running);
            out.flush();
            running.incrementExecution();
            out.println("Executing: job " + running);
            out.flush();
            running.setRoundRobin(readyQueue.size());//once it's run, put it at the end of the readyQueue
            if(running.remainingTime() != 0)//if it still has some remaining time, increase everything's wait time and put the run job back in the queue
            {
                readyQueue.increaseWaitTime();
                readyQueue.add(running);
            }
            else//if it doesn't have any remaining time, increase everything's wait time and put it the list of finished jobs
            {
                running.setFinish(currentTimeSlice);
                readyQueue.increaseWaitTime();
                finishedJobs.add(running);
                out.println("Completed: " + running);
                out.flush();
            }
            ret = true;//make sure it says we did something if we did something
        }
        return ret;
    }
    private void closingReport()
    {
        currentTimeSlice -= 51;//51 since it increments one last time after finishing the last time slice I think?
        idleTotal -= 50;
        out.println("----- Summary -----\n");
        out.flush();
        out.println("Simulation Run Time: " + currentTimeSlice);
        out.flush();
        out.println("CPU Idle Time: " + idleTotal);
        out.flush();
        out.println("CPU Busy Time: " + busyTotal);
        out.flush();
        out.println("CPU Utilization: " + getUtilization());//need to fix this and the percentage in the jobs
        out.flush();
        out.println("Average Wait Time: " + getAverageWait());
        out.flush();
        out.println("List of Jobs in Order of Completion");
        out.flush();
        while(!finishedJobs.isEmpty())
        {
            Job temp = finishedJobs.remove(0);
            out.println(temp.toStringFin());
            out.flush();
        }
    }
    private String getAverageWait()
    {
        double ret = 0;
        int count = 0;
        while(finishedJobs.size() > count)
        {
            Job temp = finishedJobs.get(count);
            ret += temp.getWaitTime();
            count++;
        }
        ret = ret/count;
        String temp = Double.toString(ret);
        if(temp.length() > temp.indexOf(".")+2)
        {
            temp = temp.substring(0, temp.indexOf(".")+3);//if it has greater than 2 decimal places, shorten it to 2 places
        }
        return temp;
    }
    private String getUtilization()
    {
        double util = (double) busyTotal/currentTimeSlice;
        util *= 100;
        String temp = Double.toString(util);
        if(temp.length() > temp.indexOf(".")+2)
        {
            temp = temp.substring(0, temp.indexOf(".")+3);//if it has greater than 2 decimal places, shorten it to 2 places
        }
        temp+="%";
        return temp;
    }
}
