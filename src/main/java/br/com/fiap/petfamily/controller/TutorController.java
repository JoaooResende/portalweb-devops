package br.com.fiap.petfamily.controller;

import br.com.fiap.petfamily.dto.request.TutorRequest;
import br.com.fiap.petfamily.dto.response.TutorResponse;
import br.com.fiap.petfamily.service.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/tutores")
@RequiredArgsConstructor
@Tag(name = "Tutores", description = "Gerenciamento de tutores de pets")
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    @Operation(summary = "Cadastrar novo tutor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tutor cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TutorResponse> criar(@Valid @RequestBody TutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.criar(request));
    }

    @GetMapping
    @Operation(summary = "Listar tutores com paginação e filtro por nome")
    public ResponseEntity<Page<TutorResponse>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(tutorService.listar(nome, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tutor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tutor encontrado"),
        @ApiResponse(responseCode = "404", description = "Tutor não encontrado")
    })
    public ResponseEntity<TutorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do tutor")
    public ResponseEntity<TutorResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TutorRequest request) {
        return ResponseEntity.ok(tutorService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover tutor e seus pets")
    @ApiResponse(responseCode = "204", description = "Tutor removido com sucesso")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
