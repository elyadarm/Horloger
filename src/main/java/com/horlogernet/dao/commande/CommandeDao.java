package com.horlogernet.dao.commande;

import java.util.List;

import com.horlogernet.dao.Dao;
import com.horlogernet.entity.Commande;

public interface CommandeDao extends Dao<Commande,Long>{

	public List<Commande> search(String number);
}
