package br.com.controler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "adicionarCompraController")
@SessionScoped
public class AdicionarCompraController {

	private List<SelectItem> listaCombo;

	private List<ClienteBean> listaClientes;

	private List<CompraClienteBean> listaCompras;

	private BigDecimal valorCompra = new BigDecimal(0);

	private String descricaoCompra;

	private CompraClienteBean bean;

	private ClienteBean clienteBean;

	public AdicionarCompraController() {
		bean = new CompraClienteBean();
		clienteBean = new ClienteBean();
		listaCompras = new ArrayList<CompraClienteBean>();
		carregarClientes();
	}

	private void carregarClientes() {
		listaClientes = new ArrayList<ClienteBean>();
		listaCombo = new ArrayList<SelectItem>();
		listaClientes = PersistenceUtils.pesquisarClientes();

		for (ClienteBean clienteBean : listaClientes) {
			listaCombo.add(new SelectItem(clienteBean.getCodigo(), clienteBean.getNome()));
		}
	}

	public void inserirCompra() {
		bean = new CompraClienteBean();
		bean.setDataCompra(new Timestamp(System.currentTimeMillis()));
		String msg = PersistenceUtils.salvar(bean);
		update();
		MessagesUtils.infoMessage(msg);
	}

	public void adicionarCliente() {
		String msg = PersistenceUtils.salvar(clienteBean);
		update();
		MessagesUtils.infoMessage(msg);
		clienteBean = new ClienteBean();
	}

	public void limpar() {
		bean = new CompraClienteBean();
		bean.setDataCompra(new Timestamp(new Date().getTime()));
		clienteBean = new ClienteBean();
	}

	private void update() {
		listaCompras = PersistenceUtils.pesquisarComprasPorCliente(bean.getCodigoCliente());
		carregarClientes();
		limpar();
	}

	public void buscarComprasCliente(ValueChangeEvent e) {
		listaCompras.clear();
		listaCompras = PersistenceUtils.pesquisarComprasPorCliente(e.getNewValue());
	}

	public List<SelectItem> getListaCombo() {
		return listaCombo;
	}

	public void setListaCombo(List<SelectItem> listaCombo) {
		this.listaCombo = listaCombo;
	}

	public List<ClienteBean> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClienteBean> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public String getDescricaoCompra() {
		return descricaoCompra;
	}

	public void setDescricaoCompra(String descricaoCompra) {
		this.descricaoCompra = descricaoCompra;
	}

	public List<CompraClienteBean> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<CompraClienteBean> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public CompraClienteBean getBean() {
		return bean;
	}

	public void setBean(CompraClienteBean bean) {
		this.bean = bean;
	}

	public ClienteBean getClienteBean() {
		return clienteBean;
	}

	public void setClienteBean(ClienteBean clienteBean) {
		this.clienteBean = clienteBean;
	}

}
