package br.com.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "compra_cliente")
public class CompraClienteBean {

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer codigo;

	@Column(name = "dia")
	private Timestamp dataCompra;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "cliente")
	private Integer codigoCliente;

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Integer codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Timestamp getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Timestamp dataCompra) {
		this.dataCompra = dataCompra;
	}
}
