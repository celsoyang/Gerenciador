package br.com.controler;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.swing.JOptionPane;

import br.com.bean.CaixaDiarioBean;

@ManagedBean(name = "caixaDiarioControler")
public class CaixaDiarioControler {

	private CaixaDiarioBean bean;

	public CaixaDiarioControler() {
		this.bean = new CaixaDiarioBean();
		getBean().setData(new Date());
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

}
