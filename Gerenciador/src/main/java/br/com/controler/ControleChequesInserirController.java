package br.com.controler;

import java.util.List;

import javax.faces.bean.ManagedBean;

import br.com.bean.ChequeBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "controleChequesInserirController")
public class ControleChequesInserirController {

	private List<ChequeBean> listaCheques;

	private ChequeBean bean;

	public ControleChequesInserirController() {
		bean = new ChequeBean();
		loadTable();
	}

	private void loadTable() {
		listaCheques = PersistenceUtils.pesquisarUltimosCheques();
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

}
