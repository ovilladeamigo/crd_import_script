package com.ovm.uy.crd_import_script.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Dialog {

	public static void showInformation(String sTitle, String sContentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(sTitle);
		alert.setHeaderText(null);
		alert.setContentText(sContentText);

		alert.showAndWait();
	}
	
	public static void showError(String sTitle, String sContentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(sTitle);
		alert.setHeaderText(null);
		alert.setContentText(sContentText);

		alert.showAndWait();
	}

	public static void showException(String sTitle, String sHeadderText,
			String sContentText, Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(sTitle);
		alert.setHeaderText(sHeadderText);
		alert.setContentText(sContentText);

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
