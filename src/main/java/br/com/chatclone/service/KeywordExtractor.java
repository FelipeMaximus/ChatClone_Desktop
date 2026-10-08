package br.com.chatclone.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordExtractor {
	
	  // Palavras que não ajudam na busca do conhecimento
    private static final List<String> STOP_WORDS = List.of(
        "a", "o", "as", "os",
        "de", "da", "do", "das", "dos",
        "em", "no", "na", "nos", "nas",
        "para", "por", "com",
        "um", "uma",
        "que", "é", "e"
    );

    public List<String> extrair(String pergunta) {

        // Converte para minúsculas e remove caracteres especiais
        String texto = pergunta
            .toLowerCase()
            .replaceAll("[^a-záéíóúãõâêôç0-9 ]", "");

        // Separa a pergunta em palavras
        return Arrays.stream(texto.split("\\s+"))

            // Remove palavras sem relevância
            .filter(palavra -> !STOP_WORDS.contains(palavra))

            // Ignora palavras vazias
            .filter(palavra -> !palavra.isBlank())

            .collect(Collectors.toList());
    }

}
