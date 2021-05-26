package br.com.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comprador_conjunto")
public class CompradorConjuntoBean {

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer codigo;

	@Column(name = "nome")
	private String nome;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "data_pagamento")
	private Date dataPagamento;
	
	@Column(name = "num_cheque")
	private Integer numCheque;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String toString() {
		return nome;
	}

	public Integer getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(Integer numCheque) {
		this.numCheque = numCheque;
	}
}
