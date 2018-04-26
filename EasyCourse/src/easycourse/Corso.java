package easycourse;


import java.util.HashMap;

public class Corso {
	private String nome;
	private String cod;
	private Docente docente;
	private HashMap<Slot,Aula> mappaOrario;
	private int semestre, anno;
	
	public Corso(String cod, String nome) {
		this.nome = nome;
		this.cod = cod;
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
	
	
	
}
