package br.com.globalsolution.service;

import br.com.globalsolution.dto.request.AtualizarUsuarioRequest;
import br.com.globalsolution.dto.request.CriarUsuarioRequest;
import br.com.globalsolution.dto.response.UsuarioResponse;
import br.com.globalsolution.exception.RegraNegocioException;
import br.com.globalsolution.exception.RecursoNaoEncontradoException;
import br.com.globalsolution.model.Usuario;
import br.com.globalsolution.repository.AvaliacaoRepository;
import br.com.globalsolution.repository.SolicitacaoExperienciaRepository;
import br.com.globalsolution.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoExperienciaRepository solicitacaoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          SolicitacaoExperienciaRepository solicitacaoRepository,
                          AvaliacaoRepository avaliacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Transactional
    public UsuarioResponse criar(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setTipoUsuario(resolverTipoUsuario(request.getTipoUsuario()));
        usuario.setDataCriacao(LocalDateTime.now());

        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, AtualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        usuarioRepository.findByEmail(request.getEmail()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new RegraNegocioException("Email já cadastrado");
            }
        });

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setTipoUsuario(resolverTipoUsuario(request.getTipoUsuario()));

        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void excluir(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (!solicitacaoRepository.findByUsuarioId(id).isEmpty()) {
            throw new RegraNegocioException("Usuário possui solicitações vinculadas");
        }
        if (!avaliacaoRepository.findByUsuarioId(id).isEmpty()) {
            throw new RegraNegocioException("Usuário possui avaliações vinculadas");
        }

        usuarioRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        return toResponse(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private String resolverTipoUsuario(String tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.isBlank()) {
            return "USUARIO";
        }
        return tipoUsuario;
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTipoUsuario(usuario.getTipoUsuario());
        response.setDataCriacao(usuario.getDataCriacao());
        return response;
    }
}
