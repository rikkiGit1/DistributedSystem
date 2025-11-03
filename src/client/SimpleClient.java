package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        // Hardcode in IP and Port
        args = new String[]{"127.0.0.1", "30121"};

        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket clientSocket = new Socket(hostName, portNumber);
             PrintWriter requestWriter = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader responseReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
        	System.out.println("Client connected");
        	//starting thread to listen for confirmation responses from master when job is done
        	Thread clientListener = new ListenForClientResponsesThread(responseReader);
        	clientListener.start();

        	while(true) {
        		//allows user to enter which job type 
                System.out.println("Enter job type (A or B): ");
                char jobType = scanner.nextLine().toUpperCase().charAt(0);
                while (jobType != 'A' && jobType != 'B') {
                    System.out.print("Invalid type. Enter job type (A or B): ");
                    jobType = scanner.nextLine().toUpperCase().charAt(0);
                }
                
                //creates job object to find next jobs jobID
                Job job = new Job();
                int jobID = job.getJobID();
                
                //concats the job into a string formatted: jobType - jobID
                String userInput = String.valueOf(jobType) + jobID;
   
                requestWriter.println(userInput); // Send job to master
                System.out.println("Sending job " + jobID + " to Master.");
        	}

            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}


