package br.com.chatclone.model;

public class User {
	
	private String nome;
    private String email;
    private String senha;

    public User(String nome, String email, String senha) {
        // Define os dados do usuário
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

}
