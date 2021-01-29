package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.bean.ClienteBean;
import br.com.bean.CompraBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "adicionarCompraController")
@SessionScoped
public class AdicionarCompraController {

	private List<SelectItem> listaCombo = new ArrayList<SelectItem>();

	private List<ClienteBean> listaClientes = new ArrayList<ClienteBean>();

	private List<CompraBean> listaCompras = new ArrayList<CompraBean>();

	private Date dataCompra = new Date();

	private BigDecimal valorCompra = new BigDecimal(0);

	private String descricaoCompra;

	public AdicionarCompraController() {
		carregarClientes();
	}

	private void carregarClientes() {
		listaClientes = PersistenceUtils.pesquisarClientes();

		for (ClienteBean clienteBean : listaClientes) {
			listaCombo.add(new SelectItem(clienteBean.getCodigo(), clienteBean.getNome()));
		}
	}

	public void adicionarCompra() {

	}

	public void limpar() {

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

	public List<CompraBean> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<CompraBean> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
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

}
