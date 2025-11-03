package master;
import java.io.*;

public class JobCompletionListenerSlaveToMasterThread extends Thread {

    private final BufferedReader requestReader;
    private final PrintWriter responseWriter;

    public JobCompletionListenerSlaveToMasterThread(BufferedReader requestReader, PrintWriter responseWriter) {
        this.requestReader = requestReader;
        this.responseWriter = responseWriter;
    }

    @Override
    public void run() {
        try {
            String completionMessage;
           // System.out.println("Got to the job completion thread.");
            while ((completionMessage = requestReader.readLine()) != null) {
                responseWriter.println(completionMessage);
                System.out.println("[Master] Job completion forwarded to client. Msg = " + completionMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}