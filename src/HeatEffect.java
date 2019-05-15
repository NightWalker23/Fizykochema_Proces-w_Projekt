public class HeatEffect {
	private double tempSolidus;
	private double tempLiquidus;
	private double heatEffect;
	private EffectType effectType;

	public HeatEffect(double tempSolidus, double tempLiquidus, double heatEffect, EffectType effectType) {
		this.tempSolidus = tempSolidus;
		this.tempLiquidus = tempLiquidus;
		this.heatEffect = heatEffect;
		this.effectType = effectType;
	}

	public double getTempSolidus() {
		return tempSolidus;
	}

	public double getTempLiquidus() {
		return tempLiquidus;
	}

	public double getHeatEffect() {
		return heatEffect;
	}

	public EffectType getEffectType() {
		return effectType;
	}
}
