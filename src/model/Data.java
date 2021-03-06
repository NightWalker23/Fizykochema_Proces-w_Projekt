package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

	public List<Element> getListFromFile(String path) throws Exception {
		List<Element> listOfElements = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(path))) {
			while (scanner.hasNext()) {
				listOfElements.add(
						new Element(Double.parseDouble(scanner.next()),
									Double.parseDouble(scanner.next()))
				);
			}
		} catch (Exception o) {
			throw new Exception();
		}

		return listOfElements;
	}

}
