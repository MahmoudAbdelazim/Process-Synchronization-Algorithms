package Scheduling;

import java.util.ArrayList;

public class RoundRobin {

    public static int[] waitingTime(ArrayList<Process> processes, int quantum, ArrayList<Process> executionOrder) {
        executionOrder.clear();
        int complete = 0, curTime = 0, finishTime = 0, n = processes.size();

        int[] waitingTime = new int[n];

        while (complete != n) {
            for (Process process: processes) {
                if (process.arrivalTime <= curTime && process.remTime > 0) {
                    if (process.remTime > quantum) {
                        curTime += quantum;
                        process.remTime -= quantum;
                    } else {
                        curTime += process.remTime;
                        process.remTime = 0;
                        complete++;
                        finishTime = curTime;
                        process.waitingTime = finishTime - process.burstTime - process.arrivalTime;
                    }
                    executionOrder.add(process);
                } else {
                    curTime++;
                }
            }
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
}
