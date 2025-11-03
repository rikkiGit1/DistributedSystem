package master;

import java.io.*;
import java.util.ArrayList;

public class SendJobClientToMasterThread extends Thread {

    private final BufferedReader requestReader;
    private final ArrayList<String> jobs;

    public SendJobClientToMasterThread(BufferedReader requestReader, ArrayList<String> jobs) {
        this.requestReader = requestReader;
        this.jobs = jobs;
    }

    @Override
    public void run() {
        try {
            String job;
            //if a job comes through (as a String) will add to arrayList which is shared memory location client-master
            while ((job = requestReader.readLine()) != null) {
                synchronized (jobs) {
                    jobs.add(job); 
                    System.out.println("[Master] received job #ID: " + job.substring(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}