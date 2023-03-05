package cse_316;

import java.util.*;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int priority;
    int remainingTime;

    Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }
}

public class Round_robbin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // Create a priority queue to store processes with the highest priority at the front
        PriorityQueue<Process> pq = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.priority, p2.priority);
            }
        });

        // Input the process details from the user
        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for process " + (i + 1));
            System.out.print("Name: ");
            String name = sc.next();
            System.out.print("Arrival time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("Burst time: ");
            int burstTime = sc.nextInt();
            System.out.print("Priority: ");
            int priority = sc.nextInt();
            pq.add(new Process(name, arrivalTime, burstTime, priority));
        }

        System.out.print("Enter the time quantum: ");
        int timeQuantum = sc.nextInt();

        // Initialize variables to keep track of time, total waiting time, and total time spent
        int time = 0;
        int totalWaitingTime = 0;
        int totalTimeSpent = 0;

        // Process the processes in the priority queue using the round robin scheduling algorithm
        while (!pq.isEmpty()) {
            Process p = pq.poll();  // Get the process with the highest priority
            if (p.arrivalTime > time) {  // If the process has not arrived yet, skip to the next arrival time
                time = p.arrivalTime;
            }
            int remainingTime = p.remainingTime;
            int waitingTime = time - p.arrivalTime;
            if (remainingTime <= timeQuantum) {  // If the process can finish within the time quantum
                time += remainingTime;
                totalWaitingTime += waitingTime;
                totalTimeSpent += remainingTime;
                System.out.println(p.name + "\t" + time);
            } else {  // If the process cannot finish within the time quantum
                time += timeQuantum;
                p.remainingTime -= timeQuantum;
                pq.add(p);  // Add the process back to the priority queue with updated remaining time
            }
            // Add all the newly arrived processes to the priority queue
            while (!pq.isEmpty() && pq.peek().arrivalTime <= time) {
                pq.add(pq.poll());
            }
        }

        // Calculate the average waiting time and print the result
        double avgWaitingTime = (double) totalWaitingTime / n;
        System.out.println("Average waiting time: " + avgWaitingTime);

        // Print the total time spent on the queries
        System.out.println("Total time spent: " + totalTimeSpent);
    }
}
