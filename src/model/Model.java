package model;

import java.util.ArrayList;
import java.util.List;

public class Model {
	private List<Element> listOfElements = new ArrayList<>();
	private double[] enthalpy;

	public List<Element> getListOfElements() {
		return listOfElements;
	}

	public void setListOfElements(List<Element> listOfElements) {
		this.listOfElements = listOfElements;
	}

	public double[] getEnthalpy() {
		return enthalpy;
	}

	private double interpolateCP(double x1, double x2, double y1, double y2, double x_sz) {
		return (x_sz - x1) * (y2 - y1) / (x2 - x1) + y1;
	}

	public void interpolateData() {
		for (int i = 0; i < listOfElements.size() - 1; i++) {
			double currentTemp = listOfElements.get(i).getTemperature();
			double nextTemp = listOfElements.get(i + 1).getTemperature();
			double currentCP = listOfElements.get(i).getSpecificHeat();
			double nextCP = listOfElements.get(i + 1).getSpecificHeat();
			int stepsNumber = (int) (nextTemp - currentTemp) - 1;

			for (int j = 0; j < stepsNumber; j++) {
				double newTemperature = currentTemp + j + 1;
				double newCP = interpolateCP(currentTemp, nextTemp, currentCP, nextCP, newTemperature);
				listOfElements.add(i + 1 + j, new Element(newTemperature, newCP));
			}
		}
	}

	public void assignHeatEffectToTheElements(List<HeatEffect> heatEffectList){
		for (HeatEffect heatEl: heatEffectList) {
			for (Element el : listOfElements){
				if ((int)el.getTemperature() >= (int)heatEl.getTempS() && (int)el.getTemperature() <= (int)heatEl.getTempE()){
					el.setHeatEffectFraction(el.getHeatEffectFraction() + calculateHeatEffectFraction(heatEl, el));
				}
			}
		}
	}

	private double calculateHeatEffectFraction(HeatEffect heatEffect, Element el){
		return heatEffect.fun(el.getTemperature(), heatEffect.getEffectType());
	}

	public void calculateEnthalpy() {
		enthalpy = new double[this.listOfElements.size() - 1];

		if (enthalpy.length > 1) {
			double dT, dCp;
			Element el1 = listOfElements.get(1);
			Element el2 = listOfElements.get(0);
			dT = el1.getTemperature() - el2.getTemperature();
			dCp = (el1.getSpecificHeat() + el2.getSpecificHeat()) / 2.0;
			enthalpy[0] = dT * dCp;

			for (int i = 1; i < listOfElements.size() - 1; i++) {
				el1 = listOfElements.get(i + 1);
				el2 = listOfElements.get(i);
				dT = el1.getTemperature() - el2.getTemperature();
				dCp = (el1.getSpecificHeat() + el2.getSpecificHeat()) / 2.0;
				enthalpy[i] = enthalpy[i - 1] + dT * dCp + el2.getHeatEffectFraction();
			}
		}
	}

	public void clearElementsHeatEffect(){
		for (Element el : listOfElements){
			el.setHeatEffectFraction(0.0);
		}
	}

}
