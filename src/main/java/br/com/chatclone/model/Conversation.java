package br.com.chatclone.model;

import java.time.LocalDateTime;
import java.util.UUID;

//AUTENTICACAO DE CADA CONVERSA AO USUARIO
public class Conversation {

	private String id;
	    private String usuario;
	    private String titulo;
	    private LocalDateTime dataCriacao;

	    public Conversation(String usuario, String titulo) {
	    	 // Gera um identificador único para a conversa
	        this.id = UUID.randomUUID().toString();
	        
	        // Identifica o dono da conversa
	        this.usuario = usuario;
	        // Define o título da conversa
	        this.titulo = titulo;
	        // Registra a criação
	        this.dataCriacao = LocalDateTime.now();
	    }
	    
	    
	    //NECESSARIO DOIS CONSTRUTORES PARA DUAS UUID DAS CONVERSAS
	    public Conversation(
	    	    String id,
	    	    String usuario,
	    	    String titulo,
	    	    LocalDateTime dataCriacao
	    	) {
	    	    // Reaproveita os dados existentes do MongoDB
	    	    this.id = id;
	    	    this.usuario = usuario;
	    	    this.titulo = titulo;
	    	    this.dataCriacao = dataCriacao;
	    	}
	    
	    public String getId() {
	        // Retorna o identificador da conversa
	        return id;
	    }

	    public String getUsuario() {
	        return usuario;
	    }

	    public String getTitulo() {
	        return titulo;
	    }

	    public LocalDateTime getDataCriacao() {
	        return dataCriacao;
	    }

}
