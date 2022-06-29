package com.checkUpdate.App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Launcher extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn = new Button("Click Me.");
		btn.setOnAction(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.show();
		});
		
		BorderPane pane = new BorderPane();
		pane.setCenter(btn);
		
		Scene scene = new Scene(pane, 300, 200);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void launchApp(String[] args) {
		launch(args);
	}

}
