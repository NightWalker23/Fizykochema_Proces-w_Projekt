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
		enthalpy = new double[this.listOfElements.size() - 1];
	}

	public double[] getEnthalpy() {
		return enthalpy;
	}

	public void interpolateData() {
		//TODO: dokonać interpolacji danych z listy, co 1 stopień temperatury
	}

	public void calculateEnthalpy() {
		if (enthalpy != null && enthalpy.length > 1) {
			double dT, dCp;
			dT = listOfElements.get(1).getTemperature() - listOfElements.get(0).getTemperature();
			dCp = (listOfElements.get(1).getSpecificHeat() + listOfElements.get(0).getSpecificHeat()) / 2.0;
			enthalpy[0] = dT * dCp;

			for (int i = 1; i < listOfElements.size() - 1; i++) {
				dT = listOfElements.get(i + 1).getTemperature() - listOfElements.get(i).getTemperature();
				dCp = (listOfElements.get(i + 1).getSpecificHeat() + listOfElements.get(i).getSpecificHeat()) / 2.0;
				enthalpy[i] = enthalpy[i - 1] + dT * dCp;
			}
		}
	}

}
