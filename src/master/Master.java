package master;

import java.io.*;
import java.net.*;
import java.util.*;

public class Master {
	public static int numJobs = 0;
	public static int getNextID() {
		return ++numJobs;
	}
	private static final ArrayList<Integer> jobIds = new ArrayList<>();
    private static final ArrayList<PrintWriter> clientWriters = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        args = new String[]{"30121", "30122"};

        if (args.length > 2) {
            System.err.println("Usage: java Master <port number>");
            System.exit(1);
        }

        int clientPort = Integer.parseInt(args[0]);
        int slaveAPort = Integer.parseInt(args[1]);
        int slaveBPort = slaveAPort + 1;

        ArrayList<String> jobs = new ArrayList<>();
        Queue<String> jobTypeA = new LinkedList<>();
        Queue<String> jobTypeB = new LinkedList<>();
        Object lock = new Object();

        try (ServerSocket clientServerSocket = new ServerSocket(clientPort);
             ServerSocket slaveAServerSocket = new ServerSocket(slaveAPort);
             ServerSocket slaveBServerSocket = new ServerSocket(slaveBPort)) {

            System.out.println("Master is running...");

            Socket slaveSocketA = slaveAServerSocket.accept();
            PrintWriter responseWriterA = new PrintWriter(slaveSocketA.getOutputStream(), true);
            BufferedReader requestReaderA = new BufferedReader(new InputStreamReader(slaveSocketA.getInputStream()));
            System.out.println("Slave A connected.");

            Socket slaveSocketB = slaveBServerSocket.accept();
            PrintWriter responseWriterB = new PrintWriter(slaveSocketB.getOutputStream(), true);
            BufferedReader requestReaderB = new BufferedReader(new InputStreamReader(slaveSocketB.getInputStream()));
            System.out.println("Slave B connected.");

            Socket clientSocket1 = clientServerSocket.accept();
            PrintWriter responseWriterClient1 = new PrintWriter(clientSocket1.getOutputStream(), true);
            BufferedReader requestReaderClient1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
            System.out.println("Client 1 connected.");

            Socket clientSocket2 = clientServerSocket.accept();
            PrintWriter responseWriterClient2 = new PrintWriter(clientSocket2.getOutputStream(), true);
            BufferedReader requestReaderClient2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            System.out.println("Client 2 connected.");

            ArrayList<Thread> threads = new ArrayList<>();
            threads.add(new SendJobClientToMasterThread(requestReaderClient1, jobs));
            threads.add(new SendJobClientToMasterThread(requestReaderClient2, jobs));
            threads.add(new AssignJobsToQueuesThread(lock, jobs, jobTypeA, jobTypeB));
            threads.add(new SendSlaveJobThread(responseWriterA,jobTypeA));
            threads.add(new SendSlaveJobThread(responseWriterB,jobTypeB));
            threads.add(new JobCompletionListenerSlaveToMasterThread(requestReaderA, responseWriterClient1));
            threads.add(new JobCompletionListenerSlaveToMasterThread(requestReaderB, responseWriterClient2));

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}