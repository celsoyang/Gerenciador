package br.com.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import br.com.enums.TipoImportacaoEnum;

public class ImportacaoUtils {

	private static XSSFWorkbook worlbook;

	private static UploadedFile arquivo;

	private static List<ChequeBean> listaCheques;

	private static List<CompraBean> listaCompras;

	private static List<GastoBean> listaGastos;

	private static List<CaixaDiarioBean> listaCaixa;

	public ImportacaoUtils() {
	}

	public static String importar(FileUploadEvent event, Integer tipoImportacao) {
		if (tipoImportacao.equals(TipoImportacaoEnum.CAIXA.getCodigo()))
			return importarCaixa(event);
		else if (tipoImportacao.equals(TipoImportacaoEnum.CHEQUE.getCodigo()))
			return importarCheque(event);
		else if (tipoImportacao.equals(TipoImportacaoEnum.COMPRA.getCodigo()))
			return importarCompra(event);
		else if (tipoImportacao.equals(TipoImportacaoEnum.GASTO.getCodigo()))
			return importarGasto(event);
		return null;
	}

	private static String importarCaixa(FileUploadEvent event) {
		try {
			listaCaixa = new ArrayList<CaixaDiarioBean>();
			arquivo = event.getFile();
			worlbook = new XSSFWorkbook(arquivo.getInputstream());

			XSSFSheet planilha = worlbook.getSheetAt(0);

			Iterator<Row> rowIterator = planilha.rowIterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				CaixaDiarioBean caixa = new CaixaDiarioBean();

				listaCaixa.add(caixa);
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getColumnIndex()) {
					case 0:
						caixa.setData(cell.getDateCellValue());
						break;

					case 1:
						caixa.setEntrada(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;

					case 2:
						caixa.setSaida(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;
					}
				}
			}
			return PersistenceUtils.salvarlista(listaCaixa.toArray());

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "Problema na criação da planilha";
		}
	}

	private static String importarCheque(FileUploadEvent event) {
		try {
			listaCheques = new ArrayList<ChequeBean>();
			arquivo = event.getFile();
			worlbook = new XSSFWorkbook(arquivo.getInputstream());

			XSSFSheet planilha = worlbook.getSheetAt(0);

			Iterator<Row> rowIterator = planilha.rowIterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				ChequeBean cheque = new ChequeBean();

				listaCheques.add(cheque);
				while (cellIterator.hasNext()) {
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
			return PersistenceUtils.salvarlista(listaCheques.toArray());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "Problema na criação da planilha";
		}
	}

	private static String importarGasto(FileUploadEvent event) {
		try {
			listaGastos = new ArrayList<GastoBean>();
			arquivo = event.getFile();
			worlbook = new XSSFWorkbook(arquivo.getInputstream());

			XSSFSheet planilha = worlbook.getSheetAt(0);

			Iterator<Row> rowIterator = planilha.rowIterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				GastoBean gasto = new GastoBean();

				listaGastos.add(gasto);
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getColumnIndex()) {
					case 0:
						gasto.setDescricao(cell.getStringCellValue());
						break;

					case 1:
						gasto.setValor(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;

					case 2:
						gasto.setData(cell.getDateCellValue());
						break;
					}
				}
			}
			return PersistenceUtils.salvarlista(listaGastos.toArray());

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "Problema na criação da planilha";
		}
	}

	private static String importarCompra(FileUploadEvent event) {
		try {
			listaCompras = new ArrayList<CompraBean>();
			arquivo = event.getFile();
			worlbook = new XSSFWorkbook(arquivo.getInputstream());

			XSSFSheet planilha = worlbook.getSheetAt(0);

			Iterator<Row> rowIterator = planilha.rowIterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				CompraBean compra = new CompraBean();

				listaCompras.add(compra);
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getColumnIndex()) {
					case 0:
						compra.setVendedor(cell.getStringCellValue());
						break;

					case 1:
						compra.setValor(BigDecimal.valueOf(cell.getNumericCellValue()));
						break;

					case 2:
						compra.setDataCompra(cell.getDateCellValue());
						break;

					case 3:
						compra.setDataPagamento(cell.getDateCellValue());
						break;
					}
				}
			}
			return PersistenceUtils.salvarlista(listaCompras.toArray());

		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "Problema na criação da planilha";
		}
	}

	public static XSSFWorkbook getWorlbook() {
		return worlbook;
	}

	public static void setWorlbook(XSSFWorkbook worlbook) {
		ImportacaoUtils.worlbook = worlbook;
	}

	public static UploadedFile getArquivo() {
		return arquivo;
	}

	public static void setArquivo(UploadedFile arquivo) {
		ImportacaoUtils.arquivo = arquivo;
	}

	public static List<ChequeBean> getListaCheques() {
		return listaCheques;
	}

	public static void setListaCheques(List<ChequeBean> listaCheques) {
		ImportacaoUtils.listaCheques = listaCheques;
	}

	public static List<CompraBean> getListaCompras() {
		return listaCompras;
	}

	public static void setListaCompras(List<CompraBean> listaCompras) {
		ImportacaoUtils.listaCompras = listaCompras;
	}

	public static List<GastoBean> getListaGastos() {
		return listaGastos;
	}

	public static void setListaGastos(List<GastoBean> listaGastos) {
		ImportacaoUtils.listaGastos = listaGastos;
	}

	public static List<CaixaDiarioBean> getListaCaixa() {
		return listaCaixa;
	}

	public static void setListaCaixa(List<CaixaDiarioBean> listaCaixa) {
		ImportacaoUtils.listaCaixa = listaCaixa;
	}
}
