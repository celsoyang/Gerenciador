package br.com.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "compra_diaria")
public class CompraBean {

	@Id
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "dia")
	private Date dia;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

}
