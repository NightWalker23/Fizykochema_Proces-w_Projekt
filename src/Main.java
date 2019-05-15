
public class Main {
	public static void main(String[] args) {
		Data data = new Data();
		Model model = new Model();
		model.setListOfElements(data.getListFromFile("./Specific_Heat.txt"));
		model.interpolateData();
		model.calculateEnthalpy();

		//TODO:
		//liczba przemian
//		for (Element el : model.getListOfElements()){
//			System.out.println("T =\t" + el.getTemperature());
//			System.out.println("CP =\t" + el.getSpecificHeat() + "\n");
//		}

//		double[] enthalpy = model.getEnthalpy();
//		for (double anEnthalpy : enthalpy) {
//			System.out.println(anEnthalpy);
//		}
	}
}
