package br.com.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	private static FacesContext context = FacesContext.getCurrentInstance();

	private static HttpSession sessao = (HttpSession) context.getExternalContext().getSession(false);

	public static HttpSession getSession() {
		return sessao;
	}

	public static void setAtibute(String name, String integer) {
		sessao.setAttribute(name, integer);
	}

	public static Object getAtibute(String name) {
		return sessao.getAttribute(name);
	}

	public static void carregarUsuarioSessao(String usuario, Integer integer) {
		sessao.setAttribute(StringUtils.USUARIO, usuario);
		sessao.setAttribute(StringUtils.NIVEL, integer);
	}

	public static Object retornaUsuarioSessao() {
		return sessao.getAttribute(StringUtils.USUARIO);
	}

	public static Object retornaNivelUsuarioSessao() {
		return sessao.getAttribute(StringUtils.NIVEL);
	}

	public static void invalidateSession() {
		sessao.invalidate();
	}

	public static HttpSession getSessao() {
		return sessao;
	}

	public static void setSessao(HttpSession sessao) {
		SessionUtil.sessao = sessao;
	}

	public static FacesContext getContext() {
		return context;
	}

	public static void setContext(FacesContext context) {
		SessionUtil.context = context;
	}

}
