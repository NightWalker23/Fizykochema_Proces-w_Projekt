package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(this.getClass().getResource("/view/MainView.fxml"));
		AnchorPane pane = fxmlLoader.load();

		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Entalpia");
		primaryStage.show();
	}
}
