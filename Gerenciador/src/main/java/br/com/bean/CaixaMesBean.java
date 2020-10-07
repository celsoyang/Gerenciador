package br.com.bean;

import java.math.BigDecimal;

public class CaixaMesBean {

	private String nomeMes;

	private Integer numeroMes;
	
	private BigDecimal totalMes;

	public String getNomeMes() {
		return nomeMes;
	}

	public void setNomeMes(String nomeMes) {
		this.nomeMes = nomeMes;
	}

	public Integer getNumeroMes() {
		return numeroMes;
	}

	public void setNumeroMes(Integer numeroMes) {
		this.numeroMes = numeroMes;
	}

	public BigDecimal getTotalMes() {
		return totalMes;
	}

	public void setTotalMes(BigDecimal totalMes) {
		this.totalMes = totalMes;
	}
}
