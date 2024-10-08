package com.br.core.config;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Generator {
	
	private static final String CARACTERES_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String CARACTERES_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITOS = "0123456789";
    private static final String ESPECIAIS = "!@#$%^&*()-_=+[{]}|;:,<.>/?";

    private String num = "";
    private Long cont = 1L;

    public void sigla(UUID inicio) {
        if (inicio != null) {
            long mostSigBits = Math.abs(inicio.getMostSignificantBits());
            long can = cont + mostSigBits % 10000; // Ajuste para garantir que `can` seja um número de 4 dígitos
            num = String.format("%04d", can); // Formatação para 4 dígitos com zeros à esquerda
        } else {
            // Caso o UUID seja nulo, tratar o caso de acordo
            num = "0000"; // Valor padrão ou tratamento adequado
        }
    }

    public String getSigla(String nomeDepartamento) {
        return nomeDepartamento + this.num;
    }

    public static String password(int comprimento) {
        StringBuilder senha = new StringBuilder();
        Random random = new SecureRandom();

        // Adiciona pelo menos um caractere de cada tipo
        senha.append(pickRandom(CARACTERES_UPPER, random));
        senha.append(pickRandom(CARACTERES_LOWER, random));
        senha.append(pickRandom(DIGITOS, random));
        senha.append(pickRandom(ESPECIAIS, random));

        // Preenche o restante da senha com caracteres aleatórios
        for (int i = 4; i < comprimento; i++) {
            String conjuntoCaracteres = CARACTERES_UPPER + CARACTERES_LOWER + DIGITOS + ESPECIAIS;
            senha.append(pickRandom(conjuntoCaracteres, random));
        }

        // Embaralha a senha para torná-la mais aleatória
        char[] senhaArray = senha.toString().toCharArray();
        for (int i = 0; i < senhaArray.length; i++) {
            int j = random.nextInt(senhaArray.length);
            char temp = senhaArray[i];
            senhaArray[i] = senhaArray[j];
            senhaArray[j] = temp;
        }

        return new String(senhaArray);
    }

    private static char pickRandom(String conjuntoCaracteres, Random random) {
        int index = random.nextInt(conjuntoCaracteres.length());
        return conjuntoCaracteres.charAt(index);
    }
}