package easycourse;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Slot {
	private String giorno;
	private int oraInizio;
	private int oraFine; //mettere qui l'aula
	private Aula aula;
	
	public Slot(String giorno,int oraInizio, int oraFine, Aula a) {
		this.setGiorno(giorno); 
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.setAula(a);
	}
	
	
	public String getGiorno() {
		return giorno;
	}
	public void setGiorno(String giorno) {
		this.giorno = Validation.validate(giorno);
	}
	public int getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(int oraInizio) {
		this.oraInizio = oraInizio;
	}
	public int getOraFine() {
		return oraFine;
	}
	public void setOraFine(int oraFine) {
		this.oraFine = oraFine;
	}

	
	public static boolean isGreater(int a,int b ) {
		if(a>b)
			return true;
		return false;
	}
	
	public static boolean isSmaller(int a, int b) {
		return !Slot.isGreater(a, b);
	}


	public Aula getAula() {
		return aula;
	}


	public void setAula(Aula aula) {
		this.aula = aula;
	}
}
