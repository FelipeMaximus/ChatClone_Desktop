package br.com.chatclone.service;

import br.com.chatclone.model.User;
import br.com.chatclone.repository.UserRepository;
import br.com.chatclone.util.PasswordUtil;
import java.util.Optional;

public class UserService {
	
	private final UserRepository repository;

    public UserService(UserRepository repository) {
        // Recebe o repository de usuários
        this.repository = repository;
    }

    public void cadastrarUsuario(String nome, String email, String senha) {
        // Verifica se o e-mail já está cadastrado
        if (repository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                "Este e-mail já está cadastrado."
            );
        }

        // Gera o hash antes de salvar a senha
        String senhaHash = PasswordUtil.gerarHash(senha);

        // Cria o usuário com a senha protegida
        User user = new User(nome, email, senhaHash);

        // Persiste o usuário
        repository.salvarUsuario(user);
    }
    
    public Optional<User> buscarPorEmail(String email) {
        // Consulta o usuário através do repository
        return repository.buscarPorEmail(email);
    }
    
    
    
    public boolean autenticar(String email, String senha) {
        // Busca o usuário pelo e-mail
        Optional<User> usuario = repository.buscarPorEmail(email);

        if (usuario.isEmpty()) {
            // E-mail não cadastrado
            return false;
        }

        // Compara a senha informada com o hash armazenado
        return PasswordUtil.verificarSenha(
            senha,
            usuario.get().getSenha()
        );
    }

}
