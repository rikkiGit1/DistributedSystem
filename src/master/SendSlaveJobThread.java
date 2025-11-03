package master;

import java.io.PrintWriter;
import java.util.Queue;

public class SendSlaveJobThread extends Thread {

    private final PrintWriter responseWriter;
    private final Queue<String> jobQueue;

    public SendSlaveJobThread(PrintWriter responseWriter,Queue<String> jobQueue) {
        this.responseWriter = responseWriter;
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
    	String job;
    	while (true) {
    		//if queue is empty, thread will sleep and check again if there is a job to send to a slave when it wakes
    		while (jobQueue.isEmpty()) {
    			try {
    				Thread.sleep(3000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		//removing job and holding onto string (no need for for synchronization since only one slave talks to this thread
    		job = jobQueue.poll();
    		if (job != null) {
    			//sends job to the slave
    			responseWriter.println(job);
    			System.out.println("Job ID " + job.substring(1) + " sent to Slave.");
    		}
    	}
    }
}