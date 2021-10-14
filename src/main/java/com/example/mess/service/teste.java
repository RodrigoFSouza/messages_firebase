package com.example.mess.service;

import java.time.LocalDateTime;

public class teste {
    public static void main(String[] args) {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime ontem = hoje.minusDays(1);
        LocalDateTime umMesAtras = hoje.minusDays(30);

        if (hoje.compareTo(ontem) > 0) {
            System.out.println(hoje.minusDays(2).compareTo(ontem));
        }

    }
}
