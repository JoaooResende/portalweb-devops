package br.com.fiap.petfamily.service;

import br.com.fiap.petfamily.dto.request.ConsultaRequest;
import br.com.fiap.petfamily.dto.response.ConsultaResponse;
import br.com.fiap.petfamily.entity.Consulta;
import br.com.fiap.petfamily.entity.Consulta.StatusConsulta;
import br.com.fiap.petfamily.entity.Pet;
import br.com.fiap.petfamily.exception.ResourceNotFoundException;
import br.com.fiap.petfamily.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PetService petService;

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "consultas", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public ConsultaResponse criar(ConsultaRequest request) {
        Pet pet = petService.findById(request.getPetId());
        Consulta consulta = Consulta.builder()
                .data(request.getData())
                .horario(request.getHorario())
                .tipoConsulta(request.getTipoConsulta())
                .status(request.getStatus())
                .observacoes(request.getObservacoes())
                .pet(pet)
                .build();
        return toResponse(consultaRepository.save(consulta));
    }

    public Page<ConsultaResponse> listar(StatusConsulta status, Pageable pageable) {
        if (status != null) {
            return consultaRepository.findByStatus(status, pageable).map(this::toResponse);
        }
        return consultaRepository.findAll(pageable).map(this::toResponse);
    }

    public ConsultaResponse buscarPorId(Long id) {
        return toResponse(findById(id));
    }

    public Page<ConsultaResponse> listarFuturas(Pageable pageable) {
        return consultaRepository.findConsultasFuturas(LocalDate.now(), pageable).map(this::toResponse);
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "consultas", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public ConsultaResponse atualizar(Long id, ConsultaRequest request) {
        Consulta consulta = findById(id);
        Pet pet = petService.findById(request.getPetId());
        consulta.setData(request.getData());
        consulta.setHorario(request.getHorario());
        consulta.setTipoConsulta(request.getTipoConsulta());
        consulta.setStatus(request.getStatus());
        consulta.setObservacoes(request.getObservacoes());
        consulta.setPet(pet);
        return toResponse(consultaRepository.save(consulta));
    }

    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "consultas", allEntries = true),
        @CacheEvict(value = "dashboard", allEntries = true)
    })
    public void deletar(Long id) {
        consultaRepository.delete(findById(id));
    }

    public Consulta findById(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada com id: " + id));
    }

    private ConsultaResponse toResponse(Consulta c) {
        return ConsultaResponse.builder()
                .id(c.getId())
                .data(c.getData())
                .horario(c.getHorario())
                .tipoConsulta(c.getTipoConsulta())
                .status(c.getStatus())
                .observacoes(c.getObservacoes())
                .petId(c.getPet().getId())
                .petNome(c.getPet().getNome())
                .tutorNome(c.getPet().getTutor().getNome())
                .build();
    }
}
