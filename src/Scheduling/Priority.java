package Scheduling;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Priority {

    public static int[] waitingTime(ArrayList<Process> processes, ArrayList<Process> executionOrder) {
        executionOrder.clear();
        int complete = 0, curTime = 0, finishTime = 0, n = processes.size();
        boolean check = false;

        int[] waitingTime = new int[n];

        PriorityQueue<Process> currentProccesses = new PriorityQueue<>();

        while (complete != n) {
            currentProccesses.clear();
            for (Process process : processes) {
                if ((process.arrivalTime <= curTime) && process.remTime > 0) {
                    currentProccesses.add(process);
                    check = true;
                }
            }
            if (!check) {
                curTime++;
                continue;
            }
            Process current = currentProccesses.peek();
            current.remTime--;
            executionOrder.add(current);
            if (current.remTime == 0) {
                complete++;
                check = false;
                finishTime = curTime + 1;
                current.waitingTime = finishTime - current.burstTime - current.arrivalTime;
                if (current.waitingTime < 0)
                    current.waitingTime = 0;
            }
            curTime++;
        }
        for (int i = 0; i < n; i++) {
            waitingTime[i] = processes.get(i).waitingTime;
        }
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
