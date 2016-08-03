package com.ovm.uy.crd_import_script.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private TextField idTextField;
	@FXML
	private Button folderButton;
	@FXML
	private Button importButton;
	@FXML
	private ProgressBar importProgressBar;
	@FXML
	private Label importLabel;
	@FXML
	private Button configButton;
	@FXML
	private Button exitButton;

	private Task task;
	private Thread thread;

	// Reference to the main application.
	private Main mainApp;

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {

		folderButton.setOnAction((event) -> {
			final DirectoryChooser fileChooser = new DirectoryChooser();
			Stage stage = (Stage) exitButton.getScene().getWindow();
			File file = fileChooser.showDialog(stage);

			if (file != null) {
				idTextField.setText(file.getAbsolutePath());
			}
		});

		importButton.setOnAction((event) -> {
			task = new InsertTask(idTextField.getText());

			importProgressBar.progressProperty().bind(task.progressProperty());
			importLabel.textProperty().bind(task.messageProperty());

			task.stateProperty().addListener(new ChangeListener<Worker.State>() {
				@Override
				public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
					if (newValue == Worker.State.SUCCEEDED) {
						// downloadButton.setDisable(false);
						// downloadButton.setText("Download");
						Dialog.showInformation("Exito", "Se importaron correctamente los archivos");
					} else if (newValue == State.FAILED) {
						try {
							throw task.getException();

						} catch (ClassNotFoundException | SQLException e) {
							Dialog.showError("Error", "Se produjo un error en la conexion con la base de datos");
							e.printStackTrace();
						} catch (FolderNotFoundException e) {
							Dialog.showError("Error", "No se selecciono nunguna carpeta");
						} catch (FileNotFoundException e) {
							Dialog.showError("Error", "No se encuentan archivos válidos");
						} catch (LineaInvalidException e) {
							Dialog.showError("Error", e.getMessage());
						} catch (Throwable e) {
							Dialog.showError("Error", "Se produjo un error inesperado");
							e.printStackTrace();
						}
						task.cancel();
						task.stateProperty().removeListener(this);

					}
				}
			});
			thread = new Thread(task);
			thread.start();

		});

		configButton.setOnAction((event) -> {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("config/Config.fxml"));
			BorderPane root1;
			try {
				root1 = (BorderPane) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);

				stage.setTitle("Configuración");
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		exitButton.setOnAction((event) -> {
			Stage stage = (Stage) exitButton.getScene().getWindow();
			// if (thread != null) {
			// task.cancel();
			// thread.stop();
			// }
			stage.close();
			Platform.exit();
			System.exit(0);
		});
	}

	private void importFiles() {
		// TODO Auto-generated method stub

	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;

	}
}
