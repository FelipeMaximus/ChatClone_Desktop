package br.com.chatclone.util;

public class SessionManager {
	
	private static String usuarioAtual;

    public static void iniciarSessao(String usuario) {
        // Guarda o usuário atualmente conectado
        usuarioAtual = usuario;
    }

    public static String getUsuarioAtual() {
        // Retorna o usuário da sessão
        return usuarioAtual;
    }

    public static void encerrarSessao() {
        // Limpa a sessão
        usuarioAtual = null;
    }
    
    public static void logout() {
        // Remove o usuário da sessão atual
        usuarioAtual = null;
    }

}
