package easycourse;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;



@Path("/{id_uni}")
public class EasyCourse {
	
	private static HashMap<String,Corso> mapCorsi;
	private static HashMap<String,Docente> mapDocenti;
	private static HashMap<Integer,Aula> mapAule;
	
	
 	
	public EasyCourse() {
		if (mapCorsi == null) {
			
			mapCorsi = new HashMap<String,Corso>();
			mapDocenti = new HashMap<String,Docente>();
			mapAule = new HashMap<Integer,Aula>();
			
			Aula a0 = new Aula(0, "T25");
			Corso c = new Corso("1", "Ingegneria del software", new Docente("Pasquale", "Foggia", "1"), 1, 1,new Slot("Luned'", 7, 10,a0));
			HashMap<String, Slot> h = new HashMap<String, Slot>();
			Aula a = new Aula(1, "M");
			h.put("slot1",new Slot("Gioved�", 9, 13,a));
			Aula a1 = new Aula(2, "C");
			h.put("slot2",new Slot("Marted�", 9, 13,a1));
			
			c.setMappaOrario(h);
			mapCorsi.put(c.getCod(), c);
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
	public Corso getCorso(@PathParam("id_corso") String id_corso){
		return this.mapCorsi.get(id_corso);
	}
	
	@GET 
	@Path("/corsi")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String,Corso> getCorsi(@Context UriInfo ui){
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
		if(queryParams.containsKey("idDocente") && queryParams.containsKey("anno") && queryParams.containsKey("semestre")){
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while (i.hasNext()) {
				Corso c = i.next();
				if (c.getDocente().getMatricola().equals(queryParams.getFirst("idDocente")) && c.getAnno()==Integer.parseInt(queryParams.getFirst("anno")) && c.getSemestre()==Integer.parseInt(queryParams.getFirst("semestre")))
					mapC.put(c.getCod(), c);
			}
			return mapC;
		}
		if(queryParams.containsKey("idDocente")){
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while (i.hasNext()) {
				Corso c = i.next();
				if (c.getDocente().getMatricola().equals(queryParams.getFirst("idDocente")))
					mapC.put(c.getCod(), c);
			}
			return mapC;
		}
		if(queryParams.containsKey("anno")){
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while (i.hasNext()) {
				Corso c = i.next();
				if (c.getAnno()==Integer.parseInt(queryParams.getFirst("anno")))
					mapC.put(c.getCod(), c);
			}
			return mapC;
		}
		if(queryParams.containsKey("semestre")){
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while (i.hasNext()) {
				Corso c = i.next();
				if (c.getSemestre()==Integer.parseInt(queryParams.getFirst("semestre")))
					mapC.put(c.getCod(), c);
			}
			return mapC;
		}
		else if (!queryParams.isEmpty()){
			return null;
		}
		
		if(queryParams.containsKey("giorno")){
			
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while(i.hasNext()) {
				Corso c = i.next();
				if(c.getMappaOrario().keySet().contains(queryParams.getFirst("giorno")))
						mapC.put(c.getCod(),c);
			}
			return mapC;
			
		}
		
		return mapCorsi;
	}
	
//	@GET 
//	@Path("/corsi")
//	@Produces(MediaType.APPLICATION_JSON)
//	public HashMap<String,Corso> getCorsiByDocente(@QueryParam("idDocente") String idDocente){
//		if (idDocente != null) {
//			Iterator<Corso> i = mapCorsi.values().iterator();
//			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
//			while (i.hasNext()) {
//				Corso c = i.next();
//				if (c.getDocente().getMatricola().equals(idDocente))
//					mapC.put(c.getCod(), c);
//			}
//			return mapC;
//		}
//		
//		return mapCorsi;
//	}
	
	
	@POST
	@Path("/corsi/{id_corso}")
	public Response test(@PathParam("id_corso") String idCorso, @FormParam("nome") String nome, @FormParam("matDocente") String matDocente,@FormParam("semestre") int semestre,@FormParam("anno") int anno,@FormParam("idAula") Integer idAula,@FormParam("giorno") String giorno,@FormParam("oraInizio") Integer oraInizio,@FormParam("oraFine") Integer oraFine) {
		String output = "Corso gia presente";
		if(!mapCorsi.containsKey(idCorso)){
			Docente doc = this.mapDocenti.get(matDocente);
			Aula a = this.mapAule.get(idAula);
			Slot s = new Slot(giorno,oraInizio,oraFine,a);
			mapCorsi.put(idCorso,new Corso(idCorso,nome, doc,semestre,anno,s));
			if(!mapDocenti.containsKey(matDocente)){
				
				mapDocenti.put(doc.getMatricola(), doc);
			}
			output = "Corso " + idCorso + " inserito";
		}
		return Response.status(200).entity(output).build();
	}
	
	@PUT
	@Path("/corsi/{id_corso}")
	public Response updateCorso(@PathParam("id_corso") String idCorso,@FormParam("idAula") Integer idAula,@FormParam("giorno") String giorno,@FormParam("oraInizio") Integer oraInizio,@FormParam("oraFine") Integer oraFine) {
	
		
		Aula a = this.mapAule.get(idAula);
		Slot s = new Slot(giorno,oraInizio,oraFine,a);
		Corso c = this.getCorso(idCorso);
		c.getMappaOrario().put(s.getGiorno(), s);
		String output = "Corso " + idCorso + " aggiornato";
		
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
	
	
	@POST
	@Path("/docenti/{id_docente}")
	public Response setDocente(@PathParam("id_docente") String idDocente, @FormParam("nome") String nome,@FormParam("cognome") String cognome) {
		String output = "Docente gia presente";
		if(!mapDocenti.containsKey(idDocente)){
			Docente doc = new Docente(nome,cognome,idDocente);
			mapDocenti.put(idDocente, doc);
			
			output = "Docente " + idDocente + " inserito";
		}
		
		return Response.status(200).entity(output).build();
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
	public Aula getAula(@PathParam("id_aula") Integer id_aula){
		return this.mapAule.get(id_aula);
	}
	
	
	@POST
	@Path("/aule/{id_aula}")
	public Response setAula(@PathParam("id_aula") Integer idAula, @FormParam("nome") String nome) {
		String output = "Aula gia presente";
		if(!mapAule.containsKey(idAula)){
			
			Aula a = new Aula(idAula,nome);
			mapAule.put(idAula, a);
			
			output = "Aula " + idAula + " inserito";
		}
		
		return Response.status(200).entity(output).build();
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
