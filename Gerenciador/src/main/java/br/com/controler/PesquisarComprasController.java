package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.bean.PagamentosClienteBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "pesquisarComprasController")
@SessionScoped
@ViewScoped
public class PesquisarComprasController {

	private List<CompraClienteBean> listaComprasCliente;

	private BigDecimal totalComprado;

	private BigDecimal totalPago;

	private BigDecimal saldoDevedor;

	private CompraClienteBean compraSelecionada;

	private List<PagamentosClienteBean> listaPagamentosCliente;

	private PagamentosClienteBean pagamentoSelecionado;

	private PagamentosClienteBean pagamentoBean;

	private CompraClienteBean compraBean;

	private BigDecimal valorPagamento;

	private String descricaoPagamento;

	private String nomeClientePesquisa;

	private ClienteBean clienteSelecionadoPesquisa;

	private List<ClienteBean> listaClietes;

	public PesquisarComprasController() {
		listaComprasCliente = new ArrayList<CompraClienteBean>();
//		totalComprado = new BigDecimal(0);
//		totalPago = new BigDecimal(0);
//		saldoDevedor = new BigDecimal(0);
//		valorPagamento = new BigDecimal(0);
		clienteSelecionadoPesquisa = new ClienteBean();
	}

	public void carregarListaClientes() {
		listaClietes = new ArrayList<ClienteBean>();
		listaClietes = PersistenceUtils.pesquisarClientesPorNome(nomeClientePesquisa);
		if (listaClietes.size() > 0) {
			abrirDialog();
		} else {
			MessagesUtils.waringMessage("Nenhum Registro Encontrado");
		}
	}

	public void abrirDialog() {
		PrimeFaces.current().executeScript("PF('dialogClientePesquisado').show();");
	}

	public void pesquisarComprasPagamentosCliente() {
		if (confirmarSelecao()) {
			listaComprasCliente = PersistenceUtils.pesquisarComprasPorCliente(clienteSelecionadoPesquisa.getCodigo());
			listaPagamentosCliente = PersistenceUtils
					.pesquisarPagamentosPorCliente(clienteSelecionadoPesquisa.getCodigo());
			caucularTotais(listaComprasCliente, listaPagamentosCliente);
			setNomeClientePesquisa(clienteSelecionadoPesquisa.getNome());
		} else {
			MessagesUtils.errorMessage("Selecione um registro");
		}
	}

	private Boolean confirmarSelecao() {
		Boolean retorno = Boolean.TRUE;

		if (clienteSelecionadoPesquisa == null) {
			retorno = Boolean.FALSE;
		}

		return retorno;
	}

	private void caucularTotais(List<CompraClienteBean> listaCompra, List<PagamentosClienteBean> listaPagamentos) {
		totalComprado = new BigDecimal(0);
		totalPago = new BigDecimal(0);
		saldoDevedor = new BigDecimal(0);
		for (CompraClienteBean compra : listaCompra) {
			totalComprado = totalComprado.add(compra.getValor());
		}
		for (PagamentosClienteBean pagamento : listaPagamentos) {
			totalPago = totalPago.add(pagamento.getValor());
		}
		saldoDevedor = totalComprado.subtract(totalPago);

	}

	public void confirmarPagamento() {
		pagamentoBean = new PagamentosClienteBean();

		pagamentoBean.setCliente(clienteSelecionadoPesquisa.getCodigo());
		pagamentoBean.setData(new Date());
		pagamentoBean.setValor(valorPagamento);
		pagamentoBean.setDescricao(descricaoPagamento);
		pagamentoBean.setSaldoDia(getSaldoDevedor().subtract(valorPagamento));

		PersistenceUtils.salvar(pagamentoBean);
		limparDialog();
		pesquisarComprasPagamentosCliente();
	}

	private void limparDialog() {
		valorPagamento = null;
		descricaoPagamento = "";
	}

	public void excluirPagamento() {
		if (confirmarSelecaoPagamento()) {
			try {
				PagamentosClienteBean pagamento = PersistenceUtils.getEntitiManager().find(PagamentosClienteBean.class,
						pagamentoSelecionado.getCodigo());

				String msg = PersistenceUtils.delete(pagamento);
				MessagesUtils.infoMessage(msg);
				pesquisarComprasPagamentosCliente();
			} catch (Exception e) {
				MessagesUtils.errorMessage("Problema ao Excluir");
			}
		}
	}

	private Boolean confirmarSelecaoPagamento() {
		Boolean retorno = Boolean.TRUE;
		if (pagamentoSelecionado == null) {
			retorno = Boolean.FALSE;
			MessagesUtils.errorMessage("Selecione Um Pagamento");
		}
		return retorno;
	}

	public void excluirCompra() {
		if (confirmarSelecaoCompra()) {
			try {
				CompraClienteBean compra = PersistenceUtils.getEntitiManager().find(CompraClienteBean.class,
						compraSelecionada.getCodigo());

				String msg = PersistenceUtils.delete(compra);

				MessagesUtils.infoMessage(msg);
				pesquisarComprasPagamentosCliente();
			} catch (Exception e) {
				MessagesUtils.errorMessage("Problema ao Excluir");
			}
		}
	}

	private Boolean confirmarSelecaoCompra() {
		Boolean retorno = Boolean.TRUE;
		if (compraSelecionada == null) {
			retorno = Boolean.FALSE;
			MessagesUtils.errorMessage("Selecione Uma Compra");
		}
		return retorno;
	}

	public BigDecimal getTotalComprado() {
		return totalComprado;
	}

	public void setTotalComprado(BigDecimal totalComprado) {
		this.totalComprado = totalComprado;
	}

	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}

	public BigDecimal getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(BigDecimal saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}

	public List<CompraClienteBean> getListaComprasCliente() {
		return listaComprasCliente;
	}

	public void setListaComprasCliente(List<CompraClienteBean> listaComprasCliente) {
		this.listaComprasCliente = listaComprasCliente;
	}

	public List<PagamentosClienteBean> getListaPagamentosCliente() {
		return listaPagamentosCliente;
	}

	public void setListaPagamentosCliente(List<PagamentosClienteBean> listaPagamentosCliente) {
		this.listaPagamentosCliente = listaPagamentosCliente;
	}

	public PagamentosClienteBean getPagamentoSelecionado() {
		return pagamentoSelecionado;
	}

	public void setPagamentoSelecionado(PagamentosClienteBean pagamentoSelecionado) {
		this.pagamentoSelecionado = pagamentoSelecionado;
	}

	public CompraClienteBean getCompraSelecionada() {
		return compraSelecionada;
	}

	public void setCompraSelecionada(CompraClienteBean compraSelecionada) {
		this.compraSelecionada = compraSelecionada;
	}

	public PagamentosClienteBean getPagamentoBean() {
		return pagamentoBean;
	}

	public void setPagamentoBean(PagamentosClienteBean pagamentoBean) {
		this.pagamentoBean = pagamentoBean;
	}

	public CompraClienteBean getCompraBean() {
		return compraBean;
	}

	public void setCompraBean(CompraClienteBean compraBean) {
		this.compraBean = compraBean;
	}

	public BigDecimal getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public String getDescricaoPagamento() {
		return descricaoPagamento;
	}

	public void setDescricaoPagamento(String descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}

	public String getNomeClientePesquisa() {
		return nomeClientePesquisa;
	}

	public void setNomeClientePesquisa(String nomeClientePesquisa) {
		this.nomeClientePesquisa = nomeClientePesquisa;
	}

	public List<ClienteBean> getListaClietes() {
		return listaClietes;
	}

	public void setListaClietes(List<ClienteBean> listaClietes) {
		this.listaClietes = listaClietes;
	}

	public ClienteBean getClienteSelecionadoPesquisa() {
		return clienteSelecionadoPesquisa;
	}

	public void setClienteSelecionadoPesquisa(ClienteBean clienteSelecionadoPesquisa) {
		this.clienteSelecionadoPesquisa = clienteSelecionadoPesquisa;
	}

}
