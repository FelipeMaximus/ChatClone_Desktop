package br.com.chatclone.repository;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//Representa uma mensagem da aplicação
import br.com.chatclone.model.Message;

public class MessageRepository {
	
	private final MongoCollection<Document> collection;

	   public MessageRepository(MongoDatabase database) {

	       // Acessa a coleção "chatclone"
	       collection = database.getCollection("messages");
	   }

	   public void salvarMensagem(Message message) {

		    // Cria o documento com os dados da mensagem
		    Document documento = new Document()
		    	.append("conversationId", message.getConversationId())
		        .append("usuario", message.getUsuario())
		        .append("mensagem", message.getMensagem())
		        .append("dataHora", message.getDataHora().toString()); // Salva a data/hora
 
		    // Salva a mensagem no MongoDB
		    collection.insertOne(documento);
	   }
	   
	// Busca todas as mensagens de uma conversa
	   public java.util.List<Message> buscarPorConversa(String conversationId) {

	       java.util.List<Message> mensagens = new java.util.ArrayList<>();

	       for (Document documento : collection.find(
	               new Document("conversationId", conversationId))) {
	           Message mensagem = new Message(
	               documento.getString("conversationId"),
	               documento.getString("usuario"),
	               documento.getString("mensagem")
	           );
	           mensagens.add(mensagem);
	       }
	       return mensagens;
	   }
	   
	   // Exclui todas as mensagens de uma conversa
	   public void excluirPorConversa(String conversationId) {
	       collection.deleteMany(
	           new Document("conversationId", conversationId)
	       );
	   }

}
