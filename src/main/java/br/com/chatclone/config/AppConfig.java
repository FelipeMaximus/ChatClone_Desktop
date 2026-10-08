package br.com.chatclone.config;

import com.mongodb.client.MongoDatabase;
import br.com.chatclone.repository.MessageRepository;
import br.com.chatclone.service.ChatService;
import br.com.chatclone.repository.UserRepository;
import br.com.chatclone.service.UserService;
import br.com.chatclone.repository.ConversationRepository;
import br.com.chatclone.service.ConversationService;
import br.com.chatclone.service.GroqService;
import br.com.chatclone.repository.KnowledgeRepository;
import br.com.chatclone.service.KnowledgeService;
import br.com.chatclone.service.KeywordExtractor;
import br.com.chatclone.service.RagService;

public class AppConfig {
	
	private final MongoConfig mongoConfig;

    public AppConfig() {
        // Inicializa a configuração do MongoDB
        mongoConfig = new MongoConfig();
    }

    public MongoDatabase getDatabase() {
        // Retorna o banco para os repositories
        return mongoConfig.getDatabase();
    }
    
    public ChatService getChatService() {
        MessageRepository repository =
                new MessageRepository(getDatabase());

            // Cria o serviço responsável pela comunicação com o Groq
            GroqService groqService = new GroqService();
            
            // Cria o serviço responsável pelo RAG
            RagService ragService = getRagService();

            // Injeta o Groq no ChatService
            return new ChatService(
                repository,
                groqService,
                ragService
            );
    }
    
    public KnowledgeService getKnowledgeService() {

        // Cria o repository usando o banco configurado
        KnowledgeRepository repository =
            new KnowledgeRepository(getDatabase());

        // Entrega o serviço responsável pelo conhecimento do RAG
        return new KnowledgeService(repository);
    }
    
    
    public UserService getUserService() {
        // Cria o repository de usuários
        UserRepository repository =
            new UserRepository(getDatabase());

        // Retorna o serviço de usuários
        return new UserService(repository);
    }
    
    public ConversationService getConversationService() {

        // Repositório responsável pelas conversas
        ConversationRepository repository =
            new ConversationRepository(getDatabase());

        // Serviço responsável pelas mensagens
        ChatService chatService = getChatService();

        // Injeta os dois serviços
        return new ConversationService(
            repository,
            chatService
        );
        
    
}
    
    //adicionar antes do último } da classe AppConfig
    public RagService getRagService() {

        // Obtém o serviço que acessa a base de conhecimento
        KnowledgeService knowledgeService = getKnowledgeService();

        // Cria o extrator de palavras-chave
        KeywordExtractor keywordExtractor = new KeywordExtractor();

        // Monta o serviço responsável pelo RAG
        return new RagService(
            knowledgeService,
            keywordExtractor
        );
    }
    
}
