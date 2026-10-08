package br.com.chatclone.controller;

import br.com.chatclone.service.UserService;
import br.com.chatclone.util.SessionManager;

public class LoginController {
	
	  private final UserService userService;

	    public LoginController(UserService userService) {
	        // Recebe o serviço responsável pela autenticação
	        this.userService = userService;
	    }

	    public boolean autenticar(String email, String senha) {
	        // Valida as credenciais usando o UserService
	        boolean autenticado = userService.autenticar(email, senha);

	        if (autenticado) {
	            // Inicia a sessão com o e-mail autenticado
	            SessionManager.iniciarSessao(email);
	        }

	        return autenticado;
	    }

}
