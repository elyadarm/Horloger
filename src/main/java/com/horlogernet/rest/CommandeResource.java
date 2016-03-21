package com.horlogernet.rest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.horlogernet.dao.commande.CommandeDao;
import com.horlogernet.entity.Commande;
import com.horlogernet.entity.Statut;
import com.horlogernet.views.JsonViews;


@Component
@Path("/commande")
public class CommandeResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommandeDao commandeDao;

	@Autowired
	private ObjectMapper mapper;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Commande> allEntries = this.commandeDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Commande read(@PathParam("id") Long id)
	{
		this.logger.info("read(id)");

		Commande commande = this.commandeDao.find(id);
		if (commande == null) {
			throw new WebApplicationException(404);
		}
		return commande;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public Commande read(@FormParam("number") String number)
	{

		List<Commande> commandes = this.commandeDao.search(number);
		if (commandes != null && commandes.size()>0) {
			return commandes.get(0);
		}
		return null;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Commande create(Commande commande)
	{
		this.logger.info("create(): " + commande);
		commande.setStatut(Statut.enCours);
		commande.setCreationDate(new Date());
		return this.commandeDao.save(commande);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Commande update(@PathParam("id") Long id, Commande commande)
	{
		this.logger.info("update(): " + commande);

		if(commande.getFacture() != null){
			commande.setStatut(Statut.finaliser);
		}
		return this.commandeDao.save(commande);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id)
	{
		this.logger.info("delete(id)");

		this.commandeDao.delete(id);
	}


	private boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.toString().equals("admin")) {
				return true;
			}
		}

		return false;
	}

}