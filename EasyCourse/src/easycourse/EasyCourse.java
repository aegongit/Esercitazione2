package easycourse;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	@Path("/{idCorso}")
	@Produces(MediaType.APPLICATION_JSON)
	public Corso getCorso(@PathParam("idCorso") String idCorso) {
		Iterator<Corso> i = listCorsi.iterator();
		while(i.hasNext()) {
			Corso c =  i.next();
			if(c.getCod().equals(idCorso))
				return c;
		}
		return null;
			
	}
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Corso> getCorsi(@QueryParam("da") int da, @QueryParam("a") int a){
		
		if(da == 0 && a==0)
			return listCorsi;
		
		Iterator<Corso> i = EasyCourse.listCorsi.iterator();
		ArrayList<Corso> list = new ArrayList<Corso>();
		
		while(i.hasNext()) {
			Corso c = i.next();
			Iterator<Slot> i1 = c.getMappaOrario().keySet().iterator();
				while(i1.hasNext()) {
					Slot s = i1.next();
					if(Slot.isGreater(s.getOraInizio(),da) && Slot.isSmaller(s.getOraFine(),a))
						list.add(c);
				}
		}
		
		return list;
	}
	
	
	@GET
	@Path("/docenti/{id_docente}/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Corso> getCorsi(@PathParam("id_docente") String idDocente) {
		Iterator<Corso> i = listCorsi.iterator();
		ArrayList<Corso> list = new ArrayList<Corso>();
		while(i.hasNext()) {
			Corso c = i.next();
			if(c.getDocente().getMatricola().equals(idDocente))
				list.add(c);
			
		}
		return list;
	}
	
	
	
	@POST
	@Path("/{corso}/")
	//@Consumes(MediaType.TEXT_PLAIN)
	public Response addCorso(@PathParam("corso") String idCorso) {
		listCorsi.add(new Corso(idCorso,idCorso));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/test")
	//@Consumes(MediaType.)
	public Response test(@FormParam("cod") String idCorso, @FormParam("nome") String nome, @FormParam("nomeDocente") String nomeDocente, @FormParam("cognomeDocente") String cognomeDocente,@FormParam("matDocente") String matDocente,@FormParam("semestre") int semestre,@FormParam("anno") int anno) {
		listCorsi.add(new Corso(idCorso,nome,new Docente(nomeDocente,cognomeDocente,matDocente),semestre,anno));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
	
	
}
