package br.com.fiap.petfamily.repository;

import br.com.fiap.petfamily.entity.Lembrete;
import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LembreteRepository extends JpaRepository<Lembrete, Long> {

    Page<Lembrete> findByStatus(StatusLembrete status, Pageable pageable);

    List<Lembrete> findByPetIdAndStatus(Long petId, StatusLembrete status);

    long countByStatus(StatusLembrete status);
}
