package br.com.chatclone.repository;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import br.com.chatclone.model.User;
import java.util.Optional;

public class UserRepository {
	
	 private final MongoCollection<Document> collection;

	    public UserRepository(MongoDatabase database) {
	        // Acessa a coleção de usuários
	        collection = database.getCollection("users");
	    }

	    public void salvarUsuario(User user) {
	        // Converte o usuário para documento MongoDB
	        Document documento = new Document()
	            .append("nome", user.getNome())
	            .append("email", user.getEmail())
	            .append("senha", user.getSenha());

	        // Salva o usuário
	        collection.insertOne(documento);
	    }
	    
	    //LOGIN DE USUÁRIO
	    public Optional<User> buscarPorEmail(String email) {
	        // Procura o usuário pelo e-mail
	        Document documento = collection.find(
	            new Document("email", email)
	        ).first();

	        if (documento == null) {
	            // Usuário não encontrado
	            return Optional.empty();
	        }

	        // Reconstrói o usuário a partir do MongoDB
	        User user = new User(
	            documento.getString("nome"),
	            documento.getString("email"),
	            documento.getString("senha")
	        );

	        return Optional.of(user);
	    }

}
