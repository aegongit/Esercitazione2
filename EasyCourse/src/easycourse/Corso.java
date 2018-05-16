package easycourse;


import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Corso {
	private String nome;
	private String cod;
	private Docente docente;
	private HashMap<String,Slot> mappaOrario;
	private int semestre, anno;
	
	public Corso(String cod, String nome) {
		this.nome = nome;
		this.cod = cod;
		this.mappaOrario = new HashMap<String,Slot>();
	}
	
	public Corso(String cod, String nome,Docente d,int semestre, int anno) {
		this.setNome(nome); 
		this.setCod(cod);
		this.mappaOrario = new HashMap<String,Slot>();
		this.docente =d;
		this.anno = anno;
		this.semestre = semestre;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = Validation.validate(nome);
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = Validation.validate(cod);
	}
	public Docente getDocente() {
		return docente;
	}
	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	public HashMap<String,Slot> getMappaOrario() {
		return mappaOrario;
	}
	public void setMappaOrario(HashMap<String,Slot> mappaOrario) {
		this.mappaOrario = mappaOrario;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	
	
	
	
}
