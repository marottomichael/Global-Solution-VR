package br.com.globalsolution.controller;

import br.com.globalsolution.dto.request.AtualizarFonteDadosRequest;
import br.com.globalsolution.dto.request.CriarFonteDadosRequest;
import br.com.globalsolution.dto.response.FonteDadosResponse;
import br.com.globalsolution.model.enums.TipoFonteDados;
import br.com.globalsolution.service.FonteDadosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fontes-dados")
@CrossOrigin(origins = "*")
public class FonteDadosController {

    private final FonteDadosService fonteDadosService;

    public FonteDadosController(FonteDadosService fonteDadosService) {
        this.fonteDadosService = fonteDadosService;
    }

    @PostMapping
    public ResponseEntity<FonteDadosResponse> criar(@Valid @RequestBody CriarFonteDadosRequest request) {
        FonteDadosResponse response = fonteDadosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonteDadosResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fonteDadosService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<FonteDadosResponse>> listar() {
        return ResponseEntity.ok(fonteDadosService.listar());
    }

    @GetMapping("/tipo/{tipoFonte}")
    public ResponseEntity<List<FonteDadosResponse>> listarPorTipo(@PathVariable TipoFonteDados tipoFonte) {
        return ResponseEntity.ok(fonteDadosService.listarPorTipo(tipoFonte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FonteDadosResponse> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody AtualizarFonteDadosRequest request) {
        return ResponseEntity.ok(fonteDadosService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        fonteDadosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
