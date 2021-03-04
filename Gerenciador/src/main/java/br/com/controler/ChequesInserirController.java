package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.bean.ChequeBean;
import br.com.bean.CompradorConjuntoBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "chequesInserirController")
@SessionScoped
@ViewScoped
public class ChequesInserirController {

	private List<ChequeBean> listaCheques;

	private ChequeBean bean;

	private List<CompradorConjuntoBean> listaOutrosCompradores;

	private List<CompradorConjuntoBean> listaOutrosCompradoresSelecionados;

	private List<CompradorConjuntoBean> listaCombo;

	private CompradorConjuntoBean compradorSelecionado;

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

		listaCombo = new ArrayList<CompradorConjuntoBean>();
		for (CompradorConjuntoBean comprador : listaOutrosCompradores) {
			listaCombo.add(comprador);
		}
	}

	private void loadTable() {
		listaCheques = PersistenceUtils.pesquisarUltimosCheques();
	}

	public String adicionarOutroComprador() {
		CompradorConjuntoBean comp = new CompradorConjuntoBean();
		comp.setCodigo(compradorSelecionado.getCodigo());
		comp.setNome(compradorSelecionado.toString());
		comp.setValor(valorOutroComprador);
		comp.setDataPagamento(bean.getDataPagamento());
		listaOutrosCompradoresSelecionados.add(comp);
		
		return "";
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

	public void apagarCampos() {
		setBean(new ChequeBean());
		listaOutrosCompradoresSelecionados = new ArrayList<CompradorConjuntoBean>();
		valorOutroComprador = new BigDecimal(0);
		compradorSelecionado = null;
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

	public List<CompradorConjuntoBean> getListaCombo() {
		return listaCombo;
	}

	public void setListaCombo(List<CompradorConjuntoBean> listaCombo) {
		this.listaCombo = listaCombo;
	}

	public CompradorConjuntoBean getCompradorSelecionado() {
		return compradorSelecionado;
	}

	public void setCompradorSelecionado(CompradorConjuntoBean compradorSelecionado) {
		this.compradorSelecionado = compradorSelecionado;
	}

}
