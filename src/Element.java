public class Element {
	private double temperature, specificHeat;

	public double getTemperature() {
		return temperature;
	}

	public double getSpecificHeat() {
		return specificHeat;
	}

	public Element(double temperature, double specificHeat) {
		this.temperature = temperature;
		this.specificHeat = specificHeat;
	}
}
