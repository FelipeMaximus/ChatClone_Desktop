package br.com.chatclone;

//Gerencia a sessão do usuário
import br.com.chatclone.util.SessionManager;
import br.com.chatclone.view.LoginView;
//Controla autenticação
import br.com.chatclone.controller.LoginController;
import javafx.scene.control.Button; // Botão da interface
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.control.TextInputDialog; // Janela para editar o nome
import javafx.scene.layout.HBox; // Agrupa o nome e o botão
import javafx.scene.control.Alert; // Janela de confirmação
import javafx.scene.control.ScrollPane; // Permite rolagem das conversas
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import br.com.chatclone.model.Conversation;
import br.com.chatclone.service.ConversationService;
//Representa uma conversa do usuário
import br.com.chatclone.model.Conversation;

import java.util.List;


public class SidebarView {
	
	    private Label tituloChat;
	    private BorderPane layoutPrincipal; // Layout principal da aplicação
	    private BorderPane chat; // Guarda o conteúdo principal do chat
	    // Guarda as conversas carregadas do MongoDB
	    private List<Conversation> conversas;
	    // Guarda a janela principal da aplicação
	    private javafx.stage.Stage stage;
	    // Serviço responsável pelas conversas
	    private ConversationService conversationService;
	    // Permite informar ao MainView qual conversa foi selecionada
	    private java.util.function.Consumer<Conversation> aoSelecionarConversa;
	    // Recebe a conversa criada
	    private java.util.function.Consumer<Conversation> aoCriarNovaConversa;

	   // Recebe o título do chat central
	    public void setTituloChat(Label tituloChat) {
	    	this.tituloChat = tituloChat;
	    }

        // Cria o conteúdo visual da barra lateral
        public VBox criarSidebar() {
        Label titulo = new Label("Conversas");
        
        // Aplica estilo ao título
        titulo.getStyleClass().add("titulo-sidebar");
        
        // Botão para iniciar uma conversa
        Button novaConversa = new Button("+ Nova conversa");
        
        
        // Aplica estilo ao botão
        novaConversa.getStyleClass().add("nova-conversa");
        
        // Botão de configurações
        Button configuracoes = new Button("Configurações");
        configuracoes.getStyleClass().add("botao-sidebar");
        
        configuracoes.setOnAction(event -> {
        	SettingsView settingsView = new SettingsView();
        	settingsView.setChat(chat); // Entrega o chat original
        	settingsView.setLayoutPrincipal(layoutPrincipal); // Entrega o layout para a SettingsView
            layoutPrincipal.setCenter(settingsView.criarView()); // Abre configurações
        });
        
        // Botão para sair
        Button sair = new Button("Sair");
        sair.getStyleClass().add("botao-sidebar");
        
        //LOGICA DO BOTAO SAIR
        sair.setOnAction(event -> {

            Alert alerta = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Deseja realmente sair?",
                ButtonType.YES,
                ButtonType.NO
            );

            alerta.setTitle("Sair");
            alerta.setHeaderText("Encerrar sessão");

            alerta.showAndWait().ifPresent(resposta -> {

                if (resposta == ButtonType.YES) {
                	 // Encerra a sessão atual
                    SessionManager.logout();

                    // Volta para a tela de login
                    LoginController loginController =
                        new LoginController(
                            new br.com.chatclone.config.AppConfig().getUserService()
                        );

                    LoginView loginView =
                        new LoginView(
                            loginController,
                            new br.com.chatclone.config.AppConfig().getChatService(),
                            new br.com.chatclone.config.AppConfig().getConversationService()
                        );
                    
                    // Cria o layout do login
                    javafx.scene.layout.BorderPane loginLayout =
                        new javafx.scene.layout.BorderPane();

                    // Adiciona a barra personalizada
                    loginLayout.setTop(
                        br.com.chatclone.view.WindowBar.criar(stage)
                    );

                    // Adiciona a tela de login
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

                    // Carrega o CSS também após o logout
                    loginScene.getStylesheets().add(
                        getClass().getResource("/css/chat.css").toExternalForm()
                    );

                    // Volta para o login estilizado
                    stage.setScene(loginScene);
                    
                    // Centraliza a tela de login após o logout
                    stage.centerOnScreen();
                }
            });
        });
        
    
        // Espaço flexível entre as áreas
        Region espaco = new Region();
        VBox.setVgrow(espaco, javafx.scene.layout.Priority.ALWAYS);
        
        
        VBox listaConversas = new VBox(5); // Área exclusiva para as conversas
        // Guarda o botão da conversa atualmente selecionada
        Button[] conversaSelecionada = {null};
        ScrollPane scrollConversas = new ScrollPane(listaConversas); // Cria a rolagem
        scrollConversas.setFitToWidth(true); // Usa toda a largura disponível
        scrollConversas.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Remove rolagem horizontal
        VBox.setVgrow(scrollConversas, javafx.scene.layout.Priority.ALWAYS); // Ocupa o espaço vertical disponível
        
        // Adiciona as conversas carregadas do MongoDB
        if (conversas != null) {

            for (Conversation conversa : conversas) {

                // Botão com o nome da conversa
                Button botao = new Button(conversa.getTitulo());

                botao.getStyleClass().add("botao-conversa");

                // Botão de exclusão
                Button excluir = new Button("X");
                excluir.setPrefWidth(35);
                excluir.setFocusTraversable(false);
                excluir.getStyleClass().add("botao-excluir");

                // Agrupa nome + X
                HBox linha = new HBox(5, botao, excluir);
                linha.setMaxWidth(Double.MAX_VALUE);

                // Faz o nome ocupar o espaço disponível
                HBox.setHgrow(
                    botao,
                    javafx.scene.layout.Priority.ALWAYS
                );

                // Seleciona a conversa
                botao.setOnAction(event -> {

                    if (conversaSelecionada[0] != null) {
                        conversaSelecionada[0]
                            .getStyleClass()
                            .remove("conversa-selecionada");
                    }

                    botao.getStyleClass()
                        .add("conversa-selecionada");
                    conversaSelecionada[0] = botao;
                    tituloChat.setText(conversa.getTitulo());
                    
                    // Informa ao MainView qual conversa foi selecionada
                    if (aoSelecionarConversa != null) {
                        aoSelecionarConversa.accept(conversa);
                    }
                });

                // Renomeia com duplo clique
                botao.setOnMouseClicked(event -> {

                    if (event.getClickCount() == 2) {

                        TextInputDialog dialog =
                            new TextInputDialog(botao.getText());

                        dialog.setTitle("Renomear conversa");
                        dialog.setHeaderText(
                            "Digite o novo nome:"
                        );

                        dialog.showAndWait().ifPresent(novoNome -> {
                         if (!novoNome.isBlank()) {
                        	   // Salva o novo nome no MongoDB
                        	   conversationService.renomearConversa(
                        	       conversa.getId(),
                        	       br.com.chatclone.util.SessionManager.getUsuarioAtual(),
                        	       novoNome
                        	   );

                        	  // Atualiza o nome na tela
                        	  botao.setText(novoNome);
                        	  tituloChat.setText(novoNome);
                             }
                        });
                    }
                });

                // Exclui a conversa visualmente
                excluir.setOnAction(event -> {

                    Alert alerta = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Deseja excluir esta conversa?",
                        ButtonType.YES,
                        ButtonType.NO
                    );

                    alerta.setTitle("Excluir conversa");
                    alerta.setHeaderText("Confirmação");

                    alerta.showAndWait().ifPresent(resposta -> {

                        if (resposta == ButtonType.YES) {
                        	 // Remove a conversa do MongoDB
                            conversationService.excluirConversa(
                                conversa.getId(),
                                br.com.chatclone.util.SessionManager.getUsuarioAtual()
                            );
                            // Remove a linha da lista
                            listaConversas.getChildren().remove(linha);
                        }
                    });
                });

                // Adiciona nome + X na lista
                listaConversas.getChildren().add(linha);
            }
        }
        
     // Agrupa os botões de Configurações e Sair no rodapé
        VBox botoesInferiores = new VBox(
            10,
            configuracoes,
            sair
        );

        VBox sidebar = new VBox(
            10,
            titulo,
            novaConversa,
            scrollConversas,
            botoesInferiores
        );
        
        sidebar.getStyleClass().add("sidebar"); // Identifica a barra lateral no CSS
        
        // Cria uma nova conversa
        novaConversa.setOnAction(event -> {
        	 // Cria a conversa imediatamente no MongoDB
            Conversation novaConversaCriada =
                conversationService.criarConversa(
                    SessionManager.getUsuarioAtual(),
                    "Nova conversa"
                );

            // Informa ao MainView qual conversa foi criada
            if (aoCriarNovaConversa != null) {
                aoCriarNovaConversa.accept(novaConversaCriada);
            }
            // Usa o mesmo título salvo no MongoDB
            Button nova = new Button(novaConversaCriada.getTitulo());
            
            // Botão para excluir a conversa
            Button excluir = new Button("X");
            
            excluir.setPrefWidth(35); // Define largura do botão
            excluir.setFocusTraversable(false); // Evita foco visual
            excluir.getStyleClass().add("botao-excluir"); // Aplica estilo CSS
            
            // Agrupa a conversa e o botão excluir
            HBox linha = new HBox(5, nova, excluir);
            
            // Faz a linha ocupar a largura disponível
            linha.setMaxWidth(Double.MAX_VALUE);
            
            HBox.setHgrow(nova, javafx.scene.layout.Priority.ALWAYS); // Expande o nome
            
            // Ação ao selecionar a nova conversa
            nova.setOnAction(e -> {

                // Remove a seleção da conversa anterior
                if (conversaSelecionada[0] != null) {
                    conversaSelecionada[0]
                        .getStyleClass()
                        .remove("conversa-selecionada");
                }

                // Seleciona a nova conversa
                nova.getStyleClass().add("conversa-selecionada");
                conversaSelecionada[0] = nova;
                tituloChat.setText(nova.getText());
                
                // Informa ao MainView qual conversa foi selecionada
                if (aoSelecionarConversa != null) {
                    aoSelecionarConversa.accept(novaConversaCriada);
                }
            });
            
           // Duplo clique para renomear
            nova.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {

                    TextInputDialog dialog = new TextInputDialog(nova.getText());

                    dialog.setTitle("Renomear conversa");
                    dialog.setHeaderText("Digite o novo nome:");

                    dialog.showAndWait().ifPresent(novoNome -> {
                    	  if (!novoNome.isBlank()) {

                    	        // Salva o novo nome no MongoDB
                    	        conversationService.renomearConversa(
                    	            novaConversaCriada.getId(),
                    	            br.com.chatclone.util.SessionManager.getUsuarioAtual(),
                    	            novoNome
                    	        );

                    	        // Atualiza o nome na tela
                    	        nova.setText(novoNome);
                    	        tituloChat.setText(novoNome);
                    	        conversaSelecionada[0] = nova;
                    	    }
                    });
                }
            });
            
            // Remove a conversa da Sidebar
            excluir.setOnAction(e -> {
             Alert alerta = new Alert(
                     Alert.AlertType.CONFIRMATION,
                     "Deseja excluir esta conversa?",
                     ButtonType.YES,
                     ButtonType.NO
                 );

                 alerta.setTitle("Excluir conversa");
                 alerta.setHeaderText("Confirmação");

                 alerta.showAndWait().ifPresent(resposta -> {

                     if (resposta == ButtonType.YES) {
                    	  // Exclui a conversa do MongoDB
                    	    conversationService.excluirConversa(
                    	        novaConversaCriada.getId(),
                    	        SessionManager.getUsuarioAtual()
                    	    );
                    	    
                    	 // Remove a conversa da lista correta
                    	 listaConversas.getChildren().remove(linha);
                     }
                 });
            });
            nova.getStyleClass().add("botao-conversa");
            listaConversas.getChildren().add(linha); // Adiciona na lista de conversas
        });
        
        // Define a largura da barra
        sidebar.setPrefWidth(250);
        
        // Ocupa toda a altura disponível
        sidebar.setMaxHeight(Double.MAX_VALUE);
        
        // Espaçamento interno da Sidebar
        sidebar.setPadding(new javafx.geometry.Insets(15));
        return sidebar;
    }
        
    public void setConversas(List<Conversation> conversas) {
         // Recebe as conversas que serão exibidas
         this.conversas = conversas;
     }
        
      public void setLayoutPrincipal(BorderPane layoutPrincipal) {
          this.layoutPrincipal = layoutPrincipal;
      }
      
      public void setChat(BorderPane chat) {
    	    this.chat = chat;
    	}
      
      // Recebe a janela principal
      public void setStage(javafx.stage.Stage stage) {
          this.stage = stage;
      }
      
      // Recebe o serviço de conversas
      public void setConversationService(
          ConversationService conversationService
      ) {
          this.conversationService = conversationService;
      }
      
      // Define a ação executada ao selecionar uma conversa
      public void setAoSelecionarConversa(
          java.util.function.Consumer<Conversation> aoSelecionarConversa
      ) {
          this.aoSelecionarConversa = aoSelecionarConversa;
      }
      
      // Define a ação executada ao criar uma conversa
      public void setAoCriarNovaConversa(
          java.util.function.Consumer<Conversation> aoCriarNovaConversa
      ) {
          this.aoCriarNovaConversa = aoCriarNovaConversa;
      }
}