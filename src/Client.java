import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Client{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
	Client(){}
	void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("localhost", 2004);
			System.out.println("Connected to localhost in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			do{
				try{
					
					Calendar cal = Calendar.getInstance();
			    	cal.getTime();
			    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					
					message = (String)in.readObject();
					System.out.println("server>" + message);
					sendMessage("Tisch 2,"+ sdf.format(cal.getTime()) +",stuff");
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("bye"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Client client = new Client();
		client.run();
	}
}