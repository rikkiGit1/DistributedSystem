package master;

import java.util.ArrayList;
import java.util.Queue;

public class AssignJobsToQueuesThread extends Thread {

    private final Object lock;
    private final ArrayList<String> jobs;
    private final Queue<String> jobTypeA;
    private final Queue<String> jobTypeB;

    public AssignJobsToQueuesThread(Object lock, ArrayList<String> jobs2, Queue<String> jobTypeA, Queue<String> jobTypeB) {
        this.lock = lock;
        this.jobs = jobs2;
        this.jobTypeA = jobTypeA;
        this.jobTypeB = jobTypeB;
    }

    @Override
    public void run() {
        while (true) {
            	//if jobs arraylist is empty (no jobs to assign), thread will sleep and recheck once it wakes up.
            	//this is easier on the CPU than spinning.
                while (jobs.isEmpty()) {
                    try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
                
                //synchronizing so that the jobs aren't between time of removal and putting it on queue
                synchronized (lock) { 
                	//holding onto and removing next job that will be dealt with (top job in arrayList)
                	String job = jobs.remove(0); 
                	if (job != null) {
                		char jobType = job.charAt(0);                
                		//prioritizing which queue each job should go on based on sizes of each queue
                		if(jobTypeA.size() > 2 && jobTypeB.size() <= 2) {
                			jobTypeB.add(job);
                			System.out.println("Assigned to Queue B job: " + job.substring(1));
                		}
                		else if(jobTypeB.size() > 2 && jobTypeA.size() <= 2) {
                			jobTypeA.add(job);
                			System.out.println("Assigned to Queue A job: " + job.substring(1));
                		}
                		else if(jobType == 'A') {
                			jobTypeA.add(job);
                			System.out.println("Assigned to Queue A job: " + job.substring(1));
                		}
                		else if(jobType == 'B'){
                			jobTypeB.add(job);
                			System.out.println("Assigned to Queue B job: " + job.substring(1));
                		}                   
                	}
               }
        }
    }
}