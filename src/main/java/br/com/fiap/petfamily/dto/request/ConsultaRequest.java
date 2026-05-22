package br.com.fiap.petfamily.dto.request;

import br.com.fiap.petfamily.entity.Consulta.StatusConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ConsultaRequest {

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    private LocalTime horario;

    @NotBlank(message = "Tipo da consulta é obrigatório")
    private String tipoConsulta;

    @NotNull(message = "Status é obrigatório")
    private StatusConsulta status;

    private String observacoes;

    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;
}
