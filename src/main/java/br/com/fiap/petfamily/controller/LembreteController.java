package br.com.fiap.petfamily.controller;

import br.com.fiap.petfamily.dto.request.LembreteRequest;
import br.com.fiap.petfamily.dto.response.LembreteResponse;
import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import br.com.fiap.petfamily.service.LembreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lembretes")
@RequiredArgsConstructor
@Tag(name = "Lembretes", description = "Lembretes preventivos de saúde para pets")
public class LembreteController {

    private final LembreteService lembreteService;

    @PostMapping
    @Operation(summary = "Criar novo lembrete preventivo")
    @ApiResponse(responseCode = "201", description = "Lembrete criado com sucesso")
    public ResponseEntity<LembreteResponse> criar(@Valid @RequestBody LembreteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lembreteService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar lembretes com filtro por status")
    public ResponseEntity<Page<LembreteResponse>> listar(
            @RequestParam(required = false) StatusLembrete status,
            @PageableDefault(size = 10, sort = "dataLembrete") Pageable pageable) {
        return ResponseEntity.ok(lembreteService.listar(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lembrete por ID")
    public ResponseEntity<LembreteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(lembreteService.buscarPorId(id));
    }

    @GetMapping("/pet/{petId}/pendentes")
    @Operation(summary = "Listar lembretes pendentes de um pet específico")
    public ResponseEntity<List<LembreteResponse>> listarPendentesPorPet(@PathVariable Long petId) {
        return ResponseEntity.ok(lembreteService.listarPendentesPorPet(petId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lembrete")
    public ResponseEntity<LembreteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LembreteRequest request) {
        return ResponseEntity.ok(lembreteService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover lembrete")
    @ApiResponse(responseCode = "204", description = "Lembrete removido com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lembreteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
