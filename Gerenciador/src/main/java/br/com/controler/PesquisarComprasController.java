package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.bean.PagamentosClienteBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "pesquisarComprasController")
@SessionScoped
public class PesquisarComprasController {

	private List<SelectItem> comboClientes;

	private List<CompraClienteBean> listaComprasCliente;

	private BigDecimal totalComprado;

	private BigDecimal totalPago;

	private BigDecimal saldoDevedor;

	private Integer clienteSelecionado;

	private CompraClienteBean compraSelecionada;

	private List<PagamentosClienteBean> listaPagamentosCliente;

	private PagamentosClienteBean pagamentoSelecionado;

	private PagamentosClienteBean pagamentoBean;

	private CompraClienteBean compraBean;

	private BigDecimal valorPagamento;

	private String descricaoPagamento;

	public PesquisarComprasController() {
		comboClientes = new ArrayList<SelectItem>();
		listaComprasCliente = new ArrayList<CompraClienteBean>();
		clienteSelecionado = 0;
		totalComprado = new BigDecimal(0);
		totalPago = new BigDecimal(0);
		saldoDevedor = new BigDecimal(0);
		valorPagamento = new BigDecimal(0);
		carregarComboClientes();
	}

	private void carregarComboClientes() {
		List<ClienteBean> listaClietes = PersistenceUtils.pesquisarClientes();

		for (ClienteBean cli : listaClietes) {
			comboClientes.add(new SelectItem(cli.getCodigo(), cli.getNome()));
		}

	}

	public void pesquisarComprasCliente() {
		listaComprasCliente = PersistenceUtils.pesquisarComprasPorCliente(clienteSelecionado);
		listaPagamentosCliente = PersistenceUtils.pesquisarPagamentosPorCliente(clienteSelecionado);
		caucularTotais(listaComprasCliente, listaPagamentosCliente);
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

		pagamentoBean.setCliente(clienteSelecionado);
		pagamentoBean.setData(new Date());
		pagamentoBean.setValor(valorPagamento);
		pagamentoBean.setDescricao(descricaoPagamento);
		pagamentoBean.setSaldoDia(getSaldoDevedor().subtract(valorPagamento));

		PersistenceUtils.salvar(pagamentoBean);
		pesquisarComprasCliente();
		limparDialog();
	}

	private void limparDialog() {
		valorPagamento = new BigDecimal(0);
		descricaoPagamento = "";
	}

	public void excluirPagamento() {

	}

	public void alterarPagamento() {

	}

	public void excluirCompra() {

	}

	public void alterarCompra() {

	}

	public List<SelectItem> getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(List<SelectItem> comboClientes) {
		this.comboClientes = comboClientes;
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

	public Integer getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Integer clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
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

}
