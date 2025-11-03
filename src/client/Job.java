package client;

import java.util.Random;
public class Job {
	private static Random rand = new Random();
	//Global variable that starts at a randomly generated base number so there aren't shared IDs
	public static int jobID = rand.nextInt(100000);
	
	//private volatile static Job job = null;
	//synchronizes that only 1 id can be retrieved at one time so none are skipped
	public int getJobID() {
		jobID++;
		return jobID;
	}  
}


