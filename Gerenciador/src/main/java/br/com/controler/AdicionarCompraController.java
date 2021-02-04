package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "adicionarCompraController")
@SessionScoped
public class AdicionarCompraController {

	private List<SelectItem> listaCombo = new ArrayList<SelectItem>();

	private List<ClienteBean> listaClientes = new ArrayList<ClienteBean>();

	private List<CompraClienteBean> listaCompras = new ArrayList<CompraClienteBean>();

	private Integer clienteSelecionado = 0;

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

	public void buscarComprasCliente(ValueChangeEvent e) {
		listaCompras.clear();
		listaCompras = PersistenceUtils.pesquisarComprasPorCliente(clienteSelecionado);
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

	public List<CompraClienteBean> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<CompraClienteBean> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public Integer getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Integer clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}
