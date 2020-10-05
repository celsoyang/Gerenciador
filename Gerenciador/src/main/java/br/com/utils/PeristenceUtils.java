package br.com.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.bean.CaixaDiarioBean;

public class PeristenceUtils {

	private static EntityManager entitiManager;

	public PeristenceUtils() {
	}

	@SuppressWarnings("unchecked")
	public static List<CaixaDiarioBean> retornaCaixaDiario() {
		List<CaixaDiarioBean> retorno = new ArrayList<CaixaDiarioBean>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT CD FROM CaixaDiarioBean CD ORDER BY CD.data");

		Query query = returnEntityManager().createQuery(sql.toString(), CaixaDiarioBean.class);

		retorno = (List<CaixaDiarioBean>) query.getResultList();

		return retorno;
	}

	private static EntityManager returnEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("Gerenciador");
		entitiManager = factory.createEntityManager();

		return entitiManager;
	}

	public static EntityManager getEntitiManager() {
		return entitiManager;
	}

	public static void setEntitiManager(EntityManager entitiManager) {
		PeristenceUtils.entitiManager = entitiManager;
	}

	@SuppressWarnings("unchecked")
	public static List<CaixaDiarioBean> pesquisarPorMeses(List<String> mesesSelecionados) {
		List<CaixaDiarioBean> retorno = new ArrayList<CaixaDiarioBean>();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT cd FROM CaixaDiarioBean cd");
		sql.append(" WHERE DATE_PART('MONTH', cd.data) IN(");
		for (String mes : mesesSelecionados) {
			sql.append("'" + mes + "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");

		Query query = returnEntityManager().createQuery(sql.toString(), CaixaDiarioBean.class);

		retorno = query.getResultList();

		return retorno;
	}

}
