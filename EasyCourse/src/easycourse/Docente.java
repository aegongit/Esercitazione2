package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Docente {
	private String nome, cognome;
	private String matricola;

}
