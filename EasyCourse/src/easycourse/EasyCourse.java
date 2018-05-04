package easycourse;

import java.util.HashMap;
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



@Path("/{id_uni}")
public class EasyCourse {

	
	private static HashMap<String,Corso> listCorsi;
	private static HashMap<String,Docente> mapDocenti;
	private static HashMap<Integer,Aula> mapAule;
	
	
	
 	
	public EasyCourse() {
		if (listCorsi == null) {
			
			listCorsi = new HashMap<String,Corso>();
			mapDocenti = new HashMap<String,Docente>();
			mapAule = new HashMap<Integer,Aula>();
			
			Corso c = new Corso("1", "Ingegneria del software", new Docente("Pasquale", "Foggia", "1"), 1, 1);
			HashMap<Slot, Aula> h = new HashMap<Slot, Aula>();
			Aula a = new Aula(1, "M");
			h.put(new Slot("Giovedì", 9, 13), a);
			
			c.setMappaOrario(h);
			listCorsi.put(c.getCod(), c);
			mapDocenti.put(c.getDocente().getMatricola(), c.getDocente());
			mapAule.put(a.getIdAula(), a);
		}
	}
	

	/*
	
	@GET 
	@Path("/corsi/{idCorso}")
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
	*/
	
	
	/*@GET
	@Path("/corsi")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Corso> getCorsi(){
		return this.listCorsi;
	}*/
	
	@GET
	@Path("/corsi/{id_corso}")
	@Produces(MediaType.APPLICATION_JSON)
	public Corso getCorsi(@PathParam("id_corso") String id_corso){
		return this.listCorsi.get(id_corso);
	}
	
	@GET 
	@Path("/corsi")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Corso> getCorsiByDocente(@QueryParam("idDocente") String idDocente){
		if (idDocente != null) {
			Iterator<Corso> i = listCorsi.values().iterator();
			HashMap<String, Corso> list = new HashMap<String, Corso>();
			while (i.hasNext()) {
				Corso c = i.next();
				if (c.getDocente().getMatricola().equals(idDocente))
					list.put(c.getCod(), c);

			}
			return list;
		}
		
		return listCorsi;
	}
	
	
	@POST
	@Path("/corsi/{id_corso}")
	public Response test(@FormParam("cod") String idCorso, @FormParam("nome") String nome, @FormParam("nomeDocente") String nomeDocente, @FormParam("cognomeDocente") String cognomeDocente,@FormParam("matDocente") String matDocente,@FormParam("semestre") int semestre,@FormParam("anno") int anno) {
		listCorsi.put(idCorso,new Corso(idCorso,nome,new Docente(nomeDocente,cognomeDocente,matDocente),semestre,anno));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
	
	
	@GET
	@Path("/docenti")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Docente> getDocenti(){
		return this.mapDocenti;
	}
	@GET
	@Path("/docenti/{id_docente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Docente getDocente(@PathParam("id_docente") String id_docente){
		return this.mapDocenti.get(id_docente);
	}
	
	@GET
	@Path("/aule")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<Integer,Aula> getAule(){
		return this.mapAule;
	}
	
	@GET
	@Path("/aule/{id_aula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Aula getAula(@PathParam("id_aula") String id_aula){
		return this.mapAule.get(id_aula);
	}
	
	/*
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
	@Path("/corsi/{corso}/")
	//@Consumes(MediaType.TEXT_PLAIN)
	public Response addCorso(@PathParam("corso") String idCorso) {
		listCorsi.add(new Corso(idCorso,idCorso));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/corsi/test")
	//@Consumes(MediaType.)
	public Response test(@FormParam("cod") String idCorso, @FormParam("nome") String nome, @FormParam("nomeDocente") String nomeDocente, @FormParam("cognomeDocente") String cognomeDocente,@FormParam("matDocente") String matDocente,@FormParam("semestre") int semestre,@FormParam("anno") int anno) {
		listCorsi.add(new Corso(idCorso,nome,new Docente(nomeDocente,cognomeDocente,matDocente),semestre,anno));
		String output = "POST REQUEST: " + idCorso;
		return Response.status(200).entity(output).build();
		
	}
	
	
	@GET
	@Path("/aule")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Aula> getAula(){
		Iterator<Corso> i = listCorsi.iterator();
		Set<Aula> list = new LinkedHashSet<Aula>();
		while(i.hasNext()) {
			Corso c = i.next();
			list.addAll(c.getMappaOrario().values());
			
			
		}
		return list;
		
	}*/
	
	
}
