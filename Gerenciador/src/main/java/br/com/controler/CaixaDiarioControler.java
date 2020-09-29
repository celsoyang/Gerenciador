package br.com.controler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.swing.JOptionPane;

import br.com.bean.CaixaDiarioBean;
import br.com.utils.PeristenceUtils;

@ManagedBean(name = "caixaDiarioControler")
public class CaixaDiarioControler {

	private CaixaDiarioBean bean;

	private List<CaixaDiarioBean> listaMes;

	public CaixaDiarioControler() {
		this.bean = new CaixaDiarioBean();
		getBean().setData(new Date());

		loadTable();
	}

	private void loadTable() {
//		CaixaDiarioBean dia;
		listaMes = new ArrayList<CaixaDiarioBean>();

		listaMes = PeristenceUtils.retornaCaixaDiario();

//		for (int i = 0; i < 150; i++) {
//			dia = new CaixaDiarioBean();
//			dia.setCodigo(i);
//			dia.setData(new Date());
//			dia.setEntrada(BigDecimal.valueOf(Double.valueOf(i + 100)));
//			dia.setSaida(BigDecimal.valueOf(Double.valueOf(i + 1000)));
//			listaMes.add(dia);
//		}
	}

	public void inserir() {
		JOptionPane.showMessageDialog(null, "Inserido com sucesso");
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

}
