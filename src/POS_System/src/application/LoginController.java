package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController implements Initializable{


	/////////////////////////START LOGIN WINDOW Code///////////////////////////////////////////////////////////

	String[] loginInfo= new String[2]; //value 0=username & 1=password

	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadData();
	}

	@FXML
	private void Login(ActionEvent event) throws IOException
	{

		if (txtUsername.getText().equals(loginInfo[0]) && txtPassword.getText().equals(loginInfo[1])) {
			lblStatus.setText("Login Success!!!");

			//closing stage
			Stage stage = (Stage)lblStatus.getScene().getWindow();
			stage.close();


			//open new stage after login
			Stage newStage = new Stage();
			newStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			newStage.setScene(scene);
			newStage.show();

		}
		else {
			lblStatus.setText("Login Failed! Try Again");
		}
	}

	private void CreateNewUserPass() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("First time user");
		alert.setHeaderText("Create Username & Password");

		VBox v = new VBox();

		Label userLabel = new Label("Enter user name:");
		TextField userField = new TextField();

		Label passLabel = new Label("Enter password:");
		TextField passField = new TextField();

		v.getChildren().addAll(userLabel,userField,passLabel,passField);

		alert.getDialogPane().setContent(v);

		alert.showAndWait();

		loginInfo[0] = userField.getText();
		loginInfo[1] = passField.getText();
	}

	public void loadData(){
        File f = new File("." + File.separator + "posstorage.dat");

        if (f.exists()) {
            try {

                FileInputStream fis = new FileInputStream("." + File.separator + "poslogin.dat");
                ObjectInputStream in = new ObjectInputStream(fis);
                loginInfo = (String[]) in.readObject();

                in.close();
            } catch (Exception e) {
				System.out.println(e);
			}
        } else {
        	CreateNewUserPass();
        	saveData();
        }
    }

	void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("." + File.separator + "poslogin.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(loginInfo);
            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

	/////////////////////////END LOGIN WINDOW CODE///////////////////////////////////////////////////////////
}
