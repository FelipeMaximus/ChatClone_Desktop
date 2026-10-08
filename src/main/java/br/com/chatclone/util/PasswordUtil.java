package br.com.chatclone.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

//SENHA DE TELA DE LOGIN
public class PasswordUtil {

	private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    public static String gerarHash(String senha) {
        try {
            // Gera um salt aleatório para esta senha
            byte[] salt = new byte[SALT_LENGTH];
            new SecureRandom().nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                senha.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );

            SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Guarda salt + hash para futura validação
            return Base64.getEncoder().encodeToString(salt)
                + ":"
                + Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Impede o sistema de continuar sem o algoritmo
            throw new IllegalStateException(
                "Não foi possível gerar o hash da senha.", e
            );
        }
    }
    
    public static boolean verificarSenha(String senha, String senhaArmazenada) {
        try {
            // Separa o salt e o hash armazenados
            String[] partes = senhaArmazenada.split(":");

            if (partes.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(partes[0]);
            byte[] hashOriginal = Base64.getDecoder().decode(partes[1]);

            PBEKeySpec spec = new PBEKeySpec(
                senha.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );

            SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hashInformado =
                factory.generateSecret(spec).getEncoded();

            // Compara o hash informado com o armazenado
            return java.security.MessageDigest.isEqual(
                hashOriginal,
                hashInformado
            );

        } catch (Exception e) {
            // Senha inválida ou hash em formato incorreto
            return false;
        }
    }
}
