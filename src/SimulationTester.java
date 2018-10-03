
import java.io.FileNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class SimulationTester {
    public static void main(String[] args) {
        CPUSimulator sim = new CPUSimulator();
        RatioComparator compe = new RatioComparator();
        STRComparator comparebois = new STRComparator();
        SJFComparator compareme = new SJFComparator();
        RoundRobinComparator pls = new RoundRobinComparator();
        RatioComparator rat = new RatioComparator();
        sim.start();
        try {
            CPUSimulator fileIn = new CPUSimulator("commands.txt","outputRatio.txt",compe);
            fileIn.start();
        } catch (FileNotFoundException ex) {
            System.out.println("where's commands.txt?");
        }
        try {
            CPUSimulator fileIn = new CPUSimulator("commandsrob.txt","outputRound.txt",pls);
            fileIn.start();
        } catch (FileNotFoundException ex) {
            System.out.println("where's commandsrob.txt?");
        }
        
        
    }
}
