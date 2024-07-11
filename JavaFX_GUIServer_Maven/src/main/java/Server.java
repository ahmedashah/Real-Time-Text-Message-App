import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import org.w3c.dom.Text;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server extends GuiServer{

	int count = 1;
	TheServer server;
	private Consumer<Serializable> callback;

	ArrayList<ClientThread> clients = new ArrayList<Server.ClientThread>();
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;

			TextPass messageFromClient;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}

			public synchronized void updateClients(String message) {
				ArrayList<Integer> clientCountNum = new ArrayList<Integer>() ;

				for(int i = 0; i < clients.size(); i++) {
					clientCountNum.add(clients.get(i).count ) ;
				}
						for (int i = 0; i < clients.size(); i++) {
							ClientThread t = clients.get(i);

							//TextPass infoPass = new TextPass(message, true, clientCountNum);
							try {
								t.out.writeObject(message);
								t.out.writeObject(clientCountNum) ;
								//	t.out.writeObject(clientCountNum)
							} catch (Exception e) {
							}
						}
			}

			public synchronized void updateSomeClients(String msg, TextPass data, ArrayList<Integer> clientsToSend ) {
				ArrayList<Integer> clientCountNum = new ArrayList<Integer>() ;
				for(int i = 0; i < clients.size(); i++) {
					clientCountNum.add(clients.get(i).count ) ;
				}

				for (int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);


					for (int j = 0; j < clientsToSend.size(); j++) {
						if (clientsToSend.get(j) == t.count) {
							//TextPass infoPass  = new TextPass(msg, true, clientCountNum) ;
							try {
								t.out.writeObject(msg);
								t.out.writeObject(clientCountNum) ;
							} catch (Exception e) {

							}
						}
					}
				}
			}



			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
					
				 while(true) {
					    try {
					    	TextPass data = (TextPass)in.readObject();
					    	callback.accept("client: " + count + " sent: " + data.message);
					    	if (data.toAllClients){
								updateClients("client #"+count+" said: "+data.message);
							}else if(!data.toAllClients){
							updateSomeClients("client #"+count+" said: "+data.message, data, data.whichClientToSend);}
					    	}
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
							clients.remove(this);
							updateClients("Client #"+count+" has left the server!");
					    	//clients.remove(this);

					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
