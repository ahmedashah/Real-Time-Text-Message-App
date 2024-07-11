import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController2 extends GuiServer implements Initializable {
	
	@FXML
	private ListView listView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		serverConnection = new Server(data -> {
			Platform.runLater(()->{
				listView.getItems().add(data.toString());
			});
		});
	}
}
