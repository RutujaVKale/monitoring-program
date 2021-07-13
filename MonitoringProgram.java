
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class MonitoringProgram {
	public static void main(String[] args) throws IOException {
		// server is listening on port 1352
		ServerSocket ss = new ServerSocket(1352);

		while (true) {
			Socket s = null;
			try {
				// socket object to receive incoming client requests
				System.out.println("Connection request from configuration server");
				s = ss.accept();

				System.out.println("Configuration program is connected on port : " + s.getPort());

				PrintStream printStream = new PrintStream(s.getOutputStream());

				// create a new thread object
				Thread t = new ConfigurationProgramHandler(s, printStream);

				// Invoking the start() method
				t.start();

			} catch (Exception e) {
				s.close();
				e.printStackTrace();
			}

		}

	}
}

// ClientHandler class
class ConfigurationProgramHandler extends Thread {
	final Socket s;
	PrintStream printStream;

	// Constructor
	public ConfigurationProgramHandler(Socket s, PrintStream printStream) {
		this.s = s;
		this.printStream = printStream;

	}

	@Override
	public void run() {
		BufferedWriter bw = null;
		while (true) {
			try {
				bw = new BufferedWriter(new FileWriter("C:\\Users\\RUTUJA\\Desktop\\files\\MonitoringFile.txt", true));

				SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				
				//sending message to server program
				printStream = new PrintStream(s.getOutputStream());
				printStream.println(" hi " + s.getPort());
				printStream.println(s.getPort());
				
				//listening to server programs response
				Scanner sc = new Scanner(s.getInputStream());
				String message = sc.nextLine();
				bw.write(dtf.format(new Date()) + " " + s.getPort() + " : " + message + "\n");
				System.out.println(dtf.format(new Date()) + " " + s.getPort() + " " + message + "\n");

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
