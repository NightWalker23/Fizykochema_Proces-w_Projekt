package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import static model.EffectType.*;


/**
 * Created by Kamil on 2019-06-02.
 */
public class MainController {

    Model model = new Model();

    boolean isLoadData = false;

    @FXML
    private Pane calculationPane;

    @FXML
    private TextField tpTextField;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private TextField heatEffectTextField;

    @FXML
    private TextField tkTextField;

    @FXML
    private ChoiceBox<EffectType> methodChoiceBox;

    @FXML
    private TableView<HeatEffect> tableView;

    @FXML
    private TableColumn<HeatEffect, Double> tempStartColumn;

    @FXML
    private TableColumn<HeatEffect, Double> tempEndColumn;

    @FXML
    private TableColumn<HeatEffect, Double> heatEffectColumn;

    @FXML
    private TableColumn<HeatEffect, EffectType> methodColumn;

    ObservableList<HeatEffect> heatEffectObservableList;


    @FXML
    void initialize() {

        heatEffectObservableList = FXCollections.observableArrayList();

        tpTextField.setText("400");
        tkTextField.setText("500");
        heatEffectTextField.setText("50");
        methodChoiceBox.setValue(AVERAGE);

        //heatEffectObservableList.add(new HeatEffect(800, 1200, 250, SQRT));
        //heatEffectObservableList.add(new HeatEffect(800, 1200, 250, AVERAGE));

        tempStartColumn.setCellValueFactory(new PropertyValueFactory<>("tempS"));
        tempEndColumn.setCellValueFactory(new PropertyValueFactory<>("tempE"));
        heatEffectColumn.setCellValueFactory(new PropertyValueFactory<>("heatEffect"));
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("effectType"));

        tableView.setItems(heatEffectObservableList);

        // Set Choicebox default values
        ObservableList<EffectType> items = FXCollections.observableArrayList(EffectType.values());
        methodChoiceBox.setItems(items);
    }

    @FXML
    public void loadDataFromFile() {
//        String filePath = "";
//
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//        fileChooser.getExtensionFilters().add(extFilter);
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        if (selectedFile != null) {
//            filePath = selectedFile.toString();
//        }

        try {
            model.setListOfElements(new Data().getListFromFile("./Specific_Heat.txt"));
//          model.setListOfElements(new Data().getListFromFile(filePath));
            showAlert("Sukces", "Dane wczytane");
        } catch (Exception e) {
            showAlert("Zły format", "Zły format pliku");
        }

        calculationPane.setDisable(false);

        model.interpolateData();
    }


    @FXML
    public void addItem() {
        double tempS;
        double tempE;
        double effect;

        if (checkTemp(tpTextField.getText())) {
            tempS = Double.parseDouble(tpTextField.getText());
        } else {
            return;
        }

        if (checkTemp(tkTextField.getText())) {
            tempE = Double.parseDouble(tkTextField.getText());
        } else {
            return;
        }

        if (tempS >= tempE) {
            showAlert("Złe wartości", "Temperatura początkowa musi być mniejsza niż końcowa");
            return;
        }

        if (checkEnergy(heatEffectTextField.getText())) {
            effect = Double.parseDouble(heatEffectTextField.getText());
        } else {
            return;
        }

        EffectType effectType = methodChoiceBox.getValue();

        heatEffectObservableList.add(new HeatEffect(tempS, tempE, effect, effectType));

    }

    private boolean checkTemp(String input) {

        if (input == null || input.trim().isEmpty()) {
            showAlert("Brak wartości", "Wypełnij wszystkie pola");
            return false;
        }

        if (!input.matches("[0-9]+")) {
            showAlert("Zły format", "Proszę wpisać liczbę");
            return false;
        }

        double convertedInput = Double.parseDouble(input);

        double tempMin = model.getListOfElements().get(0).getTemperature();
        double tempMax = model.getListOfElements().get(model.getListOfElements().size() - 1).getTemperature();

        if (convertedInput > tempMax || convertedInput < tempMin) {
            showAlert("Zły zakres", "Minimlna temperatura to " + tempMin + " st.C , Maksymalna temperatura to " + tempMax + " st.C");
            return false;
        }

        return true;
    }

    private boolean checkEnergy(String input) {
        if (input == null || input.trim().isEmpty()) {
            showAlert("Brak wartości", "Wypełnij wszystkie pola");
            return false;
        }

        if (!input.matches("[0-9]+")) {
            showAlert("Zły format", "Proszę wpisać liczbę");
            return false;
        }
        return true;
    }

    @FXML
    public void deleteItem() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            HeatEffect selectedItem = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedItem);

            Iterator itr = heatEffectObservableList.iterator();
            while (itr.hasNext()) {
                HeatEffect x = (HeatEffect) itr.next();
                if (x.getTempS() == selectedItem.getTempS() && x.getTempE() == selectedItem.getTempE() && x.getHeatEffect() == selectedItem.getHeatEffect() && x.getEffectType() == selectedItem.getEffectType()) {
                    itr.remove();
                }
            }
        }
    }

    @FXML
    public void selectedTableView() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            HeatEffect selectedItem = tableView.getSelectionModel().getSelectedItem();
            tpTextField.setText(String.valueOf((int)selectedItem.getTempS()));
            tkTextField.setText(String.valueOf((int)selectedItem.getTempE()));
            heatEffectTextField.setText(String.valueOf((int)selectedItem.getHeatEffect()));
            methodChoiceBox.setValue(selectedItem.getEffectType());
        }
    }

    @FXML
    public void editItem() {

        double tempS;
        double tempE;
        double effect;

        if (checkTemp(tpTextField.getText())) {
            tempS = Double.parseDouble(tpTextField.getText());
        } else {
            return;
        }

        if (checkTemp(tkTextField.getText())) {
            tempE = Double.parseDouble(tkTextField.getText());
        } else {
            return;
        }

        if (tempS >= tempE) {
            showAlert("Złe wartości", "Temperatura początkowa musi być mniejsza niż końcowa");
            return;
        }

        if (checkEnergy(heatEffectTextField.getText())) {
            effect = Double.parseDouble(heatEffectTextField.getText());
        } else {
            return;
        }

        EffectType effectType = methodChoiceBox.getValue();

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            HeatEffect selectedItem = tableView.getSelectionModel().getSelectedItem();
            selectedItem.setTempS(tempS);
            selectedItem.setTempE(tempE);
            selectedItem.setHeatEffect(effect);
            selectedItem.setEffectType(effectType);
        }
        tableView.refresh();
    }

    @FXML
    public void cleanLineChart() {
        lineChart.getData().clear();
    }

    @FXML
    void showEntalpyWithEffect() {

        model.assignHeatEffectToTheElements(heatEffectObservableList);
        model.calculateEnthalpy();

        XYChart.Series series = new XYChart.Series();

        List<Element> list = model.getListOfElements();
        double en[] = model.getEnthalpy();
        for (int i = 0; i < model.getListOfElements().size() - 1; i++) {
            series.getData().add(new XYChart.Data(String.valueOf(list.get(i).getTemperature()), en[i]));
        }
        lineChart.getData().addAll(series);
        model.clearElementsHeatEffect();
    }

    @FXML
    void saveEntalpyLineChart() {
        int imageWidth = (int) lineChart.getWidth();
        int imageHeight = (int) lineChart.getHeight();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        File imageFile = fileChooser.showSaveDialog(null);

        if (imageFile != null) {
            WritableImage writableImage = new WritableImage(imageWidth, imageHeight);

            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.WHITE);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(lineChart.snapshot(snapshotParameters, writableImage), null), "png", imageFile);
            } catch (Exception s) {
                showAlert("Save image error", "Image cannot be saved");
            }
        }
    }

    @FXML
    void saveEntalpyFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz obliczenia");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                for (int i = 0; i < heatEffectObservableList.size(); i++) {
                    HeatEffect pmArray = heatEffectObservableList.get(i);
                    String pm = "Przemiana " + i + ": Tp:" + pmArray.getTempS() + ", Tk:" + pmArray.getTempE() + ", Ec:" + pmArray.getHeatEffect() +
                            ", Typ: " + pmArray.getEffectType().toString();
                    writer.println(pm);
                }
                writer.println();
                for(int i = 0 ; i < model.getEnthalpy().length; i++) {
                    writer.println("Temp: " + model.getListOfElements().get(i).getTemperature() + ", Entalpia: " + model.getEnthalpy()[i]);
                }
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
