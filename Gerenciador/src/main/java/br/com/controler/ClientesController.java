package br.com.controler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.bean.ClienteBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "clientesController")
@SessionScoped
public class ClientesController {

	private ClienteBean bean;

	private List<SelectItem> listaComboClientes;
	
	private SelectItem clienteSelecionado;

	public ClientesController() {
		listaComboClientes = new ArrayList<SelectItem>();
		bean = new ClienteBean();
		loadCombo();
	}

	private void loadCombo() {
		listaComboClientes.add(new SelectItem(0, "Selecione..."));
		listaComboClientes.addAll(PersistenceUtils.retornoClientes());
	}

	private void update() {
		limparCampos();
		loadCombo();
	}

	private void limparCampos() {
		bean = new ClienteBean();
	}

	public void confirmar() {
		PersistenceUtils.salvar(bean);
		update();
	}

	public ClienteBean getBean() {
		return bean;
	}

	public void setBean(ClienteBean bean) {
		this.bean = bean;
	}

	public List<SelectItem> getListaComboClientes() {
		return listaComboClientes;
	}

	public void setListaComboClientes(List<SelectItem> listaComboClientes) {
		this.listaComboClientes = listaComboClientes;
	}

	public SelectItem getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(SelectItem clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}
