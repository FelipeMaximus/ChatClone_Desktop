package br.com.chatclone;

import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea; // Campo de mensagem multilinha
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane; // Permite rolar o histórico
import br.com.chatclone.service.ChatService;
import br.com.chatclone.model.Message;
import br.com.chatclone.util.SessionManager;
import br.com.chatclone.model.Conversation;
import br.com.chatclone.service.ConversationService;
//permite executar a chamada da IA fora da interface
import javafx.concurrent.Task;
// elementos da animação de entrada
import javafx.scene.shape.Circle;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;

public class MainView {
	
	private ConversationService conversationService;
	// Guarda a conversa atualmente selecionada
	private String conversaAtualId;
	private ChatService chatService;
	// Guarda a janela principal da aplicação
	private javafx.stage.Stage stage;
	// Recebe a conversa criada
	private java.util.function.Consumer<Conversation> aoCriarNovaConversa;
	// controla se a IA está processando uma pergunta
	private boolean iaProcessando = false;
	// Guarda o ScrollPane para controlar a rolagem do chat
	private ScrollPane scrollMensagens;

    public Scene criarScene() {
    	
    	BorderPane layout = new BorderPane();
    	
    	// Estado inicial da animação: invisível
        layout.setOpacity(0);
        layout.setMaxHeight(Double.MAX_VALUE);
    	
    	// Layout principal do chat
    	BorderPane chat = new BorderPane();
    	
    	
    	//PERSONALIZAANDO BARRA DO SISTEMA
    	HBox barraTitulo = new HBox();

    	Label nomeAplicacao = new Label("ChatClone");
    	nomeAplicacao.getStyleClass().add("nome-aplicacao");

    	// Botões da janela
    	Button minimizar = new Button("—");
    	Button maximizar = new Button("□");
    	Button fechar = new Button("✕");

    	// Aplica o estilo dos botões
    	minimizar.getStyleClass().add("botao-janela");
    	maximizar.getStyleClass().add("botao-janela");
    	fechar.getStyleClass().add("botao-fechar");

    	// Espaço flexível entre o nome e os botões
    	javafx.scene.layout.Region espaco = new javafx.scene.layout.Region();
    	HBox.setHgrow(
    	    espaco,
    	    javafx.scene.layout.Priority.ALWAYS
    	);

    	barraTitulo.getChildren().addAll(
    	    nomeAplicacao,
    	    espaco,
    	    minimizar,
    	    maximizar,
    	    fechar
    	);

    	barraTitulo.getStyleClass().add("barra-titulo");
    	
    	// Minimiza a janela
    	minimizar.setOnAction(e -> stage.setIconified(true));

    	// Maximiza ou restaura a janela
    	maximizar.setOnAction(e -> {
    	    stage.setMaximized(!stage.isMaximized());
    	});

    	// Fecha a aplicação
    	fechar.setOnAction(e -> stage.close());

    	// Permite arrastar a janela pela barra
    	final double[] posicao = new double[2];

    	barraTitulo.setOnMousePressed(e -> {
    	    posicao[0] = e.getSceneX();
    	    posicao[1] = e.getSceneY();
    	});

    	barraTitulo.setOnMouseDragged(e -> {
    	    if (!stage.isMaximized()) {
    	        stage.setX(e.getScreenX() - posicao[0]);
    	        stage.setY(e.getScreenY() - posicao[1]);
    	    }
    	});
    	
    	
    	
    	
    	
    	
    	//sidebarView.setChat(chat); // Permite a Sidebar voltar ao chat
    	
    	// Cabeçalho do chat
    	Label tituloChat = new Label("Nova conversa");
    	
    	// Aplica o estilo do cabeçalho
    	tituloChat.getStyleClass().add("titulo-chat");

    	// Define o cabeçalho
    	chat.setTop(tituloChat);
        
         // Área onde as mensagens serão exibidas
        VBox mensagens = new VBox(10);
        mensagens.setPadding(new javafx.geometry.Insets(15)); // Espaçamento interno
        
        // Permite ocupar o espaço disponível
        mensagens.setMaxWidth(Double.MAX_VALUE);
        mensagens.setMaxHeight(Double.MAX_VALUE);

        // Cria área com rolagem para o histórico
        // Guarda o ScrollPane para poder rolar automaticamente
        scrollMensagens = new ScrollPane(mensagens);
        scrollMensagens.setFitToWidth(true);
        scrollMensagens.setFitToHeight(false);
        scrollMensagens.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Mensagens ficam no centro
        chat.setCenter(scrollMensagens);
        
        TextArea campoMensagem = new TextArea();
        campoMensagem.getStyleClass().add("text-area-mensagem"); // Aplica o estilo

        // Permite várias linhas
        campoMensagem.setWrapText(true);
        campoMensagem.setPrefRowCount(3);
        
        HBox.setHgrow(campoMensagem, javafx.scene.layout.Priority.ALWAYS);
        campoMensagem.setPromptText("Digite sua mensagem aqui...");
        
        // Botão para enviar a mensagem
        Button enviar = new Button("Enviar");
        enviar.getStyleClass().add("botao-enviar"); // Aplica o estilo
        
        
        campoMensagem.addEventFilter(
        	    javafx.scene.input.KeyEvent.KEY_PRESSED,
        	    event -> {

        	        if (event.getCode() == javafx.scene.input.KeyCode.ENTER
        	                && !event.isShiftDown()) {

        	            // Impede o TextArea de inserir uma nova linha
        	            event.consume();

        	            // Aciona o mesmo envio do botão
        	            enviar.fire();
        	        }
        	    }
        	);
        
        // Define a altura do botão
        enviar.setPrefHeight(45);
        enviar.setPrefWidth(100); // Define uma largura fixa para o botão
        
        // Organiza campo e botão na mesma linha
        HBox entrada = new HBox(10, campoMensagem, enviar);
        

        // ALTERAÇÃO AQUI: identifica a área inferior para o CSS
        entrada.getStyleClass().add("area-entrada");
        
        // Espaçamento interno da área de entrada
        entrada.setPadding(new javafx.geometry.Insets(10));
        
        entrada.setAlignment(Pos.CENTER); // Centraliza campo e botão verticalmente
        
        // Entrada fica na parte inferior
        chat.setBottom(entrada);
        

        
        //PARTE DA MENSAGEM
        // Testa o envio da mensagem
        enviar.setOnAction(event -> {
        	
        	// ALTERAÇÃO AQUI: evita várias chamadas simultâneas ao Groq
        	if (iaProcessando) {
        	    return;
        	}

        	iaProcessando = true;
        	
        	// Ignora mensagens vazias
        	if (campoMensagem.getText().isBlank()) {
        		return;
        	}

            String texto = campoMensagem.getText();
            String usuario = SessionManager.getUsuarioAtual();
            
            // Verifica se existe uma conversa selecionada
            if (conversaAtualId == null) {
                return;
            }

            // Verifica se a conversa pertence ao usuário
            boolean pertence =
                conversationService.pertenceAoUsuario(
                    conversaAtualId,
                    usuario
                );

            if (!pertence) {
                return;
            }

            // Cria a mensagem vinculada à conversa
            Message message = new Message(
                conversaAtualId,
                usuario,
                texto
            );
            
            // Salva através da camada de serviço
            chatService.enviarMensagem(message);

            // Exibe a mensagem na interface
            adicionarMensagem(
                mensagens,
                "Você: " + texto,
                true
            );
            
            // Limpa o campo imediatamente após capturar a mensagem
            campoMensagem.clear();
            
            // cria o indicador de carregamento
            Label loading = new Label("IA está digitando...");
            loading.getStyleClass().add("mensagem-ia");

            HBox linhaLoading = new HBox(loading);
            linhaLoading.setAlignment(Pos.CENTER_LEFT);

            mensagens.getChildren().add(linhaLoading);
            
         // Executa o Groq em uma thread separada
            Task<String> tarefaIA = new Task<>() {
                @Override
                protected String call() {
                    // A chamada HTTP não bloqueia a interface
                    return chatService.perguntarIA(texto);
                }
            };

            // Quando o Groq responder, volta para a interface JavaFX
            tarefaIA.setOnSucceeded(e -> {

                String respostaIA = tarefaIA.getValue();

                // Salva a resposta da IA no MongoDB
                Message mensagemIA = new Message(
                    conversaAtualId,
                    "IA",
                    respostaIA
                );

                chatService.enviarMensagem(mensagemIA);

                // ALTERAÇÃO AQUI: remove somente o loading
                mensagens.getChildren().remove(linhaLoading);

                // Exibe a resposta real
                adicionarMensagem(
                    mensagens,
                    "IA: " + respostaIA,
                    false
                );
                
                // ALTERAÇÃO AQUI: libera uma nova pergunta
                iaProcessando = false;
                
            });
            
            
            // ALTERAÇÃO AQUI: captura erros da chamada ao Groq
            tarefaIA.setOnFailed(e -> {

                Throwable erro = tarefaIA.getException();

                System.out.println(
                    "Erro ao consultar o Groq: " + erro.getMessage()
                );
                
                erro.printStackTrace();

               // ALTERAÇÃO AQUI: remove somente o loading
                mensagens.getChildren().remove(linhaLoading);
                
                // Informa o usuário
                adicionarMensagem(
                    mensagens,
                    "IA: Não foi possível obter uma resposta.",
                    false
                );
                
                // ALTERAÇÃO AQUI: libera uma nova tentativa após erro
                iaProcessando = false;
            });

            // Inicia a tarefa em segundo plano
            Thread thread = new Thread(tarefaIA);
            thread.setDaemon(true);
            thread.start();

            campoMensagem.clear();
        });
        
        layout.setTop(barraTitulo);
        layout.setCenter(chat);
        
        // Cria a barra lateral
        SidebarView sidebarView = new SidebarView();
        // Reseta a conversa atual para iniciar uma nova
        sidebarView.setAoCriarNovaConversa(conversa -> {
            // Guarda o ID da nova conversa
            conversaAtualId = conversa.getId();
        });
        // Atualiza a conversa ativa quando o usuário seleciona uma conversa
        sidebarView.setAoSelecionarConversa(conversa -> {
            setConversaAtualId(conversa.getId());
            // Recupera as mensagens salvas
            carregarMensagens(conversa, mensagens);
        });
        // Passa o serviço para a Sidebar
        sidebarView.setConversationService(conversationService);
        // Passa a janela principal para a barra lateral
        sidebarView.setStage(stage);
        sidebarView.setTituloChat(tituloChat);
        sidebarView.setLayoutPrincipal(layout); // Permite trocar o conteúdo central
        sidebarView.setChat(chat); // Entrega o chat atual para a Sidebar
        // Carrega as conversas do usuário logado
        carregarConversas(sidebarView);
        // Coloca a barra lateral à esquerda
        layout.setLeft(sidebarView.criarSidebar());
        
        // cria a esfera usada na transição
        Circle esferaEntrada = new Circle(60);

        // A cor da esfera será definida pelo CSS
        esferaEntrada.getStyleClass().add("esfera-entrada");

        // Centraliza a esfera sobre a tela
        StackPane camadaAnimacao = new StackPane(
            layout,
            esferaEntrada
        );
        
        Scene scene = new Scene(camadaAnimacao, 1000, 700);
        scene.setFill(javafx.scene.paint.Color.web("#0f1117"));
        
        // começa pequena
        esferaEntrada.setScaleX(0.01);
        esferaEntrada.setScaleY(0.01);

        // Expande a esfera rapidamente
        javafx.animation.ScaleTransition esfera =
            new javafx.animation.ScaleTransition(
                javafx.util.Duration.millis(700),
                esferaEntrada
            );

        esfera.setFromX(0.01);
        esfera.setFromY(0.01);
        esfera.setToX(15);
        esfera.setToY(15);
        
        // Quando a esfera terminar de crescer...
        esfera.setOnFinished(evento -> {

            // Faz a esfera desaparecer suavemente
            javafx.animation.FadeTransition saidaEsfera =
                new javafx.animation.FadeTransition(
                    javafx.util.Duration.millis(350),
                    esferaEntrada
                );

            saidaEsfera.setFromValue(1);
            saidaEsfera.setToValue(0);
            
            // Quando terminar, remove a esfera da interface
            saidaEsfera.setOnFinished(e -> {
                camadaAnimacao.getChildren().remove(esferaEntrada);
            });

            // Executa o desaparecimento
            saidaEsfera.play();
        });

        // Executa a expansão
        esfera.play();
        
        // Carrega o CSS do chat
        scene.getStylesheets().add(
            getClass().getResource("/css/chat.css").toExternalForm()
        );
        
        // Animação de entrada da tela principal
        javafx.animation.FadeTransition animacaoEntrada =
            new javafx.animation.FadeTransition(
                javafx.util.Duration.millis(600),
                layout
            );

        animacaoEntrada.setFromValue(0);
        animacaoEntrada.setToValue(1);
        animacaoEntrada.play();
        
        // Anima a tela subindo para a posição normal
        javafx.animation.TranslateTransition movimentoEntrada =
            new javafx.animation.TranslateTransition(
                javafx.util.Duration.millis(600),
                layout
            );

        movimentoEntrada.setFromY(20);
        movimentoEntrada.setToY(0);
        movimentoEntrada.play();
        
         // Remove o deslocamento após a animação
         movimentoEntrada.setOnFinished(e -> layout.setTranslateY(0));
        
        return scene;
    }
    
    //METODO NOVO
    private void adicionarMensagem(VBox mensagens, String texto, boolean usuario) {

    	Label mensagem = new Label(texto);
    	mensagem.setWrapText(true);
    	
    	// Permite que o balão ocupe até 600px e aumente verticalmente
    	mensagem.setMaxWidth(600);
    	mensagem.setPrefHeight(javafx.scene.layout.Region.USE_COMPUTED_SIZE);
    	
        // Define o estilo conforme o remetente
        mensagem.getStyleClass().add(
            usuario ? "mensagem-usuario" : "mensagem-ia"
        );
        
        // =====================================================
        // ALTERAÇÃO AQUI: permite copiar a mensagem
        // =====================================================
        MenuItem copiar = new MenuItem("Copiar mensagem");

        copiar.setOnAction(event -> {
            // Coloca o texto da mensagem no clipboard
            javafx.scene.input.Clipboard clipboard =
                    javafx.scene.input.Clipboard.getSystemClipboard();

            javafx.scene.input.ClipboardContent conteudo =
                    new javafx.scene.input.ClipboardContent();

            conteudo.putString(mensagem.getText());

            clipboard.setContent(conteudo);
        });

        ContextMenu menu = new ContextMenu(copiar);

        // Mostra o menu ao clicar com o botão direito
        mensagem.setContextMenu(menu);

        // Define o alinhamento
        HBox linha = new HBox(mensagem);
        linha.setAlignment(
            usuario ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT
        );

        // Adiciona ao histórico visual
        mensagens.getChildren().add(linha);
        VBox.setVgrow(linha, Priority.NEVER);
        
        // Aguarda o JavaFX atualizar o tamanho do conteúdo
        javafx.application.Platform.runLater(() -> {

            // Move a rolagem para o final do chat
            scrollMensagens.setVvalue(1.0);
        });
    }
    
    public void setChatService(ChatService chatService) {
        // Recebe o serviço configurado pela aplicação
        this.chatService = chatService;
    }
    
    public void setConversationService(
    	    ConversationService conversationService
    	) {
    	    // Recebe o serviço de conversas
    	    this.conversationService = conversationService;
    	}
    
    public void atualizarConversasSidebar(
    	    java.util.List<Conversation> conversas
    	) {
    	    // Envia as conversas para o componente visual
    	    // O SidebarView será responsável pela exibição
    	}
    
    private void carregarConversas(SidebarView sidebarView) {
        // Obtém o usuário da sessão atual
        String usuario = SessionManager.getUsuarioAtual();

        // Busca somente as conversas desse usuário
        var conversas =
            conversationService.buscarPorUsuario(usuario);
        
        // Envia as conversas para o SidebarView
        sidebarView.setConversas(conversas);

    }
    
    // Recebe a janela principal
    public void setStage(javafx.stage.Stage stage) {
        this.stage = stage;
    }
    
    // Define qual conversa está selecionada
    public void setConversaAtualId(String conversaAtualId) {
        this.conversaAtualId = conversaAtualId;
    }
    
   // Carrega as mensagens salvas da conversa selecionada
    private void carregarMensagens(
        Conversation conversa,
        VBox mensagens
    ) {
        mensagens.getChildren().clear();
        
        // Confirma que a conversa pertence ao usuário logado
        boolean pertence =
            conversationService.pertenceAoUsuario(
                conversa.getId(),
                SessionManager.getUsuarioAtual()
            );

        if (!pertence) {
            return;
        }

        // Busca as mensagens somente após validar o proprietário
        var mensagensSalvas =
            chatService.buscarMensagens(conversa.getId());

        for (Message message : mensagensSalvas) {

            boolean ehUsuario =
                message.getUsuario().equals(
                    SessionManager.getUsuarioAtual()
                );

            adicionarMensagem(
                mensagens,
                (ehUsuario ? "Você: " : "IA: ")
                    + message.getMensagem(),
                ehUsuario
            );
        }
    }
    
    // Define a ação para uma nova conversa
    public void setAoCriarNovaConversa(
        java.util.function.Consumer<Conversation> aoCriarNovaConversa
    ) {
        this.aoCriarNovaConversa = aoCriarNovaConversa;
    }
    
   
}