package br.com.fiap.petfamily.dto.request;

import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LembreteRequest {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String descricao;

    @NotNull(message = "Data do lembrete é obrigatória")
    private LocalDate dataLembrete;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @NotNull(message = "Status é obrigatório")
    private StatusLembrete status;

    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;
}
