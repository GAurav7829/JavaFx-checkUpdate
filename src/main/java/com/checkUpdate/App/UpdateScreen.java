package com.checkUpdate.App;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateScreen extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label label = new Label("Update Available");
		Button btn = new Button("Download");
		btn.setOnAction(e -> {
			try {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to download.");
				
				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK) {
					System.out.println("Downloading exe file.");
					JSONObject latestVersionData = UpdateChecker
							.getLatestVersionData(new FileReader(UpdateChecker.getVersionFile()));
					String path = (String) latestVersionData.get("path");
					File target = new File(System.getProperty("user.home")+File.separator+"ptViewer_update");
					if(!target.exists())
						target.mkdir();
					String targetFile = target.getPath()+File.separator+"ptViewer.exe";
					Files.copy(Paths.get(path), Paths.get(targetFile), StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Download finished.");
					
					//uninstaller code
					
					//install new exe
					ProcessBuilder p = new ProcessBuilder(targetFile);
					p.start();
					Platform.exit();
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button btn2 = new Button("Continue with current version");
		btn2.setOnAction(e->{
			System.out.println("Open Main Screen");
		});

		BorderPane pane = new BorderPane();
		pane.setTop(label);
		VBox box = new VBox(10);
		box.getChildren().addAll(btn, btn2);
		pane.setCenter(box);

		Scene scene = new Scene(pane, 300, 200);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void launchApp(String[] args) {
		launch(args);
	}

}
