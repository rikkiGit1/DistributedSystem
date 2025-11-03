package client;

import java.io.BufferedReader;
import java.io.IOException;

public class ListenForClientResponsesThread extends Thread {
	private BufferedReader responseReader;
	public ListenForClientResponsesThread(BufferedReader responseReader) {
		this.responseReader = responseReader;
	}
	
	@Override
	public void run() {
	    while (true) {
	        String serverResponse;
	        try {
	            // Read and process each line one at a time
	            if ((serverResponse = responseReader.readLine()) != null) {
	                System.out.println("[Client] Master responded: " + serverResponse);
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading response from server: " + e.getMessage());
	            break; // Exit the loop if an error occurs
	        }
	    }
	}

}