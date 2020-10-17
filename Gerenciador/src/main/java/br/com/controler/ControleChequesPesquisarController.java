package br.com.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlInputText;
import javax.faces.model.SelectItem;

import br.com.bean.ChequeBean;

@ManagedBean(name = "controleChequesPesquisarController")
public class ControleChequesPesquisarController {

	private List<ChequeBean> listaCheques;

	private List<SelectItem> listaOpcoes;

	private Integer opcaoSelecionada;

	private Date dataEmissao;

	private Date dataPagamento;

	private String parametro;
	
	private HtmlInputText campoParametro;
	
	private HtmlInputText campoDe;
	
	private HtmlInputText campoAte;

	public ControleChequesPesquisarController() {
		loadOpcoes();
		opcaoSelecionada = 4;
	}

	private void loadOpcoes() {
		listaOpcoes = new ArrayList<SelectItem>();

		listaOpcoes.add(new SelectItem(0, "Selecione..."));
		listaOpcoes.add(new SelectItem(1, "Data de Pagamento"));
		listaOpcoes.add(new SelectItem(2, "Data de Emissão"));
		listaOpcoes.add(new SelectItem(3, "Número"));
		listaOpcoes.add(new SelectItem(4, "Beneficiário"));
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

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
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

}
