package br.com.globalsolution.controller;

import br.com.globalsolution.dto.request.CriarAvaliacaoRequest;
import br.com.globalsolution.dto.response.AvaliacaoResponse;
import br.com.globalsolution.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> criar(@Valid @RequestBody CriarAvaliacaoRequest request) {
        AvaliacaoResponse response = avaliacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<AvaliacaoResponse>> listarPorSolicitacao(@PathVariable Long solicitacaoId) {
        return ResponseEntity.ok(avaliacaoService.listarPorSolicitacao(solicitacaoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AvaliacaoResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(avaliacaoService.listarPorUsuario(usuarioId));
    }
}
