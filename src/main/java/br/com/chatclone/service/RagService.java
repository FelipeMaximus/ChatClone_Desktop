package br.com.chatclone.service;

import java.util.ArrayList;
import java.util.List;

import br.com.chatclone.model.Knowledge;

public class RagService {
	
	  private final KnowledgeService knowledgeService;
	    private final KeywordExtractor keywordExtractor;

	    public RagService(
	        KnowledgeService knowledgeService,
	        KeywordExtractor keywordExtractor
	    ) {
	        // Recebe os serviços necessários para o RAG
	        this.knowledgeService = knowledgeService;
	        this.keywordExtractor = keywordExtractor;
	    }

	    public List<Knowledge> buscar(String pergunta) {

	        // Transforma a pergunta em palavras relevantes
	        List<String> palavras = keywordExtractor.extrair(pergunta);

	        List<Knowledge> resultados = new ArrayList<>();

	        // Busca nos conhecimentos cadastrados
	        for (Knowledge knowledge : knowledgeService.buscarTodos()) {

	            for (String palavra : palavras) {

	                // Verifica se alguma palavra da pergunta
	                // está cadastrada nas palavras-chave
	                if (knowledge.getPalavrasChave().contains(palavra)) {
	                    resultados.add(knowledge);
	                    break;
	                }
	            }
	        }

	        return resultados;
	    }
	    
	    //transforma os conhecimentos encontrados em contexto para a IA
	    public String criarContexto(String pergunta) {

	        List<Knowledge> conhecimentos = buscar(pergunta);

	        StringBuilder contexto = new StringBuilder();

	        for (Knowledge knowledge : conhecimentos) {

	            // Adiciona título e conteúdo ao contexto
	            contexto.append("Título: ")
	                    .append(knowledge.getTitulo())
	                    .append("\n");

	            contexto.append("Conteúdo: ")
	                    .append(knowledge.getConteudo())
	                    .append("\n\n");
	        }

	        return contexto.toString();
	    }

}
