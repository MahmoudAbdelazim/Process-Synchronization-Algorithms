package Scheduling;

import java.util.ArrayList;

public class SJF {

    public static int[] waitingTime(ArrayList<Process> processes, ArrayList<Process> executionOrder) {
        executionOrder.clear();
        int complete = 0, curTime = 0, minm = Integer.MAX_VALUE,
                shortest = 0, finishTime = 0, n = processes.size();
        boolean check = false;

        int[] waitingTime = new int[n];

        while (complete != n) {
            int i = 0;
            for (Process process : processes) {
                if ((process.arrivalTime <= curTime) && process.remTime < minm && process.remTime > 0) {
                    minm = process.remTime;
                    shortest = i;
                    check = true;
                }
                i++;
            }
            if (!check) {
                curTime++;
                continue;
            }
            processes.get(shortest).remTime--;
            executionOrder.add(processes.get(shortest));
            minm = processes.get(shortest).remTime;
            if (minm == 0)
                minm = Integer.MAX_VALUE;
            if (processes.get(shortest).remTime == 0) {
                complete++;
                check = false;
                finishTime = curTime + 1;
                processes.get(shortest).waitingTime = finishTime - processes.get(shortest).burstTime - processes.get(shortest).arrivalTime;
                if (processes.get(shortest).waitingTime < 0)
                    processes.get(shortest).waitingTime = 0;
            }
            curTime++;
        }
        for (int i = 0; i < n; i++)
            waitingTime[i] = processes.get(i).waitingTime;
        return waitingTime;
    }

    public static int[] turnAroundTime(ArrayList<Process> processes, int[] waitingTime) {
        int n = processes.size();
        int[] turnAroundTime = new int[n];
        for (int i = 0; i < n; i++) {
            turnAroundTime[i] = processes.get(i).burstTime + waitingTime[i];
        }
        return turnAroundTime;
    }

    double averageWaitingTime(int[] waitingTime) {
        int n = waitingTime.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += waitingTime[i];
        }
        return sum / n;
    }

    double averageTurnAroundTime(int[] turnAroundTime) {
        int n = turnAroundTime.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += turnAroundTime[i];
        }
        return sum / n;
    }
}
