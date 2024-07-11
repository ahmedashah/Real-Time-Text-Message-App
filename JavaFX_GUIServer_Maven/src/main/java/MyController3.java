import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyController3 extends GuiServer implements Initializable {
	
	@FXML
	private Button sendButton;

	@FXML
	private TextField textField;

	@FXML
	private TextField textField2;

	@FXML
	private ListView listView;

	@FXML
	private ListView clientListView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		clientConnection = new Client(data->{
			Platform.runLater(()->{listView.getItems().add(data.toString());
			});
		},data->{
			Platform.runLater(()->{ clientListView.getItems().clear(); clientListView.getItems().add(data.toString());
			});
		});
		clientConnection.start();
	}

	public void sendButtonClicked(ActionEvent e) throws IOException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		boolean sendAll ;
		if(Objects.equals(textField2.getText(), "all")){
			System.out.println("Hello");
			sendAll = true ;
		}else {
			sendAll = false ;
			String sendToClients = textField2.getText() + ",";
			for (int i = 0, j, n = sendToClients.length(); i < n; i = j + 1) {
				j = sendToClients.indexOf(",", i);
				list.add(Integer.parseInt(sendToClients.substring(i, j).trim()));
			}
		}
		TextPass sendText = new TextPass(textField.getText(), sendAll, list) ;

		clientConnection.send(sendText);
		textField.clear(); textField2.clear();
	}

}
