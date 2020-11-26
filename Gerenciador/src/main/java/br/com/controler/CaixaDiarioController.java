package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.LineChartModel;

import br.com.bean.CaixaDiarioBean;
import br.com.utils.MessagesUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "caixaDiarioController")
@SessionScoped
public class CaixaDiarioController {

	private CaixaDiarioBean bean;

	private List<CaixaDiarioBean> listaMes;
	private CaixaDiarioBean diaSelecionado;
	private LineChartModel graficoDiario;
	private BigDecimal total;
	private Boolean alteracao = Boolean.FALSE;

	public CaixaDiarioController() {
		this.bean = new CaixaDiarioBean();
		total = new BigDecimal(0);
		getBean().setData(new Date());

		loadTable();
	}

	private void loadTable() {
		listaMes = new ArrayList<CaixaDiarioBean>();
		listaMes = PersistenceUtils.retornaCaixaDiario();

		somar(listaMes);
	}

	private void somar(List<CaixaDiarioBean> lista) {
		total = new BigDecimal(0);
		for (CaixaDiarioBean cd : lista) {
			total = total.add(cd.getSaida().subtract(cd.getEntrada()));
		}
	}

	public void alterar() {
		bean = diaSelecionado;
		setAlteracao(Boolean.TRUE);
	}

	public void confirmar() {
		if (validarCampos() && validarData()) {
			CaixaDiarioBean cd = new CaixaDiarioBean();
			if (alteracao) {
				PersistenceUtils.openTransaction();
				cd = PersistenceUtils.getEntitiManager().find(CaixaDiarioBean.class, bean.getCodigo());
			}
			cd.setData(bean.getData());
			cd.setEntrada(bean.getEntrada());
			cd.setSaida(bean.getSaida());
			String msg = PersistenceUtils.salvar(cd);

			MessagesUtils.infoMessage(msg);

			alteracao = Boolean.FALSE;
			update();
		}
	}

	private boolean validarData() {
		Boolean retorno = Boolean.TRUE;

		if (bean.getData() == null) {
			retorno = Boolean.FALSE;
		}

		if (PersistenceUtils.dataCaixaExiste(bean.getData()) && !alteracao) {
			MessagesUtils.errorMessage("Data já inserida, faça uma alteração");
			return Boolean.FALSE;
		}

		if (!retorno) {
			MessagesUtils.errorMessage("Data Incorreta");
		}

		return retorno;
	}

	private boolean validarCampos() {
		Boolean retorno = Boolean.TRUE;

		if (bean.getData() == null) {
			retorno = Boolean.FALSE;
		}

		if (bean.getEntrada() == null) {
			retorno = Boolean.FALSE;
		}

		if (bean.getSaida() == null) {
			retorno = Boolean.FALSE;
		}

		if (!retorno) {
			MessagesUtils.errorMessage("Preencha todos os Campos");
		}

		return retorno;
	}

	private void apagarCampos() {
		bean.setCodigo(null);
		bean.setData(new Date());
		bean.setEntrada(null);
		bean.setSaida(null);
	}

	public void excluir() {
		CaixaDiarioBean cd = new CaixaDiarioBean();
		PersistenceUtils.openTransaction();
		cd = PersistenceUtils.getEntitiManager().find(CaixaDiarioBean.class, diaSelecionado.getCodigo());

		String msg = PersistenceUtils.delete(cd);

		MessagesUtils.infoMessage(msg);

		update();
	}

	private void update() {
		apagarCampos();
		loadTable();
		somar(listaMes);
	}

	public CaixaDiarioBean getBean() {
		return bean;
	}

	public void setBean(CaixaDiarioBean bean) {
		this.bean = bean;
	}

	public List<CaixaDiarioBean> getListaMes() {
		return listaMes;
	}

	public void setListaMes(List<CaixaDiarioBean> listaMes) {
		this.listaMes = listaMes;
	}

	public CaixaDiarioBean getDiaSelecionado() {
		return diaSelecionado;
	}

	public void setDiaSelecionado(CaixaDiarioBean diaSelecionado) {
		this.diaSelecionado = diaSelecionado;
	}

	public LineChartModel getGraficoDiario() {
		return graficoDiario;
	}

	public void setGraficoDiario(LineChartModel graficoDiario) {
		this.graficoDiario = graficoDiario;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Boolean getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(Boolean alteracao) {
		this.alteracao = alteracao;
	}
}
