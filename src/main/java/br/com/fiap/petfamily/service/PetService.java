package br.com.fiap.petfamily.service;

import br.com.fiap.petfamily.dto.request.PetRequest;
import br.com.fiap.petfamily.dto.response.PetResponse;
import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import br.com.fiap.petfamily.entity.Pet;
import br.com.fiap.petfamily.entity.Tutor;
import br.com.fiap.petfamily.exception.ResourceNotFoundException;
import br.com.fiap.petfamily.repository.LembreteRepository;
import br.com.fiap.petfamily.repository.PetRepository;
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
public class PetService {

    private final PetRepository petRepository;
    private final TutorService tutorService;
    private final LembreteRepository lembreteRepository;

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "pets", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public PetResponse criar(PetRequest request) {
        Tutor tutor = tutorService.findById(request.getTutorId());
        Pet pet = Pet.builder()
                .nome(request.getNome())
                .especie(request.getEspecie())
                .raca(request.getRaca())
                .idade(request.getIdade())
                .peso(request.getPeso())
                .observacoesSaude(request.getObservacoesSaude())
                .tutor(tutor)
                .build();
        return toResponse(petRepository.save(pet));
    }

    public Page<PetResponse> listar(Long tutorId, String especie, Pageable pageable) {
        if (tutorId != null) {
            return petRepository.findByTutorId(tutorId, pageable).map(this::toResponse);
        }
        if (especie != null && !especie.isBlank()) {
            return petRepository.findByEspecieIgnoreCase(especie, pageable).map(this::toResponse);
        }
        return petRepository.findAll(pageable).map(this::toResponse);
    }

    @Cacheable(value = "pets", key = "#id")
    public PetResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "pets", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public PetResponse atualizar(Long id, PetRequest request) {
        Pet pet = findById(id);
        Tutor tutor = tutorService.findById(request.getTutorId());
        pet.setNome(request.getNome());
        pet.setEspecie(request.getEspecie());
        pet.setRaca(request.getRaca());
        pet.setIdade(request.getIdade());
        pet.setPeso(request.getPeso());
        pet.setObservacoesSaude(request.getObservacoesSaude());
        pet.setTutor(tutor);
        return toResponse(petRepository.save(pet));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "pets", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public void deletar(Long id) {
        petRepository.delete(findById(id));
    }

    public Pet findById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com id: " + id));
    }

    private PetResponse toResponse(Pet pet) {
        long lembretesPendentes = lembreteRepository.findByPetIdAndStatus(pet.getId(), StatusLembrete.PENDENTE).size();
        return PetResponse.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .idade(pet.getIdade())
                .peso(pet.getPeso())
                .observacoesSaude(pet.getObservacoesSaude())
                .tutorId(pet.getTutor().getId())
                .tutorNome(pet.getTutor().getNome())
                .totalConsultas(pet.getConsultas().size())
                .totalLembretesPendentes((int) lembretesPendentes)
                .build();
    }
}
