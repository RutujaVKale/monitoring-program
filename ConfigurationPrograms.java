import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ConfigurationPrograms {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("127.0.0.1", 1352);		

		int delay = 1000;// delay for 1 min
		int period = 60000;// repeat every second

		SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			PrintStream printStream;
			Scanner sc = null;
			BufferedWriter bw;
			
			@Override
			public void run() {						
				try {
					bw = new BufferedWriter(new FileWriter("C:\\Users\\RUTUJA\\Desktop\\files\\MonitoringFile.txt", true));					
					
					//read input monitoring program 
					sc = new Scanner(socket.getInputStream());
					String message = sc.nextLine();					
					bw.write(dtf.format(new Date())+" "+message+"\n");
					System.out.println(dtf.format(new Date())+" "+message+"\n");
					
					//send message to monitoring program
					printStream = new PrintStream(socket.getOutputStream());
					printStream.println("Hello");
				} 
				catch (IOException e) {							
					e.printStackTrace();	
				}
				try {
					bw.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
				
		}, delay, period);
			

	}

}
