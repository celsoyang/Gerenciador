package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.bean.ChequeBean;
import br.com.bean.CompradorConjuntoBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "controleChequesPesquisarController")
@SessionScoped
@ViewScoped
public class ChequesPesquisarController {

	private List<ChequeBean> listaCheques;

	private Integer opcaoSelecionada;

	private Date dataDe;

	private Date dataAte;

	private String parametro;

	private BigDecimal totalCheques;

	private BigDecimal totalPadaria;

	private BigDecimal totalOutrosCompradores;

	public ChequesPesquisarController() {
	}

	public void pesquisar() {
		listaCheques = new ArrayList<ChequeBean>();
		if (validarDatas()) {
			listaCheques = PersistenceUtils.pesquisarChequesPorData(dataDe, dataAte);
			somarCheques(listaCheques);
		}

		caucularValores(listaCheques);
	}

	private void caucularValores(List<ChequeBean> lista) {
		totalOutrosCompradores = new BigDecimal(0);
		totalPadaria = new BigDecimal(0);
		BigDecimal totalCheques = new BigDecimal(0);
		List<CompradorConjuntoBean> listaCC = PersistenceUtils.retornaCompradoresConjuntos(lista);

		for (CompradorConjuntoBean cc : listaCC) {
			totalOutrosCompradores = totalOutrosCompradores.add(cc.getValor());
		}

		for (ChequeBean cb : lista) {
			totalCheques = totalCheques.add(cb.getValor());
		}

		totalPadaria = totalCheques.subtract(totalOutrosCompradores);
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

	public BigDecimal getTotalPadaria() {
		return totalPadaria;
	}

	public void setTotalPadaria(BigDecimal totalPadaria) {
		this.totalPadaria = totalPadaria;
	}

	public BigDecimal getTotalOutrosCompradores() {
		return totalOutrosCompradores;
	}

	public void setTotalOutrosCompradores(BigDecimal totalOutrosCompradores) {
		this.totalOutrosCompradores = totalOutrosCompradores;
	}

}
