package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Docente {
	private String nome, cognome;
	private String matricola;
	
	public Docente(String nome, String cognome,String mat) {
		this.nome = nome;
		this.cognome = cognome;
		this.matricola = mat;
	}

}
