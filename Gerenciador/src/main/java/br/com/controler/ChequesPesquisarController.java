package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.bean.ChequeBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "controleChequesPesquisarController")
@SessionScoped
@ViewScoped
public class ChequesPesquisarController {

	private List<ChequeBean> listaCheques = new ArrayList<ChequeBean>();

	private Integer opcaoSelecionada;

	private Date dataDe;

	private Date dataAte;

	private String parametro = new String();

	private BigDecimal totalCheques = new BigDecimal(0);

	public ChequesPesquisarController() {
	}

	public void pesquisar() {
		listaCheques.clear();
		if (validarDatas()) {
			listaCheques = PersistenceUtils.pesquisarChequesPorData(dataDe, dataAte);
			somarCheques(listaCheques);
		}
	}

	private void somarCheques(List<ChequeBean> listaCheques2) {
		totalCheques = new BigDecimal(0);
		for (ChequeBean ch : listaCheques2) {
			totalCheques = totalCheques.add(ch.getValor());
		}
	}

	private boolean validarDatas() {
		Boolean valido = Boolean.TRUE;

		if (dataDe == null || dataAte == null) {
			valido = Boolean.FALSE;
			MessagesUtils.errorMessage("Preencha todas os campos de data");
		} else if (dataAte.before(dataDe)) {
			valido = Boolean.FALSE;
			MessagesUtils.errorMessage("Data inicial não pode ser maior que data final");
		}

		return valido;
	}

	public List<ChequeBean> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<ChequeBean> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public Integer getOpcaoSelecionada() {
		return opcaoSelecionada;
	}

	public void setOpcaoSelecionada(Integer opcaoSelecionada) {
		this.opcaoSelecionada = opcaoSelecionada;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public Date getDataDe() {
		return dataDe;
	}

	public void setDataDe(Date dataDe) {
		this.dataDe = dataDe;
	}

	public Date getDataAte() {
		return dataAte;
	}

	public void setDataAte(Date dataAte) {
		this.dataAte = dataAte;
	}

	public BigDecimal getTotalCheques() {
		return totalCheques;
	}

	public void setTotalCheques(BigDecimal totalCheques) {
		this.totalCheques = totalCheques;
	}

}
