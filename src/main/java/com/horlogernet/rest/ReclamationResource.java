package com.horlogernet.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.horlogernet.dao.reclamation.ReclamationDao;
import com.horlogernet.entity.Reclamation;
import com.horlogernet.views.JsonViews;


@Component
@Path("/reclamation")
public class ReclamationResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReclamationDao reclamationDao;

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
		List<Reclamation> allEntries = this.reclamationDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Reclamation read(@PathParam("id") Long id)
	{
		this.logger.info("read(id)");

		Reclamation reclamation = this.reclamationDao.find(id);
		if (reclamation == null) {
			throw new WebApplicationException(404);
		}
		return reclamation;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Reclamation create(Reclamation reclamation)
	{
		this.logger.info("create(): " + reclamation);

		return this.reclamationDao.save(reclamation);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Reclamation update(@PathParam("id") Long id, Reclamation reclamation)
	{
		this.logger.info("update(): " + reclamation);

		return this.reclamationDao.save(reclamation);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id)
	{
		this.logger.info("delete(id)");

		this.reclamationDao.delete(id);
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