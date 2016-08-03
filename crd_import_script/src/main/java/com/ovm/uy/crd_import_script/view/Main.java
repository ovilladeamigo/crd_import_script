package com.ovm.uy.crd_import_script.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage mainStage;
	private TilePane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.mainStage = primaryStage;
		this.mainStage.setTitle("Importar CDR");

		initRootLayout();

		// showPersonOverview();
	}

	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Main.fxml"));
			rootLayout = (TilePane) loader.load();

			MainController controller = loader.getController();

			controller.setMainApp(this);
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
