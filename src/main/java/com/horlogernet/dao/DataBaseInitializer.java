package com.horlogernet.dao;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.horlogernet.dao.client.ClientDao;
import com.horlogernet.dao.commande.CommandeDao;
import com.horlogernet.dao.facture.FactureDao;
import com.horlogernet.dao.montre.MontreDao;
import com.horlogernet.dao.newsentry.NewsEntryDao;
import com.horlogernet.dao.reclamation.ReclamationDao;
import com.horlogernet.dao.user.UserDao;
import com.horlogernet.entity.Commande;
import com.horlogernet.entity.Montre;
import com.horlogernet.entity.NewsEntry;
import com.horlogernet.entity.Statut;
import com.horlogernet.entity.User;



public class DataBaseInitializer
{

	private NewsEntryDao newsEntryDao;
	

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;
	
	private CommandeDao commandeDao;
	
	private MontreDao montreDao;
	
//	private ClientDao clientDao;
//	private ReclamationDao reclamationDao;
//	private FactureDao factureDao;


	protected DataBaseInitializer()
	{

	}


	public DataBaseInitializer(UserDao userDao,
			NewsEntryDao newsEntryDao,
			PasswordEncoder passwordEncoder,
			CommandeDao commandeDao,
			MontreDao montreDao			
			)
	{
		this.userDao = userDao;
		this.newsEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
		this.commandeDao = commandeDao;
		this.montreDao = montreDao;
	}


	public void initDataBase()
	{
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		this.userDao.save(adminUser);

		long timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24;
		for (int i = 0; i < 10; i++) {
			NewsEntry newsEntry = new NewsEntry();
			newsEntry.setContent("This is example content " + i);
			newsEntry.setDate(new Date(timestamp));
			this.newsEntryDao.save(newsEntry);
			timestamp += 1000 * 60 * 60;
		}
		Montre montre0 = new Montre(Statut.enAttente,"Montre Fossil Men Dress Fs4778");
		Montre montre1 = new Montre(Statut.enAttente,"Montre Chronographe Luigi Ar1811");
		Montre montre2 = new Montre(Statut.enAttente,"Montre Chronographe Overflow Dz4341");
		Montre montre3 = new Montre(Statut.finaliser,"Montre Lotus Multifonctions Acier Ronde Fond Noir Et Argent Bracelet Cuir Noir 10atm");
		montre0 = this.montreDao.save(montre0);
		montre1 = this.montreDao.save(montre1);
		montre2 = this.montreDao.save(montre2);
		montre3 = this.montreDao.save(montre3);
		Commande commande1 =new Commande();
		Commande commande2 = new Commande();
		commande1.setNumber("123A");
		commande2.setNumber("185T");
		commande1.addMontre(montre0);
//		montre0.setCommande(commande1
		commande1.addMontre(montre1);
		commande1.addMontre(montre2);
		commande2.addMontre(montre3);
		commande1.setStatut(Statut.enCours);
		commande2.setStatut(Statut.finaliser);
		commande1.setCreationDate(new Date());
		commande1 = this.commandeDao.save(commande1);
		commande2 = this.commandeDao.save(commande2);
		
		
		
	}

}