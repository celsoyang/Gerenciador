package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.bean.CaixaDiarioBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "caixaDiarioController")
public class CaixaDiarioController {

	private CaixaDiarioBean bean;

	private List<CaixaDiarioBean> listaMes;
	private CaixaDiarioBean diaSelecionado;
	private LineChartModel graficoDiario;
	private BigDecimal total;
	private Double totalDouble;
	private Boolean alteracao = Boolean.FALSE;

	public CaixaDiarioController() {
		this.bean = new CaixaDiarioBean();
		total = new BigDecimal(0);
		totalDouble = 0d;
		getBean().setData(new Date());

		loadTable();
		gerarGrafico();
	}

	private void gerarGrafico() {
		graficoDiario = getModel();

		graficoDiario.setTitle("Gráfico Diário");
		graficoDiario.setLegendPosition("n");

		Axis y = graficoDiario.getAxis(AxisType.Y);
		y.setMax(3000);
		y.setMin(0);
		y.setLabel("Valor");

		Axis x = graficoDiario.getAxis(AxisType.X);
		x.setMax(31);
		x.setMin(1);
		x.setLabel("Dia");
		x.setTickFormat("%d");

	}

	public LineChartModel getModel() {
		LineChartModel model = new LineChartModel();
		LineChartSeries serieValor = new LineChartSeries("Valor");
		List<CaixaDiarioBean> listaDiaria = PersistenceUtils.retornaCaixaDiario();
		int i = 0;
		for (CaixaDiarioBean cd : listaDiaria) {
			serieValor.getData().put(i, cd.getSaida().subtract(cd.getEntrada()));
			i++;
		}

		model.addSeries(serieValor);

		return model;
	}

	private void loadTable() {
		listaMes = new ArrayList<CaixaDiarioBean>();
		listaMes = PersistenceUtils.retornaCaixaDiario();

		somar(listaMes);
	}

	private void somar(List<CaixaDiarioBean> listaMes2) {
		for (CaixaDiarioBean cd : listaMes2) {
			total.add(cd.getSaida().subtract(cd.getEntrada()));
			totalDouble += Double.valueOf(String.valueOf(cd.getSaida().subtract(cd.getEntrada())));
			System.out.println("Total: " + total);
			System.out.println("TotalD: " + totalDouble);
		}
	}

	public void alterar() {
		bean = diaSelecionado;
		setAlteracao(Boolean.TRUE);
	}

	public void confirmar() {
		if (validarCampos()) {
			CaixaDiarioBean cd = null;
			if (alteracao) {
				PersistenceUtils.openTransaction();
				cd = PersistenceUtils.getEntitiManager().find(CaixaDiarioBean.class, bean.getCodigo());
			}
			cd.setData(bean.getData());
			cd.setEntrada(bean.getEntrada());
			cd.setSaida(bean.getSaida());
			String msg = PersistenceUtils.salvar(cd);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("msgSalvo", new FacesMessage(msg));

			alteracao = Boolean.FALSE;
			loadTable();
			apagarCampos();
		}
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
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("msgSalvo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Preencha todos os campos"));
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

	public Double getTotalDouble() {
		return totalDouble;
	}

	public void setTotalDouble(Double totalDouble) {
		this.totalDouble = totalDouble;
	}

	public Boolean getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(Boolean alteracao) {
		this.alteracao = alteracao;
	}
}
