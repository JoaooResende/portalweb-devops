package br.com.fiap.petfamily.controller;

import br.com.fiap.petfamily.dto.request.ConsultaRequest;
import br.com.fiap.petfamily.dto.response.ConsultaResponse;
import br.com.fiap.petfamily.entity.Consulta.StatusConsulta;
import br.com.fiap.petfamily.service.ConsultaService;
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

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Registro e acompanhamento de consultas veterinárias")
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping
    @Operation(summary = "Registrar nova consulta veterinária")
    @ApiResponse(responseCode = "201", description = "Consulta registrada com sucesso")
    public ResponseEntity<ConsultaResponse> criar(@Valid @RequestBody ConsultaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar consultas com filtro por status")
    public ResponseEntity<Page<ConsultaResponse>> listar(
            @RequestParam(required = false) StatusConsulta status,
            @PageableDefault(size = 10, sort = "data") Pageable pageable) {
        return ResponseEntity.ok(consultaService.listar(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar consulta por ID")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/futuras")
    @Operation(summary = "Listar consultas agendadas a partir de hoje")
    public ResponseEntity<Page<ConsultaResponse>> listarFuturas(
            @PageableDefault(size = 10, sort = "data") Pageable pageable) {
        return ResponseEntity.ok(consultaService.listarFuturas(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consulta existente")
    public ResponseEntity<ConsultaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultaRequest request) {
        return ResponseEntity.ok(consultaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover consulta")
    @ApiResponse(responseCode = "204", description = "Consulta removida com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
