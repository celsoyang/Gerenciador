package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.bean.CaixaMesBean;
import br.com.enums.MesesEnum;
import br.com.utils.ColorUtils;
import br.com.utils.NumberUtils;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "caixaMensalController")
public class CaixaMensalController {

	private List<CaixaMesBean> meses;

	private List<SelectItem> listaMeses;

	private List<String> mesesSelecionados;

	private LineChartModel graficoDiario;

	private BarChartModel graficoBarra;

	public CaixaMensalController() {
		loadTable();
		gerarComboBox();
		gerarGrafico();
		gerarGraficoBarra();
	}

	private void loadTable() {
		meses = new ArrayList<CaixaMesBean>();
		List<Object[]> listaMeses = new ArrayList<Object[]>();
		listaMeses = PersistenceUtils.retornaTotalMeses();
		CaixaMesBean mes;

		for (Object[] m : listaMeses) {
			mes = new CaixaMesBean();
			
			if (m[0] != null) {
				mes.setNomeMes(MesesEnum.getBayNumero((int) ((double) m[0])).getNomeMes());
				mes.setNumeroMes((int) ((double) m[0]));
				mes.setTotalMes(new BigDecimal(String.valueOf(m[1])));

				meses.add(mes);
			}
		}
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
		y.setMax(70000);
		y.setMin(0);
		y.setLabel("Valor");

		Axis x = graficoDiario.getAxis(AxisType.X);
		x.setMax(12);
		x.setMin(0);
		x.setLabel("Mês");
		x.setTickFormat("%d");
	}

	public LineChartModel getModel() {
		LineChartModel model = new LineChartModel();
		LineChartSeries serieValor = new LineChartSeries("Valor");
		int i = 0;
		for (CaixaMesBean cm : meses) {
			serieValor.getData().put(i, cm.getTotalMes());
			i++;
		}
		model.addSeries(serieValor);

		return model;
	}

	private void gerarGraficoBarra() {
		graficoBarra = getModerBarra();

		graficoBarra.setTitle("Resumo Anual");
		graficoBarra.setLegendPosition("n");
		graficoBarra.setSeriesColors(ColorUtils.LARANJA);
		graficoBarra.setShowPointLabels(true);

		Axis y = graficoBarra.getAxis(AxisType.Y);
		y.setMax(NumberUtils.maximoGraficoBarra);
		y.setMin(0);
		y.setLabel("Valor");

		Axis x = graficoBarra.getAxis(AxisType.X);
		x.setLabel("Mês");
		x.setTickFormat("%d");
		x.setTickCount(1);
	}

	private BarChartModel getModerBarra() {

		BarChartModel model = new BarChartModel();

		ChartSeries serie = new ChartSeries();

		serie.setLabel("Valor");
		for (CaixaMesBean mes : meses) {
			serie.set(mes.getNomeMes(), mes.getTotalMes());
		}
		model.addSeries(serie);

		return model;
	}

	public void pesquisar() {
		if (verificarMesesSelecionados()) {
			List<Object[]> listaRetorno = PersistenceUtils.pesquisarPorMeses(mesesSelecionados);
			CaixaMesBean mes;
			meses.clear();

			for (Object[] m : listaRetorno) {
				mes = new CaixaMesBean();

				mes.setNomeMes(MesesEnum.getBayNumero((int) ((double) m[0])).getNomeMes());
				mes.setNumeroMes((int) ((double) m[0]));
				mes.setTotalMes(new BigDecimal(String.valueOf(m[1])));

				meses.add(mes);
			}

			gerarGraficoBarra();
		}
	}

	private boolean verificarMesesSelecionados() {
		Boolean retorno = Boolean.TRUE;

		if (mesesSelecionados.isEmpty()) {
			retorno = Boolean.FALSE;

			FacesContext pesquisarVazio = FacesContext.getCurrentInstance();
			pesquisarVazio.addMessage("pesquisarVazio",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "É necessário selecionar ao menos um mês"));
		}

		return retorno;
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

	public List<CaixaMesBean> getMeses() {
		return meses;
	}

	public void setMeses(List<CaixaMesBean> meses) {
		this.meses = meses;
	}

	public BarChartModel getGraficoBarra() {
		return graficoBarra;
	}

	public void setGraficoBarra(BarChartModel graficoBarra) {
		this.graficoBarra = graficoBarra;
	}

}
