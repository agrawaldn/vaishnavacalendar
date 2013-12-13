package calgen;

/* 
####### PipeRedirection.java 
*/

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Need at least two arguments");
            System.exit(1);
        }

        try {
            String input = null;
            for (int i = 0; i < args.length; i++) {

                String[] commandList = args[i].split(" ");

                ProcessBuilder pb = new ProcessBuilder(commandList);
                //pb.redirectErrorStream(true);
                Process p = pb.start();

                if (input != null) {
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(p
                            .getOutputStream())), true);
                    writer.println(input);
                    writer.flush();
                    writer.close();
                }

                Gobbler outGobbler = new Gobbler(p.getInputStream());
                Gobbler errGobbler = new Gobbler(p.getErrorStream());
                Thread outThread = new Thread(outGobbler);
                Thread errThread = new Thread(errGobbler);
                outThread.start();
                errThread.start();

                outThread.join();
                errThread.join();

                int exitVal = p.waitFor();
                System.out.println("\n****************************");
                System.out.println("Command: " + args[i]);
                System.out.println("Exit Value = " + exitVal);
                List<String> output = outGobbler.getOuput();
                input = "";
                for (String o : output) {
                    input += o;
                }
            }
            System.out.println("Final Output:");
            System.out.println(input);

        } catch (IOException ioe) {
            // TODO Auto-generated catch block
            System.err.println(ioe.getLocalizedMessage());
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            System.err.println(ie.getLocalizedMessage());
            ie.printStackTrace();
        }

    }

    public static class Gobbler
            implements Runnable {
        private BufferedReader reader;
        private List<String> output;

        public Gobbler(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public void run() {
            String line;
            output = new ArrayList<String>();
            try {
                while ((line = reader.readLine()) != null) {
                    output.add(line + "\n");
                }
                reader.close();
            } catch (IOException e) {
                // TODO
                System.err.println("ERROR: " + e.getMessage());
            }
        }

        public List<String> getOuput() {
            return output;
        }
    }
}