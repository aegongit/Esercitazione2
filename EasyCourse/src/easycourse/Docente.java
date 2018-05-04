package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Docente {
	private String nome, cognome;
	private String matricola;
	
	public Docente(String nome, String cognome,String mat) {
		this.setNome(nome);
		this.setCognome(cognome);
		this.setMatricola(mat);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = Validation.validate(nome);
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = Validation.validate(cognome);
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = Validation.validate(matricola);
	}

}
