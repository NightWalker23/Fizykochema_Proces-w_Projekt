package model;

import static model.EffectType.*;

public class HeatEffect {
	public double tempS;
	public double tempE;
	public double heatEffect;
	public EffectType effectType;

	public HeatEffect(double tempS, double tempE, double heatEffect, EffectType effectType) {
		this.tempS = tempS;
		this.tempE = tempE;
		this.heatEffect = heatEffect;
		this.effectType = effectType;
	}

	public void setTempS(double tempS) {
		this.tempS = tempS;
	}

	public void setTempE(double tempE) {
		this.tempE = tempE;
	}

	public void setHeatEffect(double heatEffect) {
		this.heatEffect = heatEffect;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

	public double getTempS() {
		return tempS;
	}

	public double getTempE() {
		return tempE;
	}

	public double getHeatEffect() {
		return heatEffect;
	}

	public EffectType getEffectType() {
		return effectType;
	}

	//y = const
	public double function_AVERAGE(double temperature){
		double range = tempE - tempS;
		return heatEffect / range;
	}

	//y = x
	private double function_linear(double x){
		return x;
	}

	//y = sqrt(x)
	private double function_SQRT(double x){
		return Math.sqrt(x);
	}

	//y = ln(x)
	private double function_EXP(double x){
		return Math.exp(x);
	}

	//y = exp(-x)
	private double function_R_EXP(double x){
		return Math.exp(-1 * x);
	}

	//y = MAX_START
	private double function_START(double x){
		return x == 0.0 ? heatEffect : 0.0;
	}

	public double fun(double temperature, EffectType type){
		int deltaT = (int)tempE - (int)tempS;
		double stride = 2.0 / (deltaT + 1.0);
		double sum = 0.0;
		int counter = 0;

		for (double k = 0; k <= 2; k += stride) {
			switch (type){
				case AVERAGE: sum += function_AVERAGE(k);
					break;
				case LINEAR: sum += function_linear(k);
					break;
				case SQRT: sum += function_SQRT(k);
					break;
				case EXP: sum += function_EXP(k);
					break;
				case START: sum += function_START(k);
					break;
				case R_EXP: sum += function_R_EXP(k);
					break;
			}
			counter++;
		}

		double[] values = new double[counter];
		counter = 0;

		for (double k = 0; k <= 2; k += stride) {
			switch (type){
				case AVERAGE: values[counter++] = function_AVERAGE(k);
					break;
				case LINEAR: values[counter++] = function_linear(k);
					break;
				case SQRT: values[counter++] = function_SQRT(k);
					break;
				case EXP: values[counter++] = function_EXP(k);
					break;
				case START: values[counter++] = function_START(k);
					break;
				case R_EXP: values[counter++] = function_R_EXP(k);
					break;
			}
		}

		int index = ((int)temperature - (int)tempS);
		return (values[index] / sum) * heatEffect;
	}
}
