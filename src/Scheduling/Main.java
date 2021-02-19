package Scheduling;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = in.nextInt();
        ArrayList<Process> processes = new ArrayList<>();
        int[] queue = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter process " + (i + 1) + " name: ");
            String name = in.next();
            System.out.print("Enter process " + (i + 1) + " burst time: ");
            int burstTime = in.nextInt();
            System.out.print("Enter process " + (i + 1) + " arrival time: ");
            int arrivalTime = in.nextInt();
            System.out.print("Enter process " + (i + 1) + " priority: ");
            int priority = in.nextInt();
            System.out.print("Enter process " + (i + 1) + " queue number (1 or 2): ");
            queue[i] = in.nextInt();
            processes.add(new Process(name, burstTime, arrivalTime, priority));
        }

        System.out.print("Enter Round-Robin quantum: ");
        int quantum = in.nextInt();
        int[] waitingTime, turnAroundTime;

        for (Process process: processes)
            process.remTime = process.burstTime;
        ArrayList<Process> executionOrder = new ArrayList<>();
        waitingTime = SJF.waitingTime(processes, executionOrder);
        turnAroundTime = SJF.turnAroundTime(processes, waitingTime);
        System.out.println("=====================================================");
        System.out.println("Shortest Job-First Scheduling: ");
        print(processes, executionOrder, waitingTime, turnAroundTime);

        for (Process process: processes)
            process.remTime = process.burstTime;
        waitingTime = Priority.waitingTime(processes, executionOrder);
        turnAroundTime = Priority.turnAroundTime(processes, waitingTime);
        System.out.println("=====================================================");
        System.out.println("Priority Scheduling: ");
        print(processes, executionOrder, waitingTime, turnAroundTime);

        for (Process process: processes)
            process.remTime = process.burstTime;
        waitingTime = RoundRobin.waitingTime(processes, quantum, executionOrder);
        turnAroundTime = RoundRobin.turnAroundTime(processes, waitingTime);
        System.out.println("=====================================================");
        System.out.println("Round-Robin Scheduling: ");
        print(processes, executionOrder, waitingTime, turnAroundTime);

        for (Process process: processes)
            process.remTime = process.burstTime;
        waitingTime = MultiLevel.waitingTime(processes, queue, quantum, executionOrder);
        turnAroundTime = MultiLevel.turnAroundTime(processes, waitingTime);
        System.out.println("=====================================================");
        System.out.println("Multi-Level Scheduling: ");
        print(processes, executionOrder, waitingTime, turnAroundTime);
    }

    private static void print(ArrayList<Process> processes, ArrayList<Process> executionOrder,
                              int[] waitingTime, int[] turnAroundTime) {
        int n = processes.size();
        System.out.println("Execution Order: ");
        for (Process process: executionOrder)
            System.out.print(process.name + " -> ");
        System.out.println();
        System.out.println("Process Name\tWaiting Time\tTurn Around Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes.get(i).name + "\t\t\t" + waitingTime[i] + "\t\t\t" + turnAroundTime[i]);
        }
        System.out.println("Average Waiting Time: " + getAvg(waitingTime));
        System.out.println("Average Turn Around Time: " + getAvg(turnAroundTime));
    }

    private static double getAvg(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return (double)sum / arr.length;
    }

}
