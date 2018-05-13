package Beans;

public enum Diplome {
	BAC5("BAC5"),BAC8("BAC8");
	String text;

	private Diplome(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
