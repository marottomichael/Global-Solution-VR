package br.com.globalsolution.service;

import br.com.globalsolution.dto.request.AtualizarFonteDadosRequest;
import br.com.globalsolution.dto.request.CriarFonteDadosRequest;
import br.com.globalsolution.dto.response.FonteDadosResponse;
import br.com.globalsolution.exception.RegraNegocioException;
import br.com.globalsolution.exception.RecursoNaoEncontradoException;
import br.com.globalsolution.model.FonteDados;
import br.com.globalsolution.model.enums.TipoFonteDados;
import br.com.globalsolution.repository.FonteDadosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FonteDadosService {

    private static final BigDecimal CONFIABILIDADE_PADRAO = new BigDecimal("70.0");

    private final FonteDadosRepository fonteDadosRepository;

    public FonteDadosService(FonteDadosRepository fonteDadosRepository) {
        this.fonteDadosRepository = fonteDadosRepository;
    }

    @Transactional
    public FonteDadosResponse criar(CriarFonteDadosRequest request) {
        FonteDados fonte = new FonteDados();
        fonte.setNome(request.getNome());
        fonte.setTipoFonte(request.getTipoFonte());
        fonte.setDescricao(request.getDescricao());
        fonte.setUrlReferencia(request.getUrlReferencia());
        fonte.setConfiabilidadeBase(request.getConfiabilidadeBase() != null
                ? request.getConfiabilidadeBase()
                : CONFIABILIDADE_PADRAO);

        return toResponse(fonteDadosRepository.save(fonte));
    }

    @Transactional
    public FonteDadosResponse atualizar(Long id, AtualizarFonteDadosRequest request) {
        FonteDados fonte = fonteDadosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fonte de dados não encontrada"));

        fonte.setNome(request.getNome());
        fonte.setTipoFonte(request.getTipoFonte());
        fonte.setDescricao(request.getDescricao());
        fonte.setUrlReferencia(request.getUrlReferencia());
        fonte.setConfiabilidadeBase(request.getConfiabilidadeBase() != null
                ? request.getConfiabilidadeBase()
                : CONFIABILIDADE_PADRAO);

        return toResponse(fonteDadosRepository.save(fonte));
    }

    @Transactional
    public void excluir(Long id) {
        FonteDados fonte = fonteDadosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fonte de dados não encontrada"));

        if (fonteDadosRepository.countVinculosComSolicitacao(id) > 0) {
            throw new RegraNegocioException("Fonte de dados vinculada a solicitações");
        }

        fonteDadosRepository.delete(fonte);
    }

    @Transactional(readOnly = true)
    public FonteDadosResponse buscarPorId(Long id) {
        FonteDados fonte = fonteDadosRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fonte de dados não encontrada"));
        return toResponse(fonte);
    }

    @Transactional(readOnly = true)
    public List<FonteDadosResponse> listar() {
        return fonteDadosRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FonteDadosResponse> listarPorTipo(TipoFonteDados tipoFonte) {
        return fonteDadosRepository.findByTipoFonte(tipoFonte).stream()
                .map(this::toResponse)
                .toList();
    }

    private FonteDadosResponse toResponse(FonteDados fonte) {
        FonteDadosResponse response = new FonteDadosResponse();
        response.setId(fonte.getId());
        response.setNome(fonte.getNome());
        response.setTipoFonte(fonte.getTipoFonte());
        response.setDescricao(fonte.getDescricao());
        response.setUrlReferencia(fonte.getUrlReferencia());
        response.setConfiabilidadeBase(fonte.getConfiabilidadeBase());
        return response;
    }
}
