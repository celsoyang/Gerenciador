package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.bean.CaixaDiarioBean;
import br.com.utils.PeristenceUtils;

@ManagedBean(name = "caixaDiarioControler")
public class CaixaDiarioControler {

	private CaixaDiarioBean bean;

	private List<CaixaDiarioBean> listaMes;
	private CaixaDiarioBean diaSelecionado;
	private LineChartModel graficoDiario;
	private BigDecimal total;
	private Double totalDouble;

	public CaixaDiarioControler() {
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
		List<CaixaDiarioBean> listaDiaria = PeristenceUtils.retornaCaixaDiario();
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
		listaMes = PeristenceUtils.retornaCaixaDiario();

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
		getBean().setCodigo(getDiaSelecionado().getCodigo());
		getBean().setData(getDiaSelecionado().getData());
		getBean().setEntrada(getDiaSelecionado().getEntrada());
		getBean().setSaida(getDiaSelecionado().getSaida());
	}

	public void confirmar() {
		getListaMes().set(bean.getCodigo(), getBean());
	}

	public void excluir() {
		System.out.println("Exluído com sucesso");
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
}
