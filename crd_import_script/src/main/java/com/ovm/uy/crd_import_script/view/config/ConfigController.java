package com.ovm.uy.crd_import_script.view.config;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigController {
	@FXML
	private TextField hostTextField;
	@FXML
	private TextField userTextField;
	@FXML
	private TextField tableTextField;
	@FXML
	private PasswordField passwordPasswordField;
	@FXML
	private Button saveButton;

	@FXML
	private Button exitButton;

	@FXML
	private void initialize() {
		loadData();
		saveButton.setOnAction((event) -> {
			saveData();
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
		});
		exitButton.setOnAction((event) -> {
			Stage stage = (Stage) exitButton.getScene().getWindow();
			stage.close();
		});
	}

	private void loadData() {
		hostTextField.setText(Configuration.getInstance().getProperty(Configuration.CONFIGURATION_HOST));
		userTextField.setText(Configuration.getInstance().getProperty(Configuration.CONFIGURATION_USER));
		passwordPasswordField.setText(Configuration.getInstance().getProperty(Configuration.CONFIGURATION_PASSWORD));
		tableTextField.setText(Configuration.getInstance().getProperty(Configuration.CONFIGURATION_TABLE));

		
	}

	private void saveData() {
		Configuration.getInstance().putProperty(Configuration.CONFIGURATION_HOST, hostTextField.getText());
		Configuration.getInstance().putProperty(Configuration.CONFIGURATION_USER, userTextField.getText());
		Configuration.getInstance().putProperty(Configuration.CONFIGURATION_PASSWORD, passwordPasswordField.getText());
		Configuration.getInstance().putProperty(Configuration.CONFIGURATION_TABLE, tableTextField.getText());
	}
}
