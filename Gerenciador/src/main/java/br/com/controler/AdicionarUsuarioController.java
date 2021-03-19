package br.com.controler;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import br.com.bean.UsuarioBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;
import br.com.utils.StringUtils;

@ManagedBean(name = "adicionarUsuarioController")
@SessionScoped
@ViewScoped
public class AdicionarUsuarioController {

	private String nomeUsuario;

	private String username;

	private String senha;

	private String usernameNovaSenha;

	private String novaSenha;

	private String novaSenha2;

	private UsuarioBean usuario;

	private Integer nivelUsuario;

	public AdicionarUsuarioController() {
		nomeUsuario = StringUtils.STRING_VAZIA;
		username = StringUtils.STRING_VAZIA;
		senha = StringUtils.STRING_VAZIA;
		usernameNovaSenha = StringUtils.STRING_VAZIA;
		novaSenha = StringUtils.STRING_VAZIA;
		novaSenha2 = StringUtils.STRING_VAZIA;
		usuario = new UsuarioBean();
	}

	public void criarUsuario() {
		usuario.setNome(nomeUsuario);
		usuario.setLogin(username);
		usuario.setSenha(gerarSenha(senha));
		usuario.setNivel(nivelUsuario);

		String msg = PersistenceUtils.salvar(usuario);
		limpar();

		MessagesUtils.infoMessage(msg);
	}

	private void limpar() {
		nomeUsuario = "";
		username = "";
		senha = "";
		usuario = new UsuarioBean();
	}

	private String gerarSenha(String password) {
		MessageDigest MD;
		String senhaHexa = "";
		try {
			MD = MessageDigest.getInstance("MD5");
			MD.update(password.getBytes(), 0, password.length());
			byte[] digest = MD.digest();
			senhaHexa = new BigInteger(1, digest).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return senhaHexa;
	}

	public void alterarSenha() {
		if (novaSenha.equals(novaSenha2)) {

			MessageDigest MD;
			String senhaHexa = "";
			try {
				MD = MessageDigest.getInstance("MD5");
				MD.update(novaSenha2.getBytes(), 0, novaSenha2.length());
				byte[] digest = MD.digest();
				senhaHexa = new BigInteger(1, digest).toString(16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			UsuarioBean usuarioNovaSenha = PersistenceUtils.buscarUsuario(usernameNovaSenha);

			PersistenceUtils.openTransaction();
			usuarioNovaSenha = PersistenceUtils.getEntitiManager().find(UsuarioBean.class,
					usuarioNovaSenha.getCodigo());

			usuarioNovaSenha.setSenha(senhaHexa);

			String msg = PersistenceUtils.salvar(usuarioNovaSenha);

			PrimeFaces.current().executeScript("PF('dialogAlterarSenha').hide();");

			MessagesUtils.infoMessage(msg);
		}
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenha2() {
		return novaSenha2;
	}

	public void setNovaSenha2(String novaSenha2) {
		this.novaSenha2 = novaSenha2;
	}

	public String getUsernameNovaSenha() {
		return usernameNovaSenha;
	}

	public void setUsernameNovaSenha(String usernameNovaSenha) {
		this.usernameNovaSenha = usernameNovaSenha;
	}

	public Integer getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(Integer nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

}
