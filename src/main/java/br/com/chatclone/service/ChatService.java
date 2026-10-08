package br.com.chatclone.service;

import br.com.chatclone.model.Message;
import br.com.chatclone.repository.MessageRepository;
import br.com.chatclone.service.RagService;

public class ChatService {
	
	private final MessageRepository repository;
	private final GroqService groqService; // Serviço responsável pela IA
	private final RagService ragService;

    public ChatService(MessageRepository repository, GroqService groqService, RagService ragService) {
        // Recebe o repository que será usado pelo serviço
        this.repository = repository;
        this.groqService = groqService;
        this.ragService = ragService;
    }

    public void enviarMensagem(Message message) {
        // Encaminha a mensagem para persistência
        repository.salvarMensagem(message);
    }
    
    // Busca as mensagens de uma conversa
    public java.util.List<Message> buscarMensagens(String conversationId) {
        return repository.buscarPorConversa(conversationId);
    }
    
    // Remove todas as mensagens da conversa
    public void excluirMensagensDaConversa(String conversationId) {
        repository.excluirPorConversa(conversationId);
    }
    
    // Envia a pergunta para a IA
    public String perguntarIA(String pergunta) {
    	 String contexto = ragService.criarContexto(pergunta);

    	    return groqService.enviarPergunta(
    	        pergunta,
    	        contexto
    	    );
    }

    
}
