package br.com.chatclone.util;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class AlertUtil {
	
	public static void mostrar(
	        Alert.AlertType tipo,
	        String titulo,
	        String mensagem,
	        Scene scene
	    ) {
	        // Cria o alerta
	        Alert alerta = new Alert(tipo);

	        alerta.setTitle(titulo);
	        alerta.setHeaderText(null);
	        alerta.setContentText(mensagem);

	        // Copia o tema da aplicação para o popup
	        alerta.getDialogPane()
	              .getStylesheets()
	              .addAll(scene.getStylesheets());

	        // Exibe o popup
	        alerta.showAndWait();
	    }

}
