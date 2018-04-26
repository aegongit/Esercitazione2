package easycourse;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/corsi")
public class EasyCourse {

	private static  ArrayList<Corso> listCorsi;
	
	public EasyCourse() {
		if(listCorsi == null)
			listCorsi = new ArrayList<Corso>();
	}
	
	@GET 
	@Produces(MediaType.TEXT_PLAIN)
	public String getCorsi() {
		if (!listCorsi.isEmpty()) {
			Iterator<Corso> i = listCorsi.iterator();
			String result = "";
			while (i.hasNext()) {
				result = result + "\n" + i.next().getNome();
			}
			return result;
		}
		return "Nessun corso";
	}
	
	
	@GET 
	@Path("/{corso}")
	@Produces(MediaType.APPLICATION_JSON)
	public Corso getCorso(@PathParam("corso") String idCorso) {
		Iterator<Corso> i = listCorsi.iterator();
		while(i.hasNext()) {
			Corso c =  i.next();
			if(c.getCod().equals(idCorso))
				return c;
		}
		return null;
			
	}
	
	
	@POST
	@Path("/{corso}/")
	//@Consumes(MediaType.TEXT_PLAIN)
	public Response addCorso(@PathParam("corso") String idCorso) {
		listCorsi.add(new Corso(idCorso,idCorso));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
}
