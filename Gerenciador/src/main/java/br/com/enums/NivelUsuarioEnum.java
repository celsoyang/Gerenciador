package br.com.enums;

public enum NivelUsuarioEnum {

	ADMINISTRADOR(1),

	FUNCIONARIO(2);

	private Integer nivel;

	NivelUsuarioEnum(Integer nivel) {
		this.nivel = nivel;
	}

	public static NivelUsuarioEnum getByCod(Integer cod) {
		for (NivelUsuarioEnum em : NivelUsuarioEnum.values()) {
			if (em.getNivel().equals(cod)) {
				return em;
			}
		}
		return null;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

}
