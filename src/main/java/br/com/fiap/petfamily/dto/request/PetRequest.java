package br.com.fiap.petfamily.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PetRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Espécie é obrigatória")
    private String especie;

    private String raca;

    @Positive(message = "Idade deve ser um valor positivo")
    private Integer idade;

    @Positive(message = "Peso deve ser um valor positivo")
    private Double peso;

    private String observacoesSaude;

    @NotNull(message = "ID do tutor é obrigatório")
    private Long tutorId;
}
