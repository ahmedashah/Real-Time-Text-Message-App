import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextPass implements Serializable {
    String message ;
    boolean toAllClients ;
    ArrayList<Integer> whichClientToSend;



    //ArrayList<Server.ClientThread> whichClientToSend ;
    // ArrayList<Server.ClientThread> allClients ;


    TextPass(String message, boolean toAllClients, ArrayList<Integer>  whichClientToSend ){
        this.message = message ;
        this.toAllClients = toAllClients ;
        if(toAllClients == false) {
            this.whichClientToSend = whichClientToSend;
        }
    }

}
