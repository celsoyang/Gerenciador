package br.com.controler;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "adicionarCompraController")
@SessionScoped
@ViewScoped
public class AdicionarCompraController {

	private String nomeCliente;

	private List<ClienteBean> listaClientes;

	private BigDecimal valorCompra;

	private String descricaoCompra;

	private CompraClienteBean bean;

	private ClienteBean clienteBean;

	private ClienteBean clienteBeanPesquisa;

	public AdicionarCompraController() {
		bean = new CompraClienteBean();
		valorCompra = new BigDecimal(0);
		clienteBean = new ClienteBean();
		clienteBeanPesquisa = new ClienteBean();
	}

	public void pesquisar() {
		listaClientes = new ArrayList<ClienteBean>();
		listaClientes = PersistenceUtils.pesquisarClientesPorNome(nomeCliente);

		if (listaClientes.size() > 0) {
			abrirDialog();
		} else {
			MessagesUtils.waringMessage("Nenhum Registro Encontrado");
		}
	}

	private void abrirDialog() {
		PrimeFaces.current().executeScript("PF('dialogPesquisaCliente').show();");
	}

	public void inserirCompra() {
		if (confirmarSelecao()) {
			bean.setDataCompra(new Timestamp(System.currentTimeMillis()));
			bean.setCodigoCliente(clienteBeanPesquisa.getCodigo());
			String msg = PersistenceUtils.salvar(bean);
			update();
			MessagesUtils.infoMessage(msg);
			bean = new CompraClienteBean();
			clienteBeanPesquisa = new ClienteBean();
		} else {
			MessagesUtils.errorMessage("Selecione um cliente");
		}
	}

	public void confimarCliente() {
		if (confirmarSelecao()) {
			nomeCliente = clienteBeanPesquisa.getNome();
		} else {
			MessagesUtils.errorMessage("Selecione um registro");
		}
	}

	private Boolean confirmarSelecao() {
		Boolean retorno = Boolean.TRUE;
		if (clienteBeanPesquisa == null) {
			retorno = Boolean.FALSE;
		}
		return retorno;
	}

	public void adicionarCliente() {
		String msg = "";
		if (confirmarAdicao()) {
			msg = PersistenceUtils.salvar(clienteBean);
		}
		update();
		if (!msg.endsWith(""))
			MessagesUtils.infoMessage(msg);
		clienteBean = new ClienteBean();
	}

	private boolean confirmarAdicao() {
		Boolean valido = Boolean.TRUE;

		if (clienteBean.getNome().equals("")) {
			valido = Boolean.FALSE;
			MessagesUtils.errorMessage("Nome não pode ser vazio");
		}

		return valido;
	}

	public void limpar() {
		bean = new CompraClienteBean();
		bean.setDataCompra(new Timestamp(new Date().getTime()));
		clienteBean = new ClienteBean();
	}

	private void update() {
		limpar();
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

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public ClienteBean getClienteBeanPesquisa() {
		return clienteBeanPesquisa;
	}

	public void setClienteBeanPesquisa(ClienteBean clienteBeanPesquisa) {
		this.clienteBeanPesquisa = clienteBeanPesquisa;
	}

}
