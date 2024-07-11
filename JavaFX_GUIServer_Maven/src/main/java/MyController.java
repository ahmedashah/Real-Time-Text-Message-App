import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController extends GuiServer implements Initializable {
	
	@FXML
	private Button serverButton;
	
	@FXML
	private Button clientButton;
    
    //static so each instance of controller can access to update 
    private static String textEntered = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public void serverButtonClick(ActionEvent e) throws IOException   {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/MyFXML2.fxml"));
		Stage window = (Stage) serverButton.getScene().getWindow();
		root.getStylesheets().add("/styles/style1.css");
		window.setScene(new Scene(root, 500, 500));
		window.setTitle("This is the Server");
	}

    public void clientButtonClick(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML/MyFXML3.fxml"));
		Stage window = (Stage) serverButton.getScene().getWindow();
		root.getStylesheets().add("/styles/style1.css");
		window.setScene(new Scene(root, 500, 500));
		window.setTitle("This is the Client");
    }
}
