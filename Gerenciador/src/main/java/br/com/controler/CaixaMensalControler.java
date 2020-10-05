package br.com.controler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.bean.CaixaDiarioBean;
import br.com.bean.CaixaMesBean;
import br.com.enums.MesesEnum;
import br.com.utils.PeristenceUtils;

@ManagedBean(name = "caixaMensalControler")
public class CaixaMensalControler {

	private CaixaMesBean mes;

	private List<CaixaDiarioBean> meses;

	private List<SelectItem> listaMeses;

	private List<String> mesesSelecionados;

	private LineChartModel graficoDiario;

	public CaixaMensalControler() {
		gerarComboBox();
		gerarGrafico();
	}

	private void gerarComboBox() {
		listaMeses = new ArrayList<SelectItem>();
		SelectItem item;
		for (MesesEnum mes : MesesEnum.values()) {
			item = new SelectItem();
			item.setValue(String.valueOf(mes.getNumeroMes()));
			item.setLabel(mes.getNomeMes());
			listaMeses.add(item);
		}
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
	
	public void pesquisar() {
		List<CaixaDiarioBean> meses = PeristenceUtils.pesquisarPorMeses(mesesSelecionados);
	}

	public CaixaMesBean getMes() {
		return mes;
	}

	public void setMes(CaixaMesBean mes) {
		this.mes = mes;
	}

	public List<CaixaDiarioBean> getMeses() {
		return meses;
	}

	public void setMeses(List<CaixaDiarioBean> meses) {
		this.meses = meses;
	}

	public LineChartModel getGraficoDiario() {
		return graficoDiario;
	}

	public void setGraficoDiario(LineChartModel graficoDiario) {
		this.graficoDiario = graficoDiario;
	}

	public void setListaMeses(List<SelectItem> listaMeses) {
		this.listaMeses = listaMeses;
	}

	public List<SelectItem> getListaMeses() {
		return listaMeses;
	}

	public List<String> getMesesSelecionados() {
		return mesesSelecionados;
	}

	public void setMesesSelecionados(List<String> mesesSelecionados) {
		this.mesesSelecionados = mesesSelecionados;
	}

}
