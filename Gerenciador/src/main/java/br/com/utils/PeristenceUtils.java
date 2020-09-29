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

		sql.append("select cd from CaixaDiarioBean cd");

		Query query = retornaEntityManager().createQuery(sql.toString(), CaixaDiarioBean.class);

		retorno = (List<CaixaDiarioBean>) query.getResultList();

		return retorno;
	}
			
	private static EntityManager retornaEntityManager() {
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

}
