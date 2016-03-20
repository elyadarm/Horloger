package com.horlogernet.dao.reclamation;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.horlogernet.dao.JpaDao;
import com.horlogernet.entity.Reclamation;


public class JpaReclamationDao extends JpaDao<Reclamation, Long> implements ReclamationDao
{

	public JpaReclamationDao()
	{
		super(Reclamation.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Reclamation> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Reclamation> criteriaQuery = builder.createQuery(Reclamation.class);

		Root<Reclamation> root = criteriaQuery.from(Reclamation.class);
		criteriaQuery.orderBy(builder.desc(root.get("dateCreation")));

		TypedQuery<Reclamation> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
