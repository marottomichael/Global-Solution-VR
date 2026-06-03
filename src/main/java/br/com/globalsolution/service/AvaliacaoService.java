package br.com.globalsolution.service;

import br.com.globalsolution.dto.request.CriarAvaliacaoRequest;
import br.com.globalsolution.dto.response.AvaliacaoResponse;
import br.com.globalsolution.exception.RecursoNaoEncontradoException;
import br.com.globalsolution.model.Avaliacao;
import br.com.globalsolution.model.SolicitacaoExperiencia;
import br.com.globalsolution.model.Usuario;
import br.com.globalsolution.repository.AvaliacaoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import br.com.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoExperienciaRepository solicitacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            UsuarioRepository usuarioRepository,
                            SolicitacaoExperienciaRepository solicitacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public AvaliacaoResponse criar(CriarAvaliacaoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        SolicitacaoExperiencia solicitacao = solicitacaoRepository.findById(request.getSolicitacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação não encontrada"));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setUsuario(usuario);
        avaliacao.setSolicitacao(solicitacao);
        avaliacao.setNota(request.getNota());
        avaliacao.setComentario(request.getComentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        return toResponse(avaliacaoRepository.save(avaliacao));
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponse> listarPorSolicitacao(Long solicitacaoId) {
        if (!solicitacaoRepository.existsById(solicitacaoId)) {
            throw new RecursoNaoEncontradoException("Solicitação não encontrada");
        }
        return avaliacaoRepository.findBySolicitacaoId(solicitacaoId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponse> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }
        return avaliacaoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    private AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        AvaliacaoResponse response = new AvaliacaoResponse();
        response.setId(avaliacao.getId());
        response.setUsuarioId(avaliacao.getUsuario().getId());
        response.setNomeUsuario(avaliacao.getUsuario().getNome());
        response.setSolicitacaoId(avaliacao.getSolicitacao().getId());
        response.setNota(avaliacao.getNota());
        response.setComentario(avaliacao.getComentario());
        response.setDataAvaliacao(avaliacao.getDataAvaliacao());
        return response;
    }
}
