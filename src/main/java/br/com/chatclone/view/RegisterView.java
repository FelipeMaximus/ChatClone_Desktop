package br.com.chatclone.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import br.com.chatclone.service.UserService;
import javafx.stage.Stage;
import br.com.chatclone.controller.LoginController;
import javafx.scene.control.Alert;
import br.com.chatclone.util.AlertUtil;
import javafx.scene.layout.BorderPane;
import br.com.chatclone.view.WindowBar;

//TELA DE CADASTRO
public class RegisterView {
	
	private final UserService userService;
	private final LoginController loginController;

	public RegisterView(
	    UserService userService,
	    LoginController loginController
	) {
	    // Recebe os serviços necessários para a tela
	    this.userService = userService;
	    this.loginController = loginController;
	}
	
	 public VBox criarView(Stage stage) {

	        // Título da tela
	        Label titulo = new Label("Criar conta");
	        titulo.getStyleClass().add("titulo-cadastro"); // Estilo do título

	        // Campo do nome
	        TextField nome = new TextField();
	        nome.setPromptText("Nome");
	        nome.getStyleClass().add("campo-login"); // Reutiliza o estilo dos campos

	        // Campo do e-mail
	        TextField email = new TextField();
	        email.setPromptText("E-mail");
	        email.getStyleClass().add("campo-login");

	        // Campo da senha
	        PasswordField senha = new PasswordField();
	        senha.setPromptText("Senha");
	        senha.getStyleClass().add("campo-login");

	        // Confirmação da senha
	        PasswordField confirmarSenha = new PasswordField();
	        confirmarSenha.setPromptText("Confirmar senha");
	        confirmarSenha.getStyleClass().add("campo-login");

	        // Botão de cadastro
	        Button cadastrar = new Button("Cadastrar");
	        cadastrar.getStyleClass().add("botao-login"); // Usa o mesmo visual do login
	        
	        // Botão para retornar ao login
	        Button voltar = new Button("Voltar para Login");
	        voltar.getStyleClass().add("botao-voltar"); // Estilo específico para voltar

	        voltar.setOnAction(event -> {
	            // Recria o login com o serviço de chat
	            LoginView loginView =
	            	new LoginView(
	            	    loginController,
	            		new br.com.chatclone.config.AppConfig().getChatService(),
	            		new br.com.chatclone.config.AppConfig().getConversationService()
	            	);
	            
	            
	            // Cria o layout completo do login
	            BorderPane loginLayout = new BorderPane();

	            // Adiciona a barra personalizada
	            loginLayout.setTop(
	                WindowBar.criar(stage)
	            );

	            // Adiciona o conteúdo do login
	            loginLayout.setCenter(
	                loginView.criarView(stage)
	            );


	           // Cria a cena de login
	            javafx.scene.Scene loginScene =
	                new javafx.scene.Scene(
	                    loginLayout,
	                    400,
	                    500
	                );

	            // Carrega o CSS novamente
	            loginScene.getStylesheets().add(
	                getClass().getResource("/css/chat.css").toExternalForm()
	            );

	            // Exibe a tela de login estilizada
	            stage.setScene(loginScene);

	            // Carrega o CSS novamente
	            loginScene.getStylesheets().add(
	                getClass().getResource("/css/chat.css").toExternalForm()
	            );

	            stage.setScene(loginScene);
	        });
	        
	        // Executa o cadastro do usuário
	        cadastrar.setOnAction(event -> {
	        	 // Obtém os dados digitados
	            String nomeInformado = nome.getText().trim();
	            String emailInformado = email.getText().trim();
	            String senhaInformada = senha.getText();
	            String confirmacao = confirmarSenha.getText();

	            // Valida campos obrigatórios
	            if (nomeInformado.isBlank()
	                    || emailInformado.isBlank()
	                    || senhaInformada.isBlank()
	                    || confirmacao.isBlank()) {

	            	// Usa o tema atual no popup
	            	AlertUtil.mostrar(
	            	    Alert.AlertType.WARNING,
	            	    "Cadastro",
	            	    "Preencha todos os campos.",
	            	    stage.getScene()
	            	);

	                return;
	            }
	            
	            // Valida o formato básico do e-mail
	            if (!emailInformado.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

	                Alert alerta = new Alert(Alert.AlertType.WARNING);
	                alerta.setTitle("Cadastro");
	                alerta.setHeaderText("E-mail inválido");
	                alerta.setContentText("Informe um endereço de e-mail válido.");
	                alerta.showAndWait();

	                return;
	            }
	            
	            // Exige uma senha com pelo menos 8 caracteres
	            if (senhaInformada.length() < 8) {

	                Alert alerta = new Alert(Alert.AlertType.WARNING);
	                alerta.setTitle("Cadastro");
	                alerta.setHeaderText("Senha muito curta");
	                alerta.setContentText(
	                    "A senha deve possuir pelo menos 8 caracteres."
	                );
	                alerta.showAndWait();

	                return;
	            }

	            // Confirma se as senhas são iguais
	            if (!senhaInformada.equals(confirmacao)) {

	                Alert alerta = new Alert(Alert.AlertType.WARNING);
	                alerta.setTitle("Cadastro");
	                alerta.setHeaderText("Senhas diferentes");
	                alerta.setContentText("As senhas informadas não são iguais.");
	                alerta.showAndWait();

	                return;
	            }

	            // Cadastra o usuário com a senha protegida
	            try {
	                // Tenta cadastrar o usuário
	                userService.cadastrarUsuario(
	                    nomeInformado,
	                    emailInformado,
	                    senhaInformada
	                );

	                // Informa que o cadastro foi concluído
	                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
	                alerta.setTitle("Cadastro");
	                alerta.setHeaderText("Cadastro realizado");
	                alerta.setContentText("Usuário cadastrado com sucesso.");
	                alerta.showAndWait();

	            } catch (IllegalArgumentException e) {

	                // Mostra o motivo do cadastro ter sido recusado
	                Alert alerta = new Alert(Alert.AlertType.WARNING);
	                alerta.setTitle("Cadastro");
	                alerta.setHeaderText("Não foi possível cadastrar");
	                alerta.setContentText(e.getMessage());
	                alerta.showAndWait();
	            }
	        });

	        VBox layout = new VBox(
	            15,
	            titulo,
	            nome,
	            email,
	            senha,
	            confirmarSenha,
	            cadastrar,
	            voltar
	        );

	        layout.getStyleClass().add("login-layout"); // Aplica o fundo moderno
	        layout.setAlignment(Pos.CENTER);
	        layout.setPadding(new Insets(30));
	        layout.setPrefWidth(350);

	        return layout;
	    }

}
