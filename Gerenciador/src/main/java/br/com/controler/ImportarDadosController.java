package br.com.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.bean.CaixaDiarioBean;
import br.com.bean.ChequeBean;
import br.com.bean.CompraBean;
import br.com.bean.GastoBean;
import br.com.utils.PersistenceUtils;

@ManagedBean(name = "importarDadosController")
public class ImportarDadosController {

	private XSSFWorkbook worlbook;

	private UploadedFile arquivo;

	private List<SelectItem> tipoArquivo;

	private Integer tipoSelecionado;

	private List<ChequeBean> listaCheques;

	private List<CompraBean> listaCompras;

	private List<GastoBean> listaGastos;

	private List<CaixaDiarioBean> listaCaixa;

	public ImportarDadosController() {
		listaCaixa = new ArrayList<CaixaDiarioBean>();
		listaCheques = new ArrayList<ChequeBean>();
		listaCompras = new ArrayList<CompraBean>();
		listaGastos = new ArrayList<GastoBean>();
		carregarTipoArquivo();
	}

	private void carregarTipoArquivo() {
		tipoArquivo = new ArrayList<SelectItem>();

		tipoArquivo.add(new SelectItem(0, "Selecione..."));
		tipoArquivo.add(new SelectItem(1, "Caixa Diario"));
		tipoArquivo.add(new SelectItem(2, "Cheque"));
		tipoArquivo.add(new SelectItem(3, "Compra"));
		tipoArquivo.add(new SelectItem(4, "Gasto"));
	}

	public void importar(FileUploadEvent event) {
		try {
			arquivo = event.getFile();
			worlbook = new XSSFWorkbook(arquivo.getInputstream());
			
			XSSFSheet planilha = worlbook.getSheetAt(0);
			
			Iterator<Row> rowIterator = planilha.rowIterator();

			while(rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();				
				ChequeBean cheque = new ChequeBean();
				
				listaCheques.add(cheque);
				while(cellIterator.hasNext()) {					
					Cell cell = cellIterator.next();

					switch (cell.getColumnIndex()) {
					case 0:
						cheque.setNumCheque((int) cell.getNumericCellValue());
						break;

					case 1:
						cheque.setBeneficiario(cell.getStringCellValue());
						break;

					case 2:
						cheque.setValor(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;

					case 3:
						cheque.setDataEmissao(cell.getDateCellValue());
						break;

					case 4:
						cheque.setDataPagamento(cell.getDateCellValue());
						break;

					case 5:
						cheque.setObservacao(cell.getStringCellValue());
						break;
					}
				}
			}
			PersistenceUtils.openTransaction();
			PersistenceUtils.salvarlista(listaCheques.toArray());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public List<ChequeBean> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<ChequeBean> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public List<CompraBean> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<CompraBean> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public List<GastoBean> getListaGastos() {
		return listaGastos;
	}

	public void setListaGastos(List<GastoBean> listaGastos) {
		this.listaGastos = listaGastos;
	}

	public List<CaixaDiarioBean> getListaCaixa() {
		return listaCaixa;
	}

	public void setListaCaixa(List<CaixaDiarioBean> listaCaixa) {
		this.listaCaixa = listaCaixa;
	}

	public List<SelectItem> getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(List<SelectItem> tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public Integer getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(Integer tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public XSSFWorkbook getWorlbook() {
		return worlbook;
	}

	public void setWorlbook(XSSFWorkbook worlbook) {
		this.worlbook = worlbook;
	}

}
