package com.test.gestortarefas.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.test.gestortarefas.modelo.Tarefa;

public class TarefaDAO {
	
	private ArrayList<Tarefa> tarefas;
	private int contadorParaId = 0;
	
	private static TarefaDAO banco;
	
	public TarefaDAO() {
		tarefas = new ArrayList<Tarefa>();
	}
	
	public static TarefaDAO getInstance() {
		if(banco == null) {
			banco = new TarefaDAO();
		}
		return banco;
	}

	public ArrayList<Tarefa> getTarefas() {
		return tarefas;
	}
	
	public Tarefa getTarefaEspecifica(int id) {
		for(Tarefa tarefa : tarefas) {
			if(tarefa.getId() == id) {
				return tarefa;
			}
		}
		return null;
	}

	public int getContadorParaId() {
		contadorParaId++;
		return contadorParaId;
	}
	
	public void adicionarTarefa(Tarefa tarefa) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		banco.getTarefas().add(tarefa);
	}
	
	public boolean tarefaExiste(int id) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		for(Tarefa tarefa : banco.getTarefas()) {
			if(tarefa.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void cadastrarTarefa(String titulo, String descricao, String responsavel, String prioridade, String deadline) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		int id = banco.getContadorParaId();
		
		Tarefa tarefa = new Tarefa();
		
		tarefa.setId(id);
		tarefa.setTitulo(titulo);
		tarefa.setDescricao(descricao);
		tarefa.setResponsavel(responsavel);
		tarefa.setPrioridade(prioridade);
		tarefa.setDeadline(deadline);
		
		banco.adicionarTarefa(tarefa);
	}
	
	public String editarTarefa(int id, String titulo, String descricao, String responsavel, String prioridade, String deadline) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		String output = "";
		
		if(banco.tarefaExiste(id)) {
			if(titulo != null) {
				banco.getTarefaEspecifica(id).setTitulo(titulo);
			}
			if(descricao != null) {
				banco.getTarefaEspecifica(id).setDescricao(descricao);
			}
			if(responsavel != null) {
				banco.getTarefaEspecifica(id).setResponsavel(responsavel);
			}
			if(prioridade != banco.getTarefaEspecifica(id).getPrioridade()) {
				banco.getTarefaEspecifica(id).setPrioridade(prioridade);
			}
			if(deadline != null) {
				banco.getTarefaEspecifica(id).setDeadline(deadline);
			}
		} else {
			output += "<br>Tarefa #" + id + " não existe<br>";
		}
		return output;
	}
	
	public String excluirTarefa(int id) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		String output = "";
		
		if(banco.tarefaExiste(id)) {
			banco.getTarefas().remove(banco.getTarefaEspecifica(id));
		} else {
			output += "<br>Tarefa #" + id + " não existe<br>";
		}
		return output;
	}
	
	public String concluirTarefa(int id) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		String output = "";
		
		if(banco.tarefaExiste(id)) {
			banco.getTarefaEspecifica(id).setConcluida(true);
		} else {
			output += "<br>Tarefa #" + id + " não existe<br>";
		}
		return output;
	}
	
	public boolean checarRepetido(int id, ArrayList<Integer> resultado) {
		for(int num : resultado) {
			if(num == id) {
				return true;
			}
		}
		return false;
	}
	
	public String buscarTarefas(int id, String titulo, String descricao, String responsavel, String prioridade, boolean concluida) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		ArrayList<Integer> resultadoFinal = new ArrayList<Integer>();
		
		//Busca sem filtros
		if(id == 0 && titulo == null && descricao == null && responsavel == null && prioridade == null) {
			for(Tarefa tarefa : banco.getTarefas()) {
				if(concluida == tarefa.isConcluida()) {
					resultadoFinal.add(tarefa.getId());
				}
			}
		} else {
			//Busca com filtros
			
			Set<Integer> resultadoNumero = new HashSet<Integer>();
			Set<Integer> resultadoTitulo = new HashSet<Integer>();
			Set<Integer> resultadoDescricao = new HashSet<Integer>();
			Set<Integer> resultadoResponsavel = new HashSet<Integer>();
			
			for(Tarefa tarefa : banco.getTarefas()) {
				if(concluida == tarefa.isConcluida()) {
					if(titulo == tarefa.getTitulo()) {
						resultadoTitulo.add(tarefa.getId());
					}
					
					if(descricao == tarefa.getDescricao()) {
						resultadoDescricao.add(tarefa.getId());
					}
					
					if(responsavel == tarefa.getResponsavel()) {
						resultadoResponsavel.add(tarefa.getId());
					}
					
					if(id == tarefa.getId()) {
						resultadoNumero.add(tarefa.getId());
					}
				}
			}
			
			Set<Integer> resultadoDesordenado = new HashSet<Integer>();
			
			for(Tarefa tarefa : banco.getTarefas()) {
				resultadoDesordenado.add(tarefa.getId());
			}
			
			//Checa por intersecoes para filtrar usando mais de um filtro ao mesmo tempo
			if(resultadoNumero.size() > 0) {
				resultadoDesordenado.retainAll(resultadoNumero);
			}
			if(resultadoTitulo.size() > 0) {
				resultadoDesordenado.retainAll(resultadoTitulo);
			}
			if(resultadoDescricao.size() > 0) {
				resultadoDesordenado.retainAll(resultadoDescricao);
			}
			if(resultadoResponsavel.size() > 0) {
				resultadoDesordenado.retainAll(resultadoResponsavel);
			}
			
			//Ordena o resultado da intersecao
			TreeSet resultadoOrdenado = new TreeSet(resultadoDesordenado);
			
			resultadoFinal.addAll(resultadoOrdenado);
			
		}
		return banco.listarTarefas(resultadoFinal, concluida);
	}
	
	public String listarTarefas(ArrayList<Integer> resultado, boolean concluida) {
		TarefaDAO banco = TarefaDAO.getInstance();
		
		String output = "";
		
		output += "<br>--> Lista de Tarefas";
		
		if(concluida) {
			output += " - Concluidas:<br>";
		} else {
			output += " - Em Andamento:<br>";
		}
		
		for(int id : resultado) {
			output += "<br>";
			output += "Tarefa #" + id + "<br>";
			output += "Titulo: " + banco.getTarefaEspecifica(id).getTitulo() + "<br>";
			output += "Descricao: " + banco.getTarefaEspecifica(id).getDescricao() + "<br>";
			output += "Responsavel: " + banco.getTarefaEspecifica(id).getResponsavel() + "<br>";
			output += "Prioridade: " + banco.getTarefaEspecifica(id).getPrioridade() + "<br>";
			output += "Deadline: " + banco.getTarefaEspecifica(id).getDeadline()+ "<br>";
		}
		
		if(resultado.size() == 0) {
			output += "<br>Nenhum resultado encontrado<br>";
		} else {
			output += "<br>######################################<br>";
		}
		
		return output;
	}
}
