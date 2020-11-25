package br.com.controler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.bean.CompradorConjuntoBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "compradorConjuntoController")
@SessionScoped
public class CompradorConjuntoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private CompradorConjuntoBean bean;

	private List<CompradorConjuntoBean> listaCompradores;

	private CompradorConjuntoBean compradorSelecionado;

	public CompradorConjuntoController() {
		configTable();
		loadTable();
	}

	private void configTable() {

	}

	private void loadTable() {
		listaCompradores = new ArrayList<CompradorConjuntoBean>();
		listaCompradores.addAll(PersistenceUtils.retornaCompradoresConjuntos());
	}

	public void inserir() {
		update();
	}

	public void excluir() {
		update();
	}

	public void editar() {
		update();
	}

	private void update() {
		limparCampos();
	}

	private void limparCampos() {

	}

	public CompradorConjuntoBean getBean() {
		return bean;
	}

	public void setBean(CompradorConjuntoBean bean) {
		this.bean = bean;
	}

	public List<CompradorConjuntoBean> getListaCompradores() {
		return listaCompradores;
	}

	public void setListaCompradores(List<CompradorConjuntoBean> listaCompradores) {
		this.listaCompradores = listaCompradores;
	}

	public CompradorConjuntoBean getCompradorSelecionado() {
		return compradorSelecionado;
	}

	public void setCompradorSelecionado(CompradorConjuntoBean compradorSelecionado) {
		this.compradorSelecionado = compradorSelecionado;
	}

}
