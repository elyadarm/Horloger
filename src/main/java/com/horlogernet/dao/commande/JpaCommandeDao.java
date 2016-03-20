package com.horlogernet.dao.commande;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.horlogernet.dao.JpaDao;
import com.horlogernet.entity.Commande;
import com.horlogernet.entity.NewsEntry;

public class JpaCommandeDao extends JpaDao<Commande, Long> implements CommandeDao{

	public JpaCommandeDao() {
		super(Commande.class);
	}
	@Override
	@Transactional(readOnly = true)
	public List<Commande> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Commande> criteriaQuery = builder.createQuery(Commande.class);

		Root<Commande> root = criteriaQuery.from(Commande.class);
		criteriaQuery.orderBy(builder.desc(root.get("creationDate")));

		TypedQuery<Commande> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
