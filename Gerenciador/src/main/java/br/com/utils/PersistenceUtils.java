package br.com.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.bean.CaixaDiarioBean;
import br.com.bean.ChequeBean;
import br.com.bean.ClienteBean;
import br.com.bean.CompraClienteBean;
import br.com.bean.CompradorConjuntoBean;
import br.com.bean.PagamentosClienteBean;
import br.com.bean.UsuarioBean;

public class PersistenceUtils {

	private static EntityManager entitiManager;

	private static EntityManager returnEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Gerenciador");
		entitiManager = factory.createEntityManager();

		return entitiManager;
	}

	public static EntityManager getEntitiManager() {
		return entitiManager;
	}

	public static void setEntitiManager(EntityManager entitiManager) {
		PersistenceUtils.entitiManager = entitiManager;
	}

	public static void openTransaction() {
		returnEntityManager().getTransaction().begin();
	}

	public static void commitTransaction() {
		getEntitiManager().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		getEntitiManager().getTransaction().rollback();
	}

	public static String salvar(Object objeto) {
		try {
			if (!getEntitiManager().getTransaction().isActive()) {
				openTransaction();
			}
			getEntitiManager().persist(objeto);
			getEntitiManager().getTransaction().commit();
			return StringUtils.MSG_SALVO_SUCESSO;
		} catch (Exception e) {
			getEntitiManager().getTransaction().rollback();
			return StringUtils.MSG_PROBLEMA_SALVAR;
		}
	}

	public static String salvarlista(Object[] lista) {
		try {
			if (!getEntitiManager().getTransaction().isActive()) {
				getEntitiManager().getTransaction().begin();
			}
			for (Object objeto : lista) {
				getEntitiManager().persist(objeto);
			}
			getEntitiManager().getTransaction().commit();
			return StringUtils.MSG_SALVO_SUCESSO;
		} catch (Exception e) {
			getEntitiManager().getTransaction().rollback();
			return StringUtils.MSG_PROBLEMA_SALVAR;
		}
	}

	public static String delete(Object cd) {
		try {
			getEntitiManager().getTransaction().begin();
			getEntitiManager().remove(cd);
			getEntitiManager().getTransaction().commit();
			return StringUtils.MSG_REMOVIDO_SUCESSO;
		} catch (Exception e) {
			return StringUtils.MSG_PROBLEMA_SALVAR;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Object[]> pesquisarPorMeses(List<String> mesesSelecionados) {
		List<Object[]> retorno = new ArrayList<Object[]>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT DATE_PART('MONTH', DIA), SUM(SAIDA-ENTRADA)");
		sql.append(" FROM CONTROLE_DIARIO");
		sql.append(" WHERE DATE_PART('MONTH', DIA) IN(");
		for (String mes : mesesSelecionados) {
			sql.append("'" + mes + "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		sql.append(" GROUP BY DATE_PART('MONTH', DIA)");
		sql.append(" ORDER BY DATE_PART('MONTH', DIA)");

		Query query = returnEntityManager().createNativeQuery(sql.toString());

		retorno = query.getResultList();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<CaixaDiarioBean> retornaCaixaDiario() {
		List<CaixaDiarioBean> retorno = new ArrayList<CaixaDiarioBean>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CD FROM CaixaDiarioBean CD");
		sql.append(" WHERE DATE_PART('MONTH',CD.data) = DATE_PART('MONTH', CURRENT_DATE)");
		sql.append(" ORDER BY CD.data");

		Query query = returnEntityManager().createQuery(sql.toString(), CaixaDiarioBean.class);

		retorno = (List<CaixaDiarioBean>) query.getResultList();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<Object[]> retornaTotalMeses() {
		List<Object[]> retorno = new ArrayList<Object[]>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT DATE_PART('MONTH', DIA), SUM(SAIDA-ENTRADA)");
		sql.append(" FROM CONTROLE_DIARIO");
		sql.append(" GROUP BY DATE_PART('MONTH', DIA)");
		sql.append(" ORDER BY DATE_PART('MONTH', DIA)");

		Query query = returnEntityManager().createNativeQuery(sql.toString());

		retorno = query.getResultList();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<ChequeBean> pesquisarUltimosCheques() {
		List<ChequeBean> retorno = new ArrayList<ChequeBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CHEQUE FROM ChequeBean CHEQUE");
		sql.append(" ORDER BY CHEQUE.numCheque DESC");

		Query query = returnEntityManager().createQuery(sql.toString(), ChequeBean.class);
		query.setMaxResults(10);

		retorno = query.getResultList();

		return retorno;
	}

	public static void importarCheques(List<ChequeBean> listaCheques) {

	}

	public static Boolean dataCaixaExiste(Date data) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CAIXA FROM CaixaDiarioBean CAIXA");
		sql.append(" WHERE CAIXA.data = '" + new SimpleDateFormat("dd/MM/yyyy").format(data) + "'");

		Query query = returnEntityManager().createQuery(sql.toString(), CaixaDiarioBean.class);

		Integer c = query.getResultList().size();

		if (c > 0) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@SuppressWarnings("unchecked")
	public static List<CompradorConjuntoBean> retornaCompradoresConjuntos() {
		List<CompradorConjuntoBean> retorno = new ArrayList<CompradorConjuntoBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COMPRADOR FROM CompradorConjuntoBean COMPRADOR");
		sql.append(" ORDER BY COMPRADOR.nome");

		Query query = returnEntityManager().createQuery(sql.toString(), CompradorConjuntoBean.class);

		retorno = query.getResultList();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<SelectItem> retornoClientes() {
		List<SelectItem> retorno = new ArrayList<SelectItem>();
		List<ClienteBean> clientes = new ArrayList<ClienteBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("select c from ClienteBean c order by c.nome");

		Query q = returnEntityManager().createQuery(sql.toString(), ClienteBean.class);

		clientes = q.getResultList();

		for (ClienteBean cli : clientes) {
			retorno.add(new SelectItem(cli.getCodigo(), cli.getNome()));
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<ClienteBean> pesquisarClientesPorNome(String nomeClientePesquisa) {
		List<ClienteBean> clientes = new ArrayList<ClienteBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("select c from ClienteBean c where lower(c.nome) like lower('%" + nomeClientePesquisa
				+ "%') or lower(c.apelido) like lower('%" + nomeClientePesquisa + "%') order by c.nome");

		Query q = returnEntityManager().createQuery(sql.toString(), ClienteBean.class);

		clientes = q.getResultList();
		return clientes;
	}

	@SuppressWarnings("unchecked")
	public static List<CompraClienteBean> pesquisarComprasPorCliente(Object codigoCliente) {
		List<CompraClienteBean> compras = new ArrayList<CompraClienteBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("select cc from CompraClienteBean cc");
		sql.append(" where cc.codigoCliente = " + codigoCliente);
		sql.append(" order by cc.dataCompra desc");

		Query q = returnEntityManager().createQuery(sql.toString(), CompraClienteBean.class);

		compras = q.getResultList();
		return compras;
	}

	@SuppressWarnings("unchecked")
	public static List<PagamentosClienteBean> pesquisarPagamentosPorCliente(Integer codigoCliente) {
		List<PagamentosClienteBean> pagamentos = new ArrayList<PagamentosClienteBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("select pc from PagamentosClienteBean pc");
		sql.append(" where pc.cliente = " + codigoCliente);
		sql.append(" order by pc.data desc");

		Query q = returnEntityManager().createQuery(sql.toString(), PagamentosClienteBean.class);

		pagamentos = q.getResultList();
		return pagamentos;
	}

	@SuppressWarnings("unchecked")
	public static List<ClienteBean> pesquisarTodosClientes() {
		List<ClienteBean> clientes = new ArrayList<ClienteBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("select c from ClienteBean c order by c.nome");

		Query q = returnEntityManager().createQuery(sql.toString(), ClienteBean.class);

		clientes = q.getResultList();
		return clientes;
	}

	public static UsuarioBean buscarUsuario(String usuario) {
		UsuarioBean usuarioRetorno = new UsuarioBean();

		StringBuilder sql = new StringBuilder();

		sql.append("select u from UsuarioBean u where ");
		sql.append("u.login = '" + usuario + "'");

		Query q = returnEntityManager().createQuery(sql.toString(), UsuarioBean.class);

		usuarioRetorno = (UsuarioBean) q.getSingleResult();

		return usuarioRetorno;
	}

	@SuppressWarnings("unchecked")
	public static List<ChequeBean> pesquisarChequesPorData(Date dataDe, Date dataAte) {
		List<ChequeBean> retorno = new ArrayList<ChequeBean>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CHEQUE FROM ChequeBean CHEQUE");
		sql.append(" WHERE CHEQUE.dataPagamento BETWEEN '");
		sql.append(new SimpleDateFormat("yyyy-MM-dd").format(dataDe) + "' AND '");
		sql.append(new SimpleDateFormat("yyyy-MM-dd").format(dataAte) + "'");

		Query query = returnEntityManager().createQuery(sql.toString(), ChequeBean.class);

		retorno = query.getResultList();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static List<CompradorConjuntoBean> retornaCompradoresConjuntos(List<ChequeBean> listaCheques) {
		List<CompradorConjuntoBean> retorno = new ArrayList<CompradorConjuntoBean>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CCB FROM CompradorConjuntoBean CCB ");
		sql.append(" WHERE CCB.numCheque IN (");
		for (ChequeBean cb : listaCheques) {
			sql.append(cb.getNumCheque());
			sql.append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		
		Query query = returnEntityManager().createQuery(sql.toString(), CompradorConjuntoBean.class);
		
		retorno = query.getResultList();

		return retorno;
	}

}
