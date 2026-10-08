package br.com.chatclone.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
//Biblioteca que lê o arquivo .env
import io.github.cdimascio.dotenv.Dotenv;
//Representa o banco de dados MongoDB
import com.mongodb.client.MongoDatabase;

//CLASSE CONEXAO MONGO
public class MongoConfig {
	
	// Carrega as configurações do arquivo .env
	private static final Dotenv DOTENV = Dotenv.load();

	// String de conexão do MongoDB Atlas
    private static final String URI = DOTENV.get("MONGODB_URI");

    // Cliente responsável pela conexão com o Atlas
    private final MongoClient mongoClient;

    public MongoConfig() {
    	
    	 // Impede iniciar sem a variável de ambiente configurada
        if (URI == null || URI.isBlank()) {
            throw new IllegalStateException(
                "A variável de ambiente MONGODB_URI não foi configurada."
            );
        }
        
        mongoClient = MongoClients.create(URI);
    }

    // Retorna o cliente MongoDB para as demais classes
    public MongoClient getMongoClient() {
        return mongoClient;
    }
    
   // Retorna o banco de dados usado pelo ChatClone
    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase("chatclone");
    }
    
    // Testa se o MongoDB está acessível
    public void testarConexao() {
        mongoClient.listDatabaseNames().first();
        System.out.println("Conexão com MongoDB Atlas realizada com sucesso!");
    }
}
