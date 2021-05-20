package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.bean.ChequeBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "controleChequesPesquisarController")
@SessionScoped
@ViewScoped
public class ChequesPesquisarController {

	private List<ChequeBean> listaCheques = new ArrayList<ChequeBean>();;

	private List<SelectItem> listaOpcoes = new ArrayList<SelectItem>();;

	private Integer opcaoSelecionada;

	private Date dataDe;

	private Date dataAte;

	private String parametro = new String();

	private BigDecimal totalCheques = new BigDecimal(0);

	public ChequesPesquisarController() {
		loadOpcoes();
	}

	private void loadOpcoes() {
		listaOpcoes = new ArrayList<SelectItem>();

		listaOpcoes.add(new SelectItem(0, "Selecione..."));
		listaOpcoes.add(new SelectItem(1, "Data de Pagamento"));
		listaOpcoes.add(new SelectItem(2, "Data de Emissão"));
		listaOpcoes.add(new SelectItem(3, "Número"));
		listaOpcoes.add(new SelectItem(4, "Beneficiário"));
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

	public List<SelectItem> getListaOpcoes() {
		return listaOpcoes;
	}

	public void setListaOpcoes(List<SelectItem> listaOpcoes) {
		this.listaOpcoes = listaOpcoes;
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
