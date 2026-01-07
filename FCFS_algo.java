import javax.swing.*;
import java.awt.*;

public class FCFS_algo {

    // Process class
    static class Process {
        int pid;
        int arrivalTime;
        int burstTime;
        int waitingTime;
        int turnaroundTime;

        // Constructor
        public Process(int pid, int arrivalTime, int burstTime) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.waitingTime = 0;
            this.turnaroundTime = 0;
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create processes
        Process[] processes = new Process[3];
        processes[0] = new Process(1, 0, 5);
        processes[1] = new Process(2, 1, 3);
        processes[2] = new Process(3, 2, 8);

        // FCFS calculation
        int currentTime = 0;
        for (Process p : processes) {
            p.waitingTime = Math.max(0, currentTime - p.arrivalTime);
            currentTime = Math.max(currentTime, p.arrivalTime) + p.burstTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
        }

        // Print results in console
        System.out.println("PID\tArrival\tBurst\tWaiting\tTurnaround");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                               p.waitingTime + "\t" + p.turnaroundTime);
        }

        // Create and show GUI
        JFrame frame = new JFrame("FCFS Gantt Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GanttChart(processes));
        frame.pack();
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);
    }

    // Gantt chart panel
    static class GanttChart extends JPanel {
        Process[] processes;

        public GanttChart(Process[] processes) {
            this.processes = processes;
            setPreferredSize(new Dimension(600, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int x = 50;   // starting X
            int y = 50;   // starting Y
            int height = 30;
            int scale = 30; // pixels per unit of burst time

            for (Process p : processes) {
                // Draw filled rectangle
                g.setColor(new Color((p.pid * 50) % 255, (p.pid * 80) % 255, (p.pid * 120) % 255));
                g.fillRect(x + p.waitingTime * scale, y, p.burstTime * scale, height);

                // Draw border
                g.setColor(Color.BLACK);
                g.drawRect(x + p.waitingTime * scale, y, p.burstTime * scale, height);

                // Draw PID label
                g.drawString("P" + p.pid, x + p.waitingTime * scale + 5, y + 20);
            }
        }
    }
}
