package br.com.chatclone.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import br.com.chatclone.controller.LoginController;
import javafx.stage.Stage;
import br.com.chatclone.MainView;
import br.com.chatclone.service.ChatService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import br.com.chatclone.service.ConversationService;
import br.com.chatclone.util.AlertUtil;
import javafx.scene.layout.BorderPane;
import br.com.chatclone.view.WindowBar;

//TELA DE LOGIN
public class LoginView {
	
	private final LoginController controller;
	private final ChatService chatService;
	private ConversationService conversationService;

	public LoginView(
	    LoginController controller,
	    ChatService chatService,
	    ConversationService conversationService
	) {
	    // Recebe os componentes necessários para o login
	    this.controller = controller;
	    this.chatService = chatService;
	    this.conversationService = conversationService;
	}
	
	public VBox criarView(Stage stage) {

        // Título da tela
        Label titulo = new Label("ChatClone");

        // Campo de e-mail
        TextField email = new TextField();
        email.setPromptText("E-mail");
        email.getStyleClass().add("campo-login");

        // Campo de senha
        PasswordField senha = new PasswordField();
        senha.setPromptText("Senha");
        senha.getStyleClass().add("campo-login");

        // Botão de login
        Button entrar = new Button("Entrar");
        entrar.getStyleClass().add("botao-login");
        
        // Permite usar Enter para executar o login
        entrar.setDefaultButton(true);
        
        // Executa a autenticação ao clicar em "Entrar"
        entrar.setOnAction(event -> {
        	
            // Obtém os dados digitados em branco e nao consulta o banco para nao pesalo
            String emailInformado = email.getText().trim();
            String senhaInformada = senha.getText();

            // Impede tentativa com campos vazios
            if (emailInformado.isBlank() || senhaInformada.isBlank()) {

            	// Exibe alerta usando o tema atual do ChatClone
                AlertUtil.mostrar(
                    Alert.AlertType.WARNING,
                    "Login",
                    "Informe o e-mail e a senha.",
                    stage.getScene()
                );

                return;
            }

            boolean autenticado =
                controller.autenticar(email.getText(), senha.getText());

            if (autenticado) {

                // Abre a tela principal após o login
                MainView mainView = new MainView();
                
                // Passa a janela principal para a tela de chat
                mainView.setStage(stage);

                // Injeta o serviço de chat
                mainView.setChatService(chatService);

                // Injeta o serviço de conversas
                mainView.setConversationService(conversationService);
                stage.setScene(mainView.criarScene());
                
                // Centraliza a janela na tela
                stage.centerOnScreen();

            } else {

            	// Exibe o erro usando o tema atual da aplicação
            	AlertUtil.mostrar(
            	    Alert.AlertType.ERROR,
            	    "Login",
            	    "Email o senha inválidos", // mantenha aqui a mesma mensagem atual
            	    stage.getScene()
            	);
            }
        });

        // Link para cadastro
        Button cadastrar = new Button("Criar conta");
        cadastrar.getStyleClass().add("botao-voltar");
        
        // Abre a tela de cadastro
        cadastrar.setOnAction(event -> {
        	// Cria o serviço de usuários através da configuração da aplicação
        	br.com.chatclone.config.AppConfig appConfig =
        	    new br.com.chatclone.config.AppConfig();
        	
            RegisterView registerView =
            		new RegisterView(
            				appConfig.getUserService(),
            				controller);
            
            // Cria o layout completo do cadastro
            BorderPane cadastroLayout = new BorderPane();

            // Adiciona a barra personalizada da janela
            cadastroLayout.setTop(
                WindowBar.criar(stage)
            );

            // Adiciona o conteúdo do cadastro
            cadastroLayout.setCenter(
                registerView.criarView(stage)
            );
            
            // Cria a tela de cadastro
            Scene cadastroScene =
                new Scene(
                    cadastroLayout,
                    400,
                    500
                );

            // Carrega o mesmo CSS da aplicação
            cadastroScene.getStylesheets().add(
                getClass().getResource("/css/chat.css").toExternalForm()
            );

            stage.setScene(cadastroScene);
        });

        VBox layout = new VBox(
            15,
            titulo,
            email,
            senha,
            entrar,
            cadastrar
        );
        
        // aplica identidade visual à tela de login
        layout.getStyleClass().add("login-layout");

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setPrefWidth(350);

        return layout;
    }

}
