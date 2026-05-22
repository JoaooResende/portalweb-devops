package br.com.fiap.petfamily.service;

import br.com.fiap.petfamily.dto.request.TutorRequest;
import br.com.fiap.petfamily.dto.response.TutorResponse;
import br.com.fiap.petfamily.entity.Tutor;
import br.com.fiap.petfamily.exception.ResourceNotFoundException;
import br.com.fiap.petfamily.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TutorService {

    private final TutorRepository tutorRepository;

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "tutores", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public TutorResponse criar(TutorRequest request) {
        Tutor tutor = Tutor.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .build();
        return toResponse(tutorRepository.save(tutor));
    }

    public Page<TutorResponse> listar(String nome, Pageable pageable) {
        if (nome != null && !nome.isBlank()) {
            return tutorRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::toResponse);
        }
        return tutorRepository.findAll(pageable).map(this::toResponse);
    }

    @Cacheable(value = "tutores", key = "#id")
    public TutorResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "tutores", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public TutorResponse atualizar(Long id, TutorRequest request) {
        Tutor tutor = findById(id);
        tutor.setNome(request.getNome());
        tutor.setEmail(request.getEmail());
        tutor.setTelefone(request.getTelefone());
        return toResponse(tutorRepository.save(tutor));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "tutores", allEntries = true),
        @CacheEvict(value = "pets", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public void deletar(Long id) {
        tutorRepository.delete(findById(id));
    }

    public Tutor findById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com id: " + id));
    }

    private TutorResponse toResponse(Tutor tutor) {
        return TutorResponse.builder()
                .id(tutor.getId())
                .nome(tutor.getNome())
                .email(tutor.getEmail())
                .telefone(tutor.getTelefone())
                .totalPets(tutor.getPets().size())
                .build();
    }
}
