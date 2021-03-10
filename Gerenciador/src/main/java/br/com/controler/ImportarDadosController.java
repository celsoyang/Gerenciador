package br.com.controler;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;

import br.com.enums.TipoImportacaoEnum;
import br.com.utils.ImportacaoUtils;
import br.com.utils.MessagesUtils;

@ManagedBean(name = "importarDadosController")
@SessionScoped
@ViewScoped
public class ImportarDadosController {

	private List<SelectItem> tipoArquivo;

	private String tipoSelecionado;

	public ImportarDadosController() {
		tipoArquivo = new ArrayList<SelectItem>();
		carregarTipoArquivo();
	}

	private void carregarTipoArquivo() {
		tipoArquivo = new ArrayList<SelectItem>();
		SelectItem item;
		tipoArquivo.add(new SelectItem("0", "Selecione..."));
		for (TipoImportacaoEnum tie : TipoImportacaoEnum.values()) {
			item = new SelectItem();
			item.setValue(String.valueOf(tie.getCodigo()));
			item.setLabel(tie.getDescicao());
			tipoArquivo.add(item);
		}
	}

	public void importar(FileUploadEvent event) {
		if (validarImportacao()) {
			String msg = ImportacaoUtils.importar(event, Integer.parseInt(tipoSelecionado));
			MessagesUtils.infoMessage(msg);
		}
	}

	private boolean validarImportacao() {
		Boolean retorno = Boolean.TRUE;

		if (tipoSelecionado.equals("0")) {
			retorno = Boolean.FALSE;
		}
		return retorno;
	}

	public List<SelectItem> getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(List<SelectItem> tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(String tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

}
