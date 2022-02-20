package com.test.gestortarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.gestortarefas.dao.TarefaDAO;

@SpringBootApplication
@RestController
public class GestorTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorTarefasApplication.class, args);
	}

	@GetMapping("/")
	public String toBrowser() {
		String output = "Olá Mundo<br><br>";
		
		TarefaDAO banco = TarefaDAO.getInstance();
		
		banco.cadastrarTarefa("Tarefa", "Um teste", "Antonio", "Media", "2022-08-15");
		banco.cadastrarTarefa("Tarefa", "Um testa", "Antonio", "Baixa", "2022-08-15");
		banco.cadastrarTarefa("Tarefa 3", "Um teste", "Antonio", "Baixa", "2022-08-15");
		banco.cadastrarTarefa("Tarefa 4", "U testa", "Carlos", "Alta", "2022-08-15");
		
		output += banco.buscarTarefas(0, null, null, null, null, false);
		
		output += banco.editarTarefa(4, null, null, "Joao", "Alta", null);
		
		output += banco.buscarTarefas(0, null, null, "Antonio", null, false);
		
		output += banco.buscarTarefas(0, null, "Um teste", "Antonio", null, false);
		
		output += banco.excluirTarefa(2);
		output += banco.excluirTarefa(10);
		
		banco.cadastrarTarefa("Tarefa 5", "U testa", "Ana", "Baixa", "20XX-08-15");
		
		output += banco.buscarTarefas(0, null, null, null, null, true);
		
		output += banco.concluirTarefa(1);
		
		output += banco.buscarTarefas(0, null, null, null, null, true);
		
		output += banco.buscarTarefas(0, null, null, null, null, false);
		
		return output;
	}
}
