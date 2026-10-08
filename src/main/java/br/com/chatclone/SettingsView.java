package br.com.chatclone;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SettingsView {
	
	private BorderPane layoutPrincipal; // Layout principal da aplicação
	private BorderPane chat; // Guarda o chat original
	
	public VBox criarView() {
		Button voltar = new Button("← Voltar"); // Retorna para o chat
		voltar.getStyleClass().add("botao-sidebar");
		
		voltar.setOnAction(event -> voltarParaChat());
		
	    Label titulo = new Label("Configurações");
	    titulo.getStyleClass().add("titulo-configuracoes");
	    
	    Label tituloTema = new Label("Aparência"); // Seção de configurações visuais
	    tituloTema.getStyleClass().add("subtitulo-configuracoes");

	    Label tema = new Label("Tema");
	    
	    Button temaClaro = new Button("Tema Claro");
	    Button temaEscuro = new Button("Tema Escuro");
	    temaClaro.getStyleClass().add("botao-configuracao");
	    temaEscuro.getStyleClass().add("botao-configuracao");
	    
	    ThemeController themeController = new ThemeController(); // Controla os temas

	    temaClaro.setOnAction(event -> {
	        themeController.aplicarTemaClaro(layoutPrincipal.getScene()); // Aplica tema claro
	    });
	    
	    temaEscuro.setOnAction(event -> {
	        themeController.aplicarTemaEscuro(layoutPrincipal.getScene()); // Aplica tema escuro
	    });
	    
	    Label informacao = new Label("As configurações da aplicação aparecerão aqui.");

	    VBox layout = new VBox(15, voltar, titulo, tituloTema, tema, temaClaro, temaEscuro, informacao);
	    layout.getStyleClass().add("configuracoes-layout");
	    layout.setPadding(new javafx.geometry.Insets(30));

	    return layout;
	}
	
	// Retorna a área central para o chat original
	private void voltarParaChat() {
	    layoutPrincipal.setCenter(chat);
	}
	 
	 public void setLayoutPrincipal(BorderPane layoutPrincipal) {
		    this.layoutPrincipal = layoutPrincipal;
		}
	 
	 public void setChat(BorderPane chat) {
		    this.chat = chat;
		}

}
