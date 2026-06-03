package br.com.globalsolution.controller;

import br.com.globalsolution.dto.request.CriarSolicitacaoExperienciaRequest;
import br.com.globalsolution.dto.response.SolicitacaoExperienciaResponse;
import br.com.globalsolution.model.enums.CategoriaExperiencia;
import br.com.globalsolution.model.enums.StatusGeracao;
import br.com.globalsolution.service.SolicitacaoExperienciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin(origins = "*")
public class SolicitacaoExperienciaController {

    private final SolicitacaoExperienciaService solicitacaoService;

    public SolicitacaoExperienciaController(SolicitacaoExperienciaService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public ResponseEntity<SolicitacaoExperienciaResponse> criar(
            @Valid @RequestBody CriarSolicitacaoExperienciaRequest request) {
        SolicitacaoExperienciaResponse response = solicitacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoExperienciaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SolicitacaoExperienciaResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(solicitacaoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/categoria/{categoriaExperiencia}")
    public ResponseEntity<List<SolicitacaoExperienciaResponse>> listarPorCategoria(
            @PathVariable CategoriaExperiencia categoriaExperiencia) {
        return ResponseEntity.ok(solicitacaoService.listarPorCategoria(categoriaExperiencia));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SolicitacaoExperienciaResponse>> listarPorStatus(
            @PathVariable StatusGeracao status) {
        return ResponseEntity.ok(solicitacaoService.listarPorStatus(status));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<SolicitacaoExperienciaResponse> atualizarStatus(
            @PathVariable Long id,
            @PathVariable StatusGeracao status) {
        return ResponseEntity.ok(solicitacaoService.atualizarStatus(id, status));
    }
}
