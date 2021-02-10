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
@Table(name = "pagamentos_cliente")
public class PagamentosClienteBean {

	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer codigo;

	@Column(name = "data")
	private Date data;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "saldo_do_dia")
	private BigDecimal saldoDia;

	@Column(name = "codigo_cliente")
	private Integer cliente;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldoDia() {
		return saldoDia;
	}

	public void setSaldoDia(BigDecimal saldoDia) {
		this.saldoDia = saldoDia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
