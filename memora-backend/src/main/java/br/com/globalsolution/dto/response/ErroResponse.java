package br.com.globalsolution.dto.response;

import java.time.LocalDateTime;

public record ErroResponse(
        LocalDateTime timestamp,
        int status,
        String mensagem
) {
}
