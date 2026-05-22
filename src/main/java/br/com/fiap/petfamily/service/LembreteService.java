package br.com.fiap.petfamily.service;

import br.com.fiap.petfamily.dto.request.LembreteRequest;
import br.com.fiap.petfamily.dto.response.LembreteResponse;
import br.com.fiap.petfamily.entity.Lembrete;
import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import br.com.fiap.petfamily.entity.Pet;
import br.com.fiap.petfamily.exception.ResourceNotFoundException;
import br.com.fiap.petfamily.repository.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LembreteService {

    private final LembreteRepository lembreteRepository;
    private final PetService petService;

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "lembretes", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public LembreteResponse criar(LembreteRequest request) {
        Pet pet = petService.findById(request.getPetId());
        Lembrete lembrete = Lembrete.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .dataLembrete(request.getDataLembrete())
                .tipo(request.getTipo())
                .status(request.getStatus())
                .pet(pet)
                .build();
        return toResponse(lembreteRepository.save(lembrete));
    }

    public Page<LembreteResponse> listar(StatusLembrete status, Pageable pageable) {
        if (status != null) {
            return lembreteRepository.findByStatus(status, pageable).map(this::toResponse);
        }
        return lembreteRepository.findAll(pageable).map(this::toResponse);
    }

    public LembreteResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    public List<LembreteResponse> listarPendentesPorPet(Long petId) {
        petService.findById(petId);
        return lembreteRepository.findByPetIdAndStatus(petId, StatusLembrete.PENDENTE)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "lembretes", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public LembreteResponse atualizar(Long id, LembreteRequest request) {
        Lembrete lembrete = findById(id);
        Pet pet = petService.findById(request.getPetId());
        lembrete.setTitulo(request.getTitulo());
        lembrete.setDescricao(request.getDescricao());
        lembrete.setDataLembrete(request.getDataLembrete());
        lembrete.setTipo(request.getTipo());
        lembrete.setStatus(request.getStatus());
        lembrete.setPet(pet);
        return toResponse(lembreteRepository.save(lembrete));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "lembretes", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public void deletar(Long id) {
        lembreteRepository.delete(findById(id));
    }

    public Lembrete findById(Long id) {
        return lembreteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lembrete não encontrado com id: " + id));
    }

    private LembreteResponse toResponse(Lembrete l) {
        return LembreteResponse.builder()
                .id(l.getId())
                .titulo(l.getTitulo())
                .descricao(l.getDescricao())
                .dataLembrete(l.getDataLembrete())
                .tipo(l.getTipo())
                .status(l.getStatus())
                .petId(l.getPet().getId())
                .petNome(l.getPet().getNome())
                .build();
    }
}
