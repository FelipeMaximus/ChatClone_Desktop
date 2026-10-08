package br.com.chatclone.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class WindowBar {
	
	public static HBox criar(Stage stage) {

        // Nome da aplicação
        Label nomeAplicacao = new Label("ChatClone");
        nomeAplicacao.getStyleClass().add("nome-aplicacao");

        // Botões da janela
        Button minimizar = new Button("—");
        Button maximizar = new Button("□");
        Button fechar = new Button("✕");

        // Aplica os estilos
        minimizar.getStyleClass().add("botao-janela");
        maximizar.getStyleClass().add("botao-janela");
        fechar.getStyleClass().add("botao-fechar");

        // Espaço entre o nome e os botões
        Region espaco = new Region();
        HBox.setHgrow(espaco, javafx.scene.layout.Priority.ALWAYS);

        HBox barra = new HBox(
            nomeAplicacao,
            espaco,
            minimizar,
            maximizar,
            fechar
        );

        barra.getStyleClass().add("barra-titulo");

        // Minimiza a janela
        minimizar.setOnAction(e -> stage.setIconified(true));

        // Maximiza ou restaura
        maximizar.setOnAction(e ->
            stage.setMaximized(!stage.isMaximized())
        );

        // Fecha a aplicação
        fechar.setOnAction(e -> stage.close());

        // Permite arrastar a janela
        final double[] posicao = new double[2];

        barra.setOnMousePressed(e -> {
            posicao[0] = e.getSceneX();
            posicao[1] = e.getSceneY();
        });

        barra.setOnMouseDragged(e -> {
            if (!stage.isMaximized()) {
                stage.setX(e.getScreenX() - posicao[0]);
                stage.setY(e.getScreenY() - posicao[1]);
            }
        });

        return barra;
    }
}
