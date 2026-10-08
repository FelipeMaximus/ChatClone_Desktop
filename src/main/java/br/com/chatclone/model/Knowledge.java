package br.com.chatclone.model;

import java.time.LocalDateTime;

public class Knowledge {

	    private String id;
	    private String titulo;
	    private String conteudo;
	    private java.util.List<String> palavrasChave;
	    private LocalDateTime dataCriacao;

	    public Knowledge(
	        String id,
	        String titulo,
	        String conteudo,
	        java.util.List<String> palavrasChave
	    ) {
	        this.id = id;
	        this.titulo = titulo;
	        this.conteudo = conteudo;
	        this.palavrasChave = palavrasChave;
	        this.dataCriacao = LocalDateTime.now();
	    }

	    public String getId() {
	        return id;
	    }

	    public String getTitulo() {
	        return titulo;
	    }

	    public String getConteudo() {
	        return conteudo;
	    }

	    public java.util.List<String> getPalavrasChave() {
	        return palavrasChave;
	    }

	    public LocalDateTime getDataCriacao() {
	        return dataCriacao;
	    }

}
