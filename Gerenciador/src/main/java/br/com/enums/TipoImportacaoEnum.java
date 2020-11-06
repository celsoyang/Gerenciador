package br.com.enums;

public enum TipoImportacaoEnum {

	CAIXA(1, "Caixa Diário"),

	CHEQUE(2, "Cheques"),

	COMPRA(3, "Compras"),

	GASTO(4, "Gastos");

	private Integer codigo;

	private String descicao;

	private TipoImportacaoEnum(Integer cod, String desc) {
		this.codigo = cod;
		this.descicao = desc;
	}

	public TipoImportacaoEnum getByCod(Integer cod) {
		for (TipoImportacaoEnum tie : TipoImportacaoEnum.values()) {
			if (tie.getCodigo().equals(cod)) {
				return tie;
			}
		}
		return null;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescicao() {
		return descicao;
	}

	public void setDescicao(String descicao) {
		this.descicao = descicao;
	}

}
