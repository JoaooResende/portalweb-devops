package br.com.fiap.petfamily.config;

import br.com.fiap.petfamily.entity.*;
import br.com.fiap.petfamily.entity.Consulta.StatusConsulta;
import br.com.fiap.petfamily.entity.Lembrete.StatusLembrete;
import br.com.fiap.petfamily.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(
            TutorRepository tutorRepo,
            PetRepository petRepo,
            ConsultaRepository consultaRepo,
            LembreteRepository lembreteRepo,
            InteracaoIARepository interacaoRepo) {

        return args -> {
            if (tutorRepo.count() > 0) {
                log.info("Dados já existem, pulando inicialização.");
                return;
            }

            log.info("==> Carregando dados iniciais para testes...");

            // Tutores
            Tutor pedro = tutorRepo.save(Tutor.builder()
                    .nome("Pedro Vaz")
                    .email("pedro@petfamily.com")
                    .telefone("(11) 99999-0001")
                    .build());

            Tutor joao = tutorRepo.save(Tutor.builder()
                    .nome("João Victor")
                    .email("joao@petfamily.com")
                    .telefone("(11) 99999-0002")
                    .build());

            Tutor maria = tutorRepo.save(Tutor.builder()
                    .nome("Maria Silva")
                    .email("maria@petfamily.com")
                    .telefone("(11) 99999-0003")
                    .build());

            Tutor ana = tutorRepo.save(Tutor.builder()
                    .nome("Ana Oliveira")
                    .email("ana@petfamily.com")
                    .telefone("(11) 99999-0004")
                    .build());

            // Pets
            Pet thor = petRepo.save(Pet.builder()
                    .nome("Thor")
                    .especie("Cachorro")
                    .raca("Golden Retriever")
                    .idade(3)
                    .peso(28.5)
                    .observacoesSaude("Alergia a ração com frango. Prefere ração de cordeiro.")
                    .tutor(pedro)
                    .build());

            Pet luna = petRepo.save(Pet.builder()
                    .nome("Luna")
                    .especie("Gato")
                    .raca("Siamês")
                    .idade(2)
                    .peso(3.8)
                    .observacoesSaude("Castrada. Sem alergias conhecidas. Muito ativa.")
                    .tutor(joao)
                    .build());

            Pet bolinha = petRepo.save(Pet.builder()
                    .nome("Bolinha")
                    .especie("Cachorro")
                    .raca("Poodle")
                    .idade(5)
                    .peso(5.2)
                    .observacoesSaude("Histórico de otite recorrente. Necessita limpeza auricular semanal.")
                    .tutor(maria)
                    .build());

            Pet nemo = petRepo.save(Pet.builder()
                    .nome("Nemo")
                    .especie("Peixe")
                    .raca("Peixe-palhaço")
                    .idade(1)
                    .peso(0.1)
                    .observacoesSaude("Aquário de água salgada. pH 8.2.")
                    .tutor(ana)
                    .build());

            Pet mia = petRepo.save(Pet.builder()
                    .nome("Mia")
                    .especie("Gato")
                    .raca("Persa")
                    .idade(4)
                    .peso(4.5)
                    .observacoesSaude("Problemas respiratórios leves. Acompanhamento semestral.")
                    .tutor(pedro)
                    .build());

            // Consultas
            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().minusDays(60))
                    .horario(LocalTime.of(9, 0))
                    .tipoConsulta("Rotina")
                    .status(StatusConsulta.REALIZADA)
                    .observacoes("Exame clínico completo. Pet saudável, vacinação em dia.")
                    .pet(thor)
                    .build());

            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().plusDays(7))
                    .horario(LocalTime.of(14, 0))
                    .tipoConsulta("Vacinação")
                    .status(StatusConsulta.AGENDADA)
                    .observacoes("Vacina antirrábica anual e V10 de reforço.")
                    .pet(thor)
                    .build());

            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().plusDays(3))
                    .horario(LocalTime.of(10, 30))
                    .tipoConsulta("Dermatologia")
                    .status(StatusConsulta.AGENDADA)
                    .observacoes("Verificar possível dermatite. Queda de pelo excessiva.")
                    .pet(luna)
                    .build());

            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().minusDays(15))
                    .horario(LocalTime.of(11, 0))
                    .tipoConsulta("Otite")
                    .status(StatusConsulta.REALIZADA)
                    .observacoes("Otite bacteriana bilateral. Prescrito antibiótico auricular por 10 dias.")
                    .pet(bolinha)
                    .build());

            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().plusDays(14))
                    .horario(LocalTime.of(15, 0))
                    .tipoConsulta("Retorno")
                    .status(StatusConsulta.AGENDADA)
                    .observacoes("Retorno para avaliar resolução da otite.")
                    .pet(bolinha)
                    .build());

            consultaRepo.save(Consulta.builder()
                    .data(LocalDate.now().minusDays(5))
                    .horario(LocalTime.of(8, 0))
                    .tipoConsulta("Check-up")
                    .status(StatusConsulta.CANCELADA)
                    .observacoes("Cancelado pelo tutor. Reagendar.")
                    .pet(mia)
                    .build());

            // Lembretes
            lembreteRepo.save(Lembrete.builder()
                    .titulo("Vacina Antirrábica — Thor")
                    .descricao("Renovação anual da vacina antirrábica. Agendar junto com V10.")
                    .dataLembrete(LocalDate.now().plusDays(7))
                    .tipo("Vacinação")
                    .status(StatusLembrete.PENDENTE)
                    .pet(thor)
                    .build());

            lembreteRepo.save(Lembrete.builder()
                    .titulo("Vermifugação — Luna")
                    .descricao("Vermifugação trimestral. Usar produto indicado pelo veterinário.")
                    .dataLembrete(LocalDate.now().plusDays(15))
                    .tipo("Preventivo")
                    .status(StatusLembrete.PENDENTE)
                    .pet(luna)
                    .build());

            lembreteRepo.save(Lembrete.builder()
                    .titulo("Retorno Otite — Bolinha")
                    .descricao("Retorno para verificar resolução da otite bilateral.")
                    .dataLembrete(LocalDate.now().plusDays(14))
                    .tipo("Retorno")
                    .status(StatusLembrete.PENDENTE)
                    .pet(bolinha)
                    .build());

            lembreteRepo.save(Lembrete.builder()
                    .titulo("Banho e Tosa — Bolinha")
                    .descricao("Agendamento mensal de banho e tosa no petshop.")
                    .dataLembrete(LocalDate.now().minusDays(5))
                    .tipo("Higiene")
                    .status(StatusLembrete.CONCLUIDO)
                    .pet(bolinha)
                    .build());

            lembreteRepo.save(Lembrete.builder()
                    .titulo("Check-up Semestral — Mia")
                    .descricao("Avaliação respiratória semestral. Incluir raio-X de tórax.")
                    .dataLembrete(LocalDate.now().plusDays(30))
                    .tipo("Rotina")
                    .status(StatusLembrete.PENDENTE)
                    .pet(mia)
                    .build());

            // Interações IA
            interacaoRepo.save(InteracaoIA.builder()
                    .pergunta("Quais vacinas o Thor precisa tomar esse ano?")
                    .resposta("Para Thor (Cachorro), é essencial manter o calendário vacinal em dia. " +
                        "As vacinas principais variam por espécie e estilo de vida. Recomendo consultar " +
                        "seu veterinário para montar o protocolo ideal. Em cães, as vacinas V8/V10 e " +
                        "antirrábica são fundamentais; em gatos, V4/V5 e FeLV.")
                    .dataHora(LocalDateTime.now().minusDays(3))
                    .categoria("Vacinação")
                    .pet(thor)
                    .build());

            interacaoRepo.save(InteracaoIA.builder()
                    .pergunta("O que posso dar para a Luna comer além de ração?")
                    .resposta("A nutrição de Luna deve ser adequada à espécie Gato, faixa etária e " +
                        "condição corporal. Prefira rações de qualidade com proteína como primeiro " +
                        "ingrediente. Evite alimentos humanos como uva, cebola, chocolate e xilitol, " +
                        "que são tóxicos para pets.")
                    .dataHora(LocalDateTime.now().minusDays(2))
                    .categoria("Alimentação")
                    .pet(luna)
                    .build());

            interacaoRepo.save(InteracaoIA.builder()
                    .pergunta("O Bolinha está se coçando muito nas orelhas, o que pode ser?")
                    .resposta("Se Bolinha apresenta sintomas como vômito, diarreia, letargia ou perda de " +
                        "apetite, procure atendimento veterinário imediatamente. Não administre " +
                        "medicamentos humanos sem prescrição — muitos são tóxicos para pets. " +
                        "Hidratação adequada é essencial.")
                    .dataHora(LocalDateTime.now().minusDays(1))
                    .categoria("Sintomas")
                    .pet(bolinha)
                    .build());

            log.info("==> Dados iniciais carregados com sucesso! {} tutores, {} pets criados.",
                    tutorRepo.count(), petRepo.count());
        };
    }
}
