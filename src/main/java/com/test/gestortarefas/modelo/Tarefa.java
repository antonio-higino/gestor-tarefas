package com.test.gestortarefas.modelo;

public class Tarefa {

	private int id;
	private String titulo;
	private String descricao;
	private String responsavel;
	private String prioridade;
	private String deadline;
	private boolean concluida = false;
	
	public Tarefa() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getResponsavel() {
		return responsavel;
	}
	
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	
	public String getPrioridade() {
		return prioridade;
	}
	
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	
	public String getDeadline() {
		return deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public boolean isConcluida() {
		return concluida;
	}
	
	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}
}
