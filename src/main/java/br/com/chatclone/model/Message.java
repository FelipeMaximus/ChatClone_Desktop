package br.com.chatclone.model;

public class Message {
	
	private String conversationId;
	private String usuario;
    private String mensagem;
    // Guarda o momento em que a mensagem foi criada
    private java.time.LocalDateTime dataHora;

    public Message( String conversationId, String usuario, String mensagem) {
    	
    	// Identifica a conversa da mensagem
        this.conversationId = conversationId;

        // Define quem enviou a mensagem
        this.usuario = usuario;

        // Define o conteúdo da mensagem
        this.mensagem = mensagem;
        
        // Registra automaticamente a data e hora
        this.dataHora = java.time.LocalDateTime.now();
    }
    
    public String getConversationId() {
        // Retorna a conversa relacionada
        return conversationId;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensagem() {
        return mensagem;
    }
    
    public java.time.LocalDateTime getDataHora() {
        // Retorna quando a mensagem foi criada
        return dataHora;
    }

}
