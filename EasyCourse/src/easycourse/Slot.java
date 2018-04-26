package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Slot {
	private String giorno;
	private String oraInizio;
	private String oraFine;

}
