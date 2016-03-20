package com.horlogernet.dao.client;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.horlogernet.dao.JpaDao;
import com.horlogernet.entity.Client;


public class JpaClientDao extends JpaDao<Client, Long> implements ClientDao
{

	public JpaClientDao()
	{
		super(Client.class);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Client> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);

		Root<Client> root = criteriaQuery.from(Client.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Client> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
