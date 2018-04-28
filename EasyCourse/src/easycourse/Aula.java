package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Aula {
	private int idAula;
	private String nome ;
	
	public Aula(int idAula, String nome) {
		this.idAula = idAula;
		this.nome = nome;
	}
	

}
