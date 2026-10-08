package br.com.chatclone.repository;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import br.com.chatclone.model.Conversation;
import java.util.ArrayList;
import java.util.List;

public class ConversationRepository {
	
	private final MongoCollection<Document> collection;

    public ConversationRepository(MongoDatabase database) {
        // Acessa a coleção de conversas
        collection = database.getCollection("conversations");
    }

    public void salvarConversa(Conversation conversation) {
        // Converte a conversa para documento MongoDB
        Document documento = new Document()
            .append("id", conversation.getId())
            .append("usuario", conversation.getUsuario())
            .append("titulo", conversation.getTitulo())
            .append("dataCriacao",
                    conversation.getDataCriacao().toString());

        // Salva a conversa
        collection.insertOne(documento);
    }
    
    public List<Conversation> buscarPorUsuario(String usuario) {
        // Busca somente as conversas do usuário logado
        List<Conversation> conversas = new ArrayList<>();

        for (Document documento : collection.find(
                new Document("usuario", usuario))) {

        	Conversation conversa = new Conversation(
        		    documento.getString("id"),
        		    documento.getString("usuario"),
        		    documento.getString("titulo"),
        		    java.time.LocalDateTime.parse(
        		        documento.getString("dataCriacao")
        		    )
        		);

            conversas.add(conversa);
        }

        return conversas;
    }
    
    // Altera o título somente da conversa pertencente ao usuário
    public void renomearConversa(
        String id,
        String usuario,
        String novoTitulo
    ) {
        collection.updateOne(
            new Document("id", id)
                .append("usuario", usuario),
            new Document("$set",
                new Document("titulo", novoTitulo)
            )
        );
    }
    
    // Exclui somente a conversa pertencente ao usuário
    public void excluirConversa(String id, String usuario) {
        collection.deleteOne(
            new Document("id", id)
                .append("usuario", usuario)
        );
    }
    
    // Verifica se a conversa pertence ao usuário
    public boolean pertenceAoUsuario(String id, String usuario) {
        return collection.find(
            new Document("id", id)
                .append("usuario", usuario)
        ).first() != null;
    }

}
