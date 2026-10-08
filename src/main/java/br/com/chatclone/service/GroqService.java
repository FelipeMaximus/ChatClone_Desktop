package br.com.chatclone.service;

import br.com.chatclone.config.GroqConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqService {
	    // Guarda a chave da API
	    private final String apiKey;
	    
	    private static final java.net.http.HttpClient CLIENT =
	    	    java.net.http.HttpClient.newBuilder()
	            .version(java.net.http.HttpClient.Version.HTTP_1_1)
	            .build();

	    public GroqService() {
	        
	        // Obtém a chave configurada no .env
	        this.apiKey = GroqConfig.getApiKey();
	    }

	    //MODELO DE IA DO GROQ
	    public String enviarPergunta(String pergunta, String contexto) {
	    	try {
	    		
	    		// ALTERAÇÃO AQUI: junta o conhecimento do RAG com a pergunta
	    	    String prompt = """
	    	        Responda à pergunta utilizando o contexto fornecido.

	    	        CONTEXTO:
	    	        %s

	    	        PERGUNTA:
	    	        %s
	    	        """.formatted(contexto, pergunta);
	           
	            // JSON enviado para o Groq
	            String json = """
	                {
	                    "model": "openai/gpt-oss-120b",
	                    "messages": [
	                        {
	                            "role": "user",
	                            "content": "%s"
	                        }
	                    ]
	                }
	                """.formatted(
	                		prompt
	                		.replace("\\", "\\\\")
	                		.replace("\"", "\\\"")
	                		.replace("\r", "\\r")
	                		.replace("\n", "\\n")
	                	);

	            // Monta a requisição para a API
	            java.net.http.HttpRequest request =
	                java.net.http.HttpRequest.newBuilder()
	                    .uri(java.net.URI.create(
	                        "https://api.groq.com/openai/v1/chat/completions"
	                    ))
	                    .header(
	                        "Authorization",
	                        "Bearer " + apiKey
	                    )
	                    .header(
	                        "Content-Type",
	                        "application/json"
	                    )
	                    .POST(
	                        java.net.http.HttpRequest.BodyPublishers
	                            .ofString(json)
	                    )
	                    .build();

	            // Envia a requisição
	            java.net.http.HttpResponse<String> response =
	                CLIENT.send(
	                    request,
	                    java.net.http.HttpResponse.BodyHandlers.ofString()
	                );

	         // Converte o JSON retornado pelo Groq
	            ObjectMapper mapper = new ObjectMapper();
	            
	            System.out.println("RESPOSTA GROQ: " + response.body());

	            try {
	            	// Interpreta o JSON retornado pelo Groq
	            	JsonNode respostaJson = mapper.readTree(response.body());

	            	// Extrai somente o texto da resposta da IA
	            	return respostaJson
	            	    .get("choices")
	            	    .get(0)
	            	    .get("message")
	            	    .get("content")
	            	    .asText();

	            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {

	                // Informa que a resposta não pôde ser interpretada
	                throw new RuntimeException(
	                    "Erro ao interpretar resposta do Groq.",
	                    e
	                );
	            }

	        } catch (Exception e) {
	            // Informa o erro de comunicação com o Groq
	            throw new RuntimeException(
	                "Erro ao comunicar com o Groq: " + e.getMessage(),
	                e
	            );
	        }
	    }

}
