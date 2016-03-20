package com.horlogernet.dao.facture;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.horlogernet.dao.JpaDao;
import com.horlogernet.entity.Facture;


public class JpaFactureDao extends JpaDao<Facture, Long> implements FactureDao
{

	public JpaFactureDao()
	{
		super(Facture.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Facture> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Facture> criteriaQuery = builder.createQuery(Facture.class);

		Root<Facture> root = criteriaQuery.from(Facture.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Facture> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
