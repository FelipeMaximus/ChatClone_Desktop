package br.com.chatclone.config;

import io.github.cdimascio.dotenv.Dotenv;

public class GroqConfig {
	
	 private static final Dotenv DOTENV = Dotenv.load();

	    // Lê a chave da API do arquivo .env
	    private static final String API_KEY =
	        DOTENV.get("GROQ_API_KEY");

	    public static String getApiKey() {

	        // Impede a aplicação de continuar sem a chave
	        if (API_KEY == null || API_KEY.isBlank()) {
	            throw new IllegalStateException(
	                "A variável GROQ_API_KEY não foi configurada."
	            );
	        }

	        return API_KEY;
	    }

}
