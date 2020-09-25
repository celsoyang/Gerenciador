package br.com.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Cheque")
@Table(name = "cheque")
public class ChequeBean {

	@Id
	@Column(name = "num_cheque")
	private Integer numCheque;

	@Column(name = "beneficiario")
	private String beneficiario;

	@Column(name = "data_emissao")
	private Date dataEmissao;

	@Column(name = "data_pagamento")
	private Date dataPagamento;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "observacao")
	private String observacao;

	public Integer getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(Integer numCheque) {
		this.numCheque = numCheque;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
