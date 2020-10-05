package br.com.enums;

public enum MesesEnum {

	JANEIRO(1, "Janeiro"),

	FEVEREIRO(2, "Fevereiro"),

	MARCO(3, "Março"),

	ABRIL(4, "Abril"),

	MAIO(5, "Maio"),

	JUNHO(6, "Junho"),

	JULHO(7, "Julho"),

	AGOSTO(8, "Agosto"),

	SETEMBRO(9, "Setembro"),

	OUTUBRO(10, "Outubro"),

	NOVEMBRO(11, "Novembro"),

	DEZEMBRO(12, "Dezembro");

	private Integer numeroMes;

	private String nomeMes;

	MesesEnum(Integer num, String desc) {
		this.numeroMes = num;
		this.nomeMes = desc;
	}

	public static MesesEnum getBayNumero(Integer numeroMes) {
		for (MesesEnum me : MesesEnum.values()) {
			if (me.getNumeroMes().equals(numeroMes)) {
				return me;
			}
		}
		return null;
	}

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

}
