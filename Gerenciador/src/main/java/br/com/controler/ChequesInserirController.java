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
import br.com.utils.StringUtils;

@ManagedBean(name = "chequesInserirController")
@SessionScoped
@ViewScoped
public class ChequesInserirController {

	private List<ChequeBean> listaCheques;

	private ChequeBean bean;

	private List<CompradorConjuntoBean> listaOutrosCompradores;

	private BigDecimal valorOutroComprador;

	private String nomeOutroPagador;

	private BigDecimal valorOutroPagador;

	public ChequesInserirController() {
		bean = new ChequeBean();
		listaOutrosCompradores = new ArrayList<CompradorConjuntoBean>();
		valorOutroComprador = new BigDecimal(0);
		loadTable();
	}

	private void loadTable() {
		listaCheques = PersistenceUtils.pesquisarUltimosCheques();
	}

	public void adicionarOutroComprador() {
		if (validarCamposOutroPagadaor()) {
			CompradorConjuntoBean comp = new CompradorConjuntoBean();
			comp.setNumCheque(bean.getNumCheque());
			comp.setNome(nomeOutroPagador);
			comp.setValor(valorOutroComprador);
			comp.setDataPagamento(bean.getDataPagamento());

			listaOutrosCompradores.add(comp);
			valorOutroComprador = new BigDecimal(0);
			nomeOutroPagador = StringUtils.STRING_VAZIA;
		}
	}

	private Boolean validarCamposOutroPagadaor() {
		Boolean valido = Boolean.TRUE;

		if (bean.getNumCheque() == null) {
			valido = Boolean.FALSE;
			MessagesUtils.waringMessage("Informe o número de cheque");
		}

		if (bean.getDataPagamento() == null) {
			valido = Boolean.FALSE;
			MessagesUtils.waringMessage("Informe a data de pagamento");
		}

		if (nomeOutroPagador.equalsIgnoreCase("")) {
			valido = Boolean.FALSE;
			MessagesUtils.waringMessage("Informe o nome do comprador");
		}

		if (valorOutroComprador == null) {
			valido = Boolean.FALSE;
			MessagesUtils.waringMessage("Informe o valor do comprador");
		}

		return valido;
	}

	public void inserir() {
		if (validarPreenchimento()) {
			ChequeBean cheque = new ChequeBean();
			String msg = "";
			try {
				cheque.setNumCheque(bean.getNumCheque());
				cheque.setBeneficiario(bean.getBeneficiario());
				cheque.setValor(bean.getValor());
				cheque.setDataEmissao(bean.getDataEmissao());
				cheque.setDataPagamento(bean.getDataPagamento());
				cheque.setObservacao(bean.getObservacao());
				cheque.setCompensado(false);

				msg = PersistenceUtils.salvar(cheque);
				for (CompradorConjuntoBean comp : listaOutrosCompradores) {
					PersistenceUtils.salvar(comp);
				}
			} catch (Exception e) {
				msg = "Problema ao inserir cheque";
			}

			MessagesUtils.infoMessage(msg);
			update();
		}
	}

	public void apagarCampos() {
		setBean(new ChequeBean());
		valorOutroComprador = new BigDecimal(0);
		nomeOutroPagador = "";
		listaOutrosCompradores.clear();
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
			MessagesUtils.errorMessage("Preencha todos os campos obrigatórios");
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

	public BigDecimal getValorOutroComprador() {
		return valorOutroComprador;
	}

	public void setValorOutroComprador(BigDecimal valorOutroComprador) {
		this.valorOutroComprador = valorOutroComprador;
	}

	public String getNomeOutroPagador() {
		return nomeOutroPagador;
	}

	public void setNomeOutroPagador(String nomeOutroPagador) {
		this.nomeOutroPagador = nomeOutroPagador;
	}

	public BigDecimal getValorOutroPagador() {
		return valorOutroPagador;
	}

	public void setValorOutroPagador(BigDecimal valorOutroPagador) {
		this.valorOutroPagador = valorOutroPagador;
	}

}
