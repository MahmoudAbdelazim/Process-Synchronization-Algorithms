package Scheduling;

import java.util.ArrayList;

public class MultiLevel {

    public static int[] waitingTime(ArrayList<Process> processes, int[] queue, int quantum, ArrayList<Process> executionOrder) {
        executionOrder.clear();
        int n = processes.size();
        int[] waitingTime = new int[n];
        ArrayList<Process> queue1 = new ArrayList<>();
        ArrayList<Process> queue2 = new ArrayList<>();
        int curTime = 0;
        for (int i = 0; i < n; i++) {
            if (queue[i] == 1)
                queue1.add(processes.get(i));
            else
                queue2.add(processes.get(i));
        }
        while (true) {
            int complete = 0;
            for (Process process : queue1) {
                if (process.remTime == 0) complete++;
            }
            for (Process process : queue2) {
                if (process.remTime == 0) complete++;
            }
            if (complete == n) break;
            while (true) {
                int temp = roundRobin(queue1, quantum, curTime, executionOrder);
                if (temp == -1) break;
                curTime = temp;
            }
            curTime = FCFS(queue2, curTime, executionOrder);
        }
        for (int i = 0; i < n; i++)
            waitingTime[i] = processes.get(i).waitingTime;
        return  waitingTime;
    }

    private static int roundRobin(ArrayList<Process> processes, int quantum, int curTime, ArrayList<Process> executionOrder) {
        boolean found = false;
        for (Process process : processes) {
            if (process.arrivalTime <= curTime && process.remTime > 0) {
                found = true;
                executionOrder.add(process);
                if (process.remTime > quantum) {
                    curTime += quantum;
                    process.remTime -= quantum;
                } else {
                    curTime += process.remTime;
                    process.remTime = 0;
                    int finishTime = curTime;
                    process.waitingTime = finishTime - process.burstTime - process.arrivalTime;
                }
            }
        }
        if (!found) return -1;
        return curTime;
    }

    private static int FCFS(ArrayList<Process> processes, int curTime, ArrayList<Process> executionOrder) {
        for (Process process : processes) {
            if (process.remTime > 0) {
                executionOrder.add(process);
                curTime++;
                process.remTime--;
                if (process.remTime == 0) {
                    int finishTime = curTime;
                    process.waitingTime = finishTime - process.burstTime - process.arrivalTime;
                }
                break;
            }
        }
        return curTime;
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
