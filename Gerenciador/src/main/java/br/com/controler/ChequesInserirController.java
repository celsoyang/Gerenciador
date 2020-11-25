package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import br.com.bean.ChequeBean;
import br.com.bean.CompradorConjuntoBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "chequesInserirController")
public class ChequesInserirController {

	private List<ChequeBean> listaCheques;

	private ChequeBean bean;

	private List<CompradorConjuntoBean> listaOutrosCompradores;

	private List<CompradorConjuntoBean> listaOutrosCompradoresSelecionados;

	private List<SelectItem> listaCombo;

	private Integer compradorSelecionado;

	private BigDecimal valorOutroComprador;

	public ChequesInserirController() {
		listaOutrosCompradoresSelecionados = new ArrayList<CompradorConjuntoBean>();
		bean = new ChequeBean();
		loadCompradores();
		loadTable();
	}

	private void loadCompradores() {
		listaOutrosCompradores = new ArrayList<CompradorConjuntoBean>();
		listaOutrosCompradores.addAll(PersistenceUtils.retornaCompradoresConjuntos());

		listaCombo = new ArrayList<SelectItem>();
		listaCombo.add(new SelectItem(0, "Selecione..."));
		for (CompradorConjuntoBean comprador : listaOutrosCompradores) {
			listaCombo.add(new SelectItem(comprador.getCodigo(), comprador.getNome()));
		}
	}

	private void loadTable() {
		listaCheques = PersistenceUtils.pesquisarUltimosCheques();
	}

	public void adicionarOutroComprador() {
		CompradorConjuntoBean comp = new CompradorConjuntoBean();

		comp.setCodigo(compradorSelecionado);
		comp.setValor(valorOutroComprador);

		listaOutrosCompradoresSelecionados.add(comp);
	}

	public void inserir() {
		if (validarPreenchimento()) {
			ChequeBean cheque = new ChequeBean();

			cheque.setNumCheque(bean.getNumCheque());
			cheque.setBeneficiario(bean.getBeneficiario());
			cheque.setValor(bean.getValor());
			cheque.setDataEmissao(bean.getDataEmissao());
			cheque.setDataPagamento(bean.getDataPagamento());
			cheque.setObservacao(bean.getObservacao());

			String msg = PersistenceUtils.salvar(cheque);

			MessagesUtils.infoMessage(msg);
			update();
		}
	}

	private void apagarCampos() {
		setBean(new ChequeBean());
	}

	private Boolean validarPreenchimento() {
		Boolean retorno = Boolean.TRUE;

		if (getBean().getNumCheque() == null) {
			retorno = Boolean.FALSE;
		}

		if (getBean().getBeneficiario().isEmpty()) {
			retorno = Boolean.FALSE;
		}

		if (getBean().getValor() == null) {
			retorno = Boolean.FALSE;
		}

		if (getBean().getDataEmissao() == null) {
			retorno = Boolean.FALSE;
		}

		if (getBean().getDataPagamento() == null) {
			retorno = Boolean.FALSE;
		}

		if (!retorno) {
			MessagesUtils.errorMessage("Preencha todos os campos");
		}

		return retorno;
	}

	private void update() {
		loadTable();
		apagarCampos();
	}

	public List<ChequeBean> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<ChequeBean> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public ChequeBean getBean() {
		return bean;
	}

	public void setBean(ChequeBean bean) {
		this.bean = bean;
	}

	public List<CompradorConjuntoBean> getListaOutrosCompradores() {
		return listaOutrosCompradores;
	}

	public void setListaOutrosCompradores(List<CompradorConjuntoBean> listaOutrosCompradores) {
		this.listaOutrosCompradores = listaOutrosCompradores;
	}

	public List<SelectItem> getListaCombo() {
		return listaCombo;
	}

	public void setListaCombo(List<SelectItem> listaCombo) {
		this.listaCombo = listaCombo;
	}

	public List<CompradorConjuntoBean> getListaOutrosCompradoresSelecionados() {
		return listaOutrosCompradoresSelecionados;
	}

	public void setListaOutrosCompradoresSelecionados(List<CompradorConjuntoBean> listaOutrosCompradoresSelecionados) {
		this.listaOutrosCompradoresSelecionados = listaOutrosCompradoresSelecionados;
	}

	public BigDecimal getValorOutroComprador() {
		return valorOutroComprador;
	}

	public void setValorOutroComprador(BigDecimal valorOutroComprador) {
		this.valorOutroComprador = valorOutroComprador;
	}

	public Integer getCompradorSelecionado() {
		return compradorSelecionado;
	}

	public void setCompradorSelecionado(Integer compradorSelecionado) {
		this.compradorSelecionado = compradorSelecionado;
	}

}
