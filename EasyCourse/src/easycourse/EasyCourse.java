package easycourse;

import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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
			
			Aula a0 = new Aula(0, "M");
			Corso c = new Corso("1", "Ingegneria del software", new Docente("Pasquale", "Foggia", "1"), 1, 1,new Slot("Lunedi", 7, 10,a0));
			HashMap<String, Slot> h = new HashMap<String, Slot>();
			Aula a1 = new Aula(1, "137");
			Corso c1 = new Corso("2", "Mobile Programming", new Docente("Alessia", "Saggese", "2"), 2, 2,new Slot("Martedi", 10, 12,a1));
			Aula a2 = new Aula(3, "C");
			h.put("slot1",new Slot("Giovedi", 9, 13,a2));
			Aula a3 = new Aula(4, "H");
			h.put("slot2",new Slot("Martedi", 11, 12,a3));
			
			c.setMappaOrario(h);
			mapCorsi.put(c.getCod(), c);
			mapCorsi.put(c1.getCod(), c1);
			mapDocenti.put(c.getDocente().getMatricola(), c.getDocente());
			mapDocenti.put(c1.getDocente().getMatricola(), c1.getDocente());
			mapAule.put(a2.getIdAula(), a2);
			mapAule.put(a3.getIdAula(), a3);
		}
	}
	

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
		System.out.println("ok");
		System.out.println(queryParams.containsKey("giorno"));
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
		
		if(queryParams.containsKey("giorno")){
			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while(i.hasNext()) {
				Corso c = i.next();
				Iterator<Slot> i2 = c.getMappaOrario().values().iterator();
				while(i2.hasNext()) {
					Slot s = i2.next();
					if(s.getGiorno().equals(queryParams.getFirst("giorno"))){
						mapC.put(c.getCod(),c);
					}
				}

			}
			return mapC;
			
		}
		
		if(queryParams.containsKey("da") && queryParams.containsKey("a")){

			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<String, Corso> mapC = new HashMap<String, Corso>();
			while(i.hasNext()) {
				Corso c = i.next();
				Iterator<Slot> i2 = c.getMappaOrario().values().iterator();
				while(i2.hasNext()) {
					Slot s = i2.next();
					if(s.getOraInizio() >= Integer.parseInt(queryParams.getFirst("da")) && s.getOraFine() <= Integer.parseInt(queryParams.getFirst("a"))){
						mapC.put(c.getCod(),c);
					}
				}

			}
			return mapC;

		}
		
		return mapCorsi;
	}
	

	
	
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
	public HashMap<Integer,Aula> getAule(@Context UriInfo ui){
		
		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

		if(queryParams.containsKey("da") && queryParams.containsKey("a")){

			Iterator<Corso> i = mapCorsi.values().iterator();
			HashMap<Integer, Aula> mapA = new HashMap<Integer, Aula>();
			while(i.hasNext()) {
				Corso c = i.next();
				Iterator<Slot> i2 = c.getMappaOrario().values().iterator();
				while(i2.hasNext()) {
					Slot s = i2.next();
					if(s.getOraInizio() >= Integer.parseInt(queryParams.getFirst("da")) && s.getOraInizio() <= Integer.parseInt(queryParams.getFirst("a"))){
						mapA.put(s.getAula().getIdAula(),s.getAula());
					}
				}

			}
			return mapA;

		}
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
	
	
	
	
}
