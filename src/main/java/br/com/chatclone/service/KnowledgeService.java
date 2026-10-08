package br.com.chatclone.service;

import java.util.List;
import java.util.UUID;

import br.com.chatclone.model.Knowledge;
import br.com.chatclone.repository.KnowledgeRepository;

public class KnowledgeService {
	
	 private final KnowledgeRepository repository;

	    public KnowledgeService(KnowledgeRepository repository) {
	        // Recebe o repository responsável pelo MongoDB
	        this.repository = repository;
	    }

	    public void adicionarConhecimento(
	        String titulo,
	        String conteudo,
	        List<String> palavrasChave
	    ) {

	        // Gera um identificador único para o conhecimento
	        String id = UUID.randomUUID().toString();

	        Knowledge knowledge = new Knowledge(
	            id,
	            titulo,
	            conteudo,
	            palavrasChave
	        );

	        // Persiste o conhecimento no MongoDB
	        repository.salvar(knowledge);
	    }

	    public List<Knowledge> buscarTodos() {
	        // Recupera os conhecimentos disponíveis para o RAG
	        return repository.buscarTodos();
	    }

}
