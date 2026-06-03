package br.com.globalsolution.controller;

import br.com.globalsolution.dto.response.EstatisticaResponse;
import br.com.globalsolution.service.EstatisticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
@CrossOrigin(origins = "*")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<EstatisticaResponse> obterResumo() {
        return ResponseEntity.ok(estatisticaService.obterResumo());
    }
}
