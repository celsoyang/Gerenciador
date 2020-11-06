package br.com.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "compras_internas")
public class CompraBean {

	@Id
	@GeneratedValue
	@SequenceGenerator(name = "sequence_compras", initialValue = 416)
	@Column(name = "codigo")
	private Integer codigo;

	@Column(name = "vendedor")
	private String vendedor;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "data_compra")
	private Date dataCompra;

	@Column(name = "data_pagamento")
	private Date dataPagamento;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

}
