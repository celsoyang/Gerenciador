package br.com.bean;

import java.util.List;

public class CaixaMesBean {

	private String nomeMes;

	private Integer numeroMes;

	private List<CaixaDiarioBean> diasMes;

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

	public List<CaixaDiarioBean> getDiasMes() {
		return diasMes;
	}

	public void setDiasMes(List<CaixaDiarioBean> diasMes) {
		this.diasMes = diasMes;
	}
}
