package br.com.chatclone;

import javafx.application.Application;
import javafx.stage.Stage;
import br.com.chatclone.view.LoginView;
import br.com.chatclone.config.AppConfig;
import br.com.chatclone.controller.LoginController;
import br.com.chatclone.service.ChatService;
import br.com.chatclone.service.ConversationService;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	
    	// Define o ícone da janela e da barra de tarefas
    	Image icone = new Image(
    	    Objects.requireNonNull(
    	        getClass().getResourceAsStream("/chatclone.png")
    	    )
    	);

    	stage.getIcons().add(icone);

    	stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.setTitle("ChatClone");

        // Cria os serviços da aplicação
        AppConfig appConfig = new AppConfig();
        
      
        LoginController loginController =
            new LoginController(appConfig.getUserService());
        
        // Obtém o serviço de chat para a tela principal
        ChatService chatService = appConfig.getChatService();

        // Obtém o serviço responsável pelas conversas
        ConversationService conversationService =
            appConfig.getConversationService();

        LoginView loginView =
            new LoginView(
                loginController,
                chatService,
                conversationService
            );
        
         // Cria o layout da tela de login
        javafx.scene.layout.BorderPane loginLayout =
            new javafx.scene.layout.BorderPane();

        // Coloca a barra personalizada no topo
        loginLayout.setTop(
            br.com.chatclone.view.WindowBar.criar(stage)
        );

        // Coloca o login no centro
        loginLayout.setCenter(
            loginView.criarView(stage)
        );

        //carrega o CSS também na tela de login
        Scene scene =
            new Scene(
                loginLayout,
                400,
                500
            );

        scene.getStylesheets().add(
            getClass().getResource("/css/chat.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}