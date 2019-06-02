package model;

public class HeatEffect {
	private double tempS;
	private double tempE;
	private double heatEffect;
	private EffectType effectType;

	public HeatEffect(double tempS, double tempE, double heatEffect, EffectType effectType) {
		this.tempS = tempS;
		this.tempE = tempE;
		this.heatEffect = heatEffect;
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

	//y=const
	public double method_AVERAGE(double temperature){
		double range = tempE - tempS;
		return heatEffect / range;
	}

	//y = x
	private double function_linear(double x){
		return x;
	}

	public double method_LINEAR(double temperature){
		double position = temperature - tempS;
		double tS = 0.0, tE = tempE - tempS;
		double integral =  0.5 * (Math.pow(tE, 2) - Math.pow(tS, 2)); //wartość całki oznaczonej z funkcji y = x
		double multiplier = heatEffect / integral;

		return multiplier * 0.5 * ( function_linear(position) + function_linear(position + 1));
	}

	//y = exp(x)
	/*private double function_ours(double x){
		return Math.exp(x);
	}*/

	//y = sqrt(x)
	private double function_ours(double x){
		return Math.sqrt(x);
	}

	public double method_OURS(double temperature){
		double position = temperature - tempS;
		double tS = 0.0, tE = tempE - tempS;
		//double integral =  Math.exp(tE) - Math.exp(tS); //wartość całki oznaczonej z funkcji y = exp(x)
		double integral =  (2.0/3.0) * (Math.pow(tE, (3.0/2.0)) - Math.pow(tS, (3.0/2.0))); //wartość całki oznaczonej z funkcji y = sqrt(x)
		double multiplier = heatEffect / integral;

		return multiplier * trapz(position, (position+1), 1000);
	}

	private double trapz(double a, double b, double parts){
		double sum = 0.0;
		double h = (b-a)/parts;
		for (double i = a; i < b; i+=h){
			sum += 0.5 * h * (function_ours(i) + function_ours(i+h));
		}
		return sum;
	}

//	public static void main(String[] args) {
//		HeatEffect h1 = new HeatEffect(1500, 1510, 500, EffectType.AVERAGE);
//
//		double fraction;
//		double sum = 0.0;

//		System.out.println("AVERAGE");
//		for (int i = (int)h1.tempS; i < h1.tempE; i++){
//			fraction = h1.method_AVERAGE(i);
//			sum += fraction;
//
//			System.out.println(fraction);
//		}
//		System.out.println("SUM = " + sum);
//
//
//		System.out.println();
//		sum = 0.0;
//
//		System.out.println("LINEAR");
//		for (int i = (int)h1.tempS; i < h1.tempE; i++){
//			fraction = h1.method_LINEAR(i);
//			sum += fraction;
//
//
//			System.out.println(fraction);
//		}
//		System.out.println("SUM = " + sum);
//
//
//		System.out.println();
//		sum = 0.0;
//
//		System.out.println("OURS");
//		for (int i = (int)h1.tempS; i < h1.tempE; i++){
//			fraction = h1.method_OURS(i);
//			sum += fraction;
//
//			System.out.println(fraction);
//		}
//		System.out.println("SUM = " + sum);
//	}
}
