package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Aula {
	private int idAula;
	private String nome ;
	
	public Aula(int idAula, String nome) {
		this.setIdAula(idAula);
		this.setNome(nome);
	}

	public int getIdAula() {
		return idAula;
	}

	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
