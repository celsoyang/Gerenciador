package br.com.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.bean.CaixaDiarioBean;

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
		getEntitiManager().getTransaction().begin();
	}

	public static void commitTransaction() {
		getEntitiManager().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		getEntitiManager().getTransaction().rollback();
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

	public static String salvar(CaixaDiarioBean objeto) {
		try {
			if (!getEntitiManager().getTransaction().isActive()) {
				getEntitiManager().getTransaction().begin();
			}
			getEntitiManager().persist(objeto);
			getEntitiManager().getTransaction().commit();
			return StringUtils.MSG_SALVO_SUCESSO;
		} catch (Exception e) {
			getEntitiManager().getTransaction().rollback();
			return StringUtils.MSG_PROBLEMA_SALVAR;
		}
	}

}
