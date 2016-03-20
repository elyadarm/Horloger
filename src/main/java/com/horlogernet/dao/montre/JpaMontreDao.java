package com.horlogernet.dao.montre;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.horlogernet.dao.JpaDao;
import com.horlogernet.entity.Montre;


public class JpaMontreDao extends JpaDao<Montre, Long> implements MontreDao
{

	public JpaMontreDao()
	{
		super(Montre.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Montre> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Montre> criteriaQuery = builder.createQuery(Montre.class);

		Root<Montre> root = criteriaQuery.from(Montre.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Montre> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
