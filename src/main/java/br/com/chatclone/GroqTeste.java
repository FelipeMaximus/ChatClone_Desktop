package br.com.chatclone;

import br.com.chatclone.service.GroqService;

public class GroqTeste {
	 public static void main(String[] args) {

	        // Cria o serviço que conversa com o Groq
	        GroqService groqService = new GroqService();
	        // Envia uma pergunta simples
	        String resposta =
	            groqService.enviarPergunta("Explique Java em uma frase.", "");
	        // Exibe a resposta no console
	        System.out.println(resposta);
	    }

}
