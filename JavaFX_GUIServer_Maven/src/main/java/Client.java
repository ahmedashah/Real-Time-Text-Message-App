import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private Consumer<Serializable> callback2;

	Client(Consumer<Serializable> call){
		callback = call ;
	}


	Client(Consumer<Serializable> call,Consumer<Serializable> call2 ){
	
		callback = call;
		callback2 = call2 ;
	}
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {

				String message = in.readObject().toString() ;
				callback.accept(message);

				ArrayList<Integer> clientCount = (ArrayList<Integer>) in.readObject();
						//infoPass.whichClientToSend;
			System.out.println("The number of clients on the server is " + clientCount.size());
			//MyController3.updateClientList(clientCount);
			//  ArrayList<Integer> clientCount = (ArrayList<Integer>) in.readObject() ;
				String allClientsonServer = "" ;

				for(int i = 0 ; i < clientCount.size(); i++){
					allClientsonServer   +=  "Client #" + clientCount.get(i) + " is on the server." + "\n" ;
				}
				callback2.accept(allClientsonServer);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(TextPass data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
