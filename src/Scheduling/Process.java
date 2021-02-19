package Scheduling;

public class Process implements Comparable<Process>{
    public String name;
    public int burstTime;
    public int arrivalTime;
    public int priority;
    public int remTime;
    public int waitingTime = 0;

    public Process(String name, int burstTime, int arrivalTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.remTime = burstTime;
    }

    @Override
    public int compareTo(Process o) {
        if (this.priority < o.priority)
            return -1;
        return 1;
    }
}
