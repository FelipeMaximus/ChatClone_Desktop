package br.com.chatclone.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.chatclone.model.Knowledge;

public class KnowledgeRepository {
	
	 private final MongoCollection<Document> collection;

	    public KnowledgeRepository(MongoDatabase database) {
	        // Usa a collection destinada ao conhecimento do RAG
	        collection = database.getCollection("knowledge");
	    }

	    public void salvar(Knowledge knowledge) {

	        // Converte o objeto Java para documento MongoDB
	        Document documento = new Document()
	            .append("id", knowledge.getId())
	            .append("titulo", knowledge.getTitulo())
	            .append("conteudo", knowledge.getConteudo())
	            .append("palavrasChave", knowledge.getPalavrasChave())
	            .append("dataCriacao", knowledge.getDataCriacao().toString());

	        collection.insertOne(documento);
	    }

	    public List<Knowledge> buscarTodos() {

	        List<Knowledge> conhecimentos = new ArrayList<>();

	        // Recupera todos os conhecimentos cadastrados
	        for (Document documento : collection.find()) {

	            Knowledge knowledge = new Knowledge(
	                documento.getString("id"),
	                documento.getString("titulo"),
	                documento.getString("conteudo"),
	                documento.getList(
	                    "palavrasChave",
	                    String.class
	                )
	            );

	            conhecimentos.add(knowledge);
	        }

	        return conhecimentos;
	    }

}
