package model;

public class Element {
	private double temperature, specificHeat, heatEffectFraction;

	public double getTemperature() {
		return temperature;
	}

	public double getSpecificHeat() {
		return specificHeat;
	}

	public double getHeatEffectFraction() {
		return heatEffectFraction;
	}

	public void setHeatEffectFraction(double heatEffectFraction) {
		this.heatEffectFraction = heatEffectFraction;
	}

	public Element(double temperature, double specificHeat) {
		this.temperature = temperature;
		this.specificHeat = specificHeat;
		heatEffectFraction = 0.0;
	}
}
