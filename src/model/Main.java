package model;

public class Main {
	public static void main(String[] args) {
		Model model = new Model();
		model.setListOfElements(new Data().getListFromFile("./Specific_Heat.txt"));
		model.interpolateData();
		model.assignHeatEffectToTheElements();
		model.calculateEnthalpy();

//		double counter = 56;
//		double[] enthalpy2 = model.getEnthalpy();
//		for (double anEnthalpy : enthalpy2) {
//			System.out.println(counter++ + "\t" + anEnthalpy);
//		}
	}
}
