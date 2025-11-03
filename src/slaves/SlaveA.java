package slaves;

import java.io.*;
import java.net.*;

public class SlaveA {

    public static void main(String[] args) throws IOException {
        args = new String[]{"127.0.0.1", "30122"};

        if (args.length != 2) {
            System.err.println("Usage: java SlaveA <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostName, portNumber);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Slave A connected.");

            String job;
            int sleepTime;
            while ((job = reader.readLine()) != null) {
                System.out.println("Slave A received job: " + job);
                if(job.charAt(0) == 'A')
                	sleepTime = 2000;
                else {
                	sleepTime = 10000;
                }
                Thread.sleep(sleepTime); // Simulate processing time for Slave A jobs
                writer.println("Slave A completed job " + job.substring(1) + ", job type " + job.charAt(0) 
                + ", slept for " + sleepTime + " ms");
                System.out.println("Slave A completed job: " + job.substring(1) + ", job type " + job.charAt(0));
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error in Slave A: " + e.getMessage());
        }
    }
}