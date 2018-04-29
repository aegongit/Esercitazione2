package easycourse;


import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Corso {
	private String nome;
	private String cod;
	private Docente docente;
	private HashMap<Slot,Aula> mappaOrario;
	private int semestre, anno;
	
	public Corso(String cod, String nome) {
		this.nome = nome;
		this.cod = cod;
		this.mappaOrario = new HashMap<Slot,Aula>();
	}
	
	public Corso(String cod, String nome,Docente d,int semestre, int anno) {
		this.nome = nome;
		this.cod = cod;
		this.mappaOrario = new HashMap<Slot,Aula>();
		this.docente =d;
		this.anno = anno;
		this.semestre = semestre;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public Docente getDocente() {
		return docente;
	}
	public void setDocente(Docente docente) {
		this.docente = docente;
	}
	public HashMap<Slot,Aula> getMappaOrario() {
		return mappaOrario;
	}
	public void setMappaOrario(HashMap<Slot,Aula> mappaOrario) {
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
