package easycourse;

public final class Validation {

	public  static final  String validate(String str) {
		return str.replaceAll("\\<.*?>", "");
	}
}
