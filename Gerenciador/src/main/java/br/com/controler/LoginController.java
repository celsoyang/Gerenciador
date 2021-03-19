package br.com.controler;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.bean.UsuarioBean;
import br.com.utils.PersistenceUtils;
import br.com.utils.SessionUtil;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginController {

	private String login;

	private String senha;

	private UsuarioBean usuario;

	private HttpSession session;

	public LoginController() {
		FacesContext context = FacesContext.getCurrentInstance();
		usuario = new UsuarioBean();
		session = (HttpSession) context.getExternalContext().getSession(true);
	}

	public void logar() {

		MessageDigest MD;
		try {
			MD = MessageDigest.getInstance("MD5");
			MD.update(senha.getBytes(), 0, senha.length());

			byte[] digest = MD.digest();
			String senhaHexa = new BigInteger(1, digest).toString(16);

			usuario = PersistenceUtils.buscarUsuario(login);

			if (usuario.getCodigo() != null) {

				if (!senhaHexa.equals(usuario.getSenha())) {
					usuario = new UsuarioBean();
				}
			}

			SessionUtil.carregarUsuarioSessao(usuario.getLogin(), usuario.getNivel());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deslogar() {
		usuario = new UsuarioBean();
		login = "";
		senha = "";

		SessionUtil.invalidateSession();
		SessionUtil.carregarUsuarioSessao("", 0);
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

}
