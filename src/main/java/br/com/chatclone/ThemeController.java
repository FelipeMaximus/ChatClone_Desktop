package br.com.chatclone;

import javafx.scene.Scene;

public class ThemeController {
	
	public void aplicarTemaClaro(Scene scene) {
        // Remove o tema escuro
        scene.getStylesheets().remove(
            getClass().getResource("/css/dark.css").toExternalForm()
        );

        // Adiciona o tema claro
        scene.getStylesheets().add(
            getClass().getResource("/css/light.css").toExternalForm()
        );
    }

    public void aplicarTemaEscuro(Scene scene) {
        // Remove o tema claro
        scene.getStylesheets().remove(
            getClass().getResource("/css/light.css").toExternalForm()
        );

        // Adiciona o tema escuro
        scene.getStylesheets().add(
            getClass().getResource("/css/dark.css").toExternalForm()
        );
    }

}
