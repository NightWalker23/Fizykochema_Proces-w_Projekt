
public class Main {
	public static void main(String[] args) {
		Data data = new Data();
		Model model = new Model();
		model.setListOfElements(data.getListFromFile("./Specific_Heat.txt"));
		model.interpolateData();
		model.calculateEnthalpy();

		System.out.println();
		double[] enthalpy = model.getEnthalpy();
		for (double anEnthalpy : enthalpy) {
			System.out.println(anEnthalpy);
		}
	}
}
