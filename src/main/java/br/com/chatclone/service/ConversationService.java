package br.com.chatclone.service;

import br.com.chatclone.model.Conversation;
import br.com.chatclone.repository.ConversationRepository;
import java.util.List;
import br.com.chatclone.service.ChatService;

public class ConversationService {
	
	 private final ConversationRepository repository;
	 private final ChatService chatService;

	    public ConversationService(ConversationRepository repository, ChatService chatService) {
	        // Recebe o repository responsável pelas conversas
	    	 this.repository = repository;
	    	 this.chatService = chatService;
	    }

	    public Conversation criarConversa(String usuario, String titulo) {
	        // Cria uma nova conversa
	        Conversation conversation =
	            new Conversation(usuario, titulo);

	        // Persiste a conversa no MongoDB
	        repository.salvarConversa(conversation);

	        // Retorna a conversa com seu ID
	        return conversation;
	    }
	    
	    public List<Conversation> buscarPorUsuario(String usuario) {
	        // Busca somente as conversas do usuário
	        return repository.buscarPorUsuario(usuario);
	    }
	    
	    // Verifica se a conversa pertence ao usuário logado
	    public boolean pertenceAoUsuario(String id, String usuario) {
	        return repository.pertenceAoUsuario(id, usuario);
	    }
	    
	    // Solicita ao repositório a alteração do título
	    public void renomearConversa(
	        String id,
	        String usuario,
	        String novoTitulo
	    ) {
	        repository.renomearConversa(
	            id,
	            usuario,
	            novoTitulo
	        );
	    }
	    
	    // Exclui a conversa do MongoDB
	    public void excluirConversa(String id, String usuario) {

	        // Primeiro verifica se a conversa pertence ao usuário
	        boolean pertence = repository.pertenceAoUsuario(id, usuario);

	        if (!pertence) {
	            return;
	        }

	        // Só remove mensagens se o usuário for o dono
	        chatService.excluirMensagensDaConversa(id);

	        // Depois remove a conversa
	        repository.excluirConversa(id, usuario);
	    }

}
