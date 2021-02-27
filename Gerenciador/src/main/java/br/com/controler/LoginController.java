package br.com.controler;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bean.UsuarioBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginController {

	private String login;

	private String senha;

	private UsuarioBean usuario;

	public LoginController() {
		usuario = new UsuarioBean();
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

				MD.update(usuario.getSenha().getBytes(), 0, usuario.getSenha().length());
				byte[] digestUsuario = MD.digest();
				String usuarioHexa = new BigInteger(1, digestUsuario).toString(16);

				if (!senhaHexa.equals(usuarioHexa)) {
					usuario = new UsuarioBean();
				}
			}

			System.out.println(senhaHexa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deslogar() {
		usuario = new UsuarioBean();
		login = "";
		senha = "";
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

}
