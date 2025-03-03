package particletrieur.viewcontrollers.morphology;

import particletrieur.models.processing.Morphology;
import particletrieur.viewmodels.SelectionViewModel;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MorphologyViewController implements Initializable {

    @FXML
    TableView tableView;

    @Inject
    SelectionViewModel selectionViewModel;

    ObservableList<MorphologyParameterViewModel> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<MorphologyParameterViewModel, String> tableColumnParameterName = new TableColumn<>("Measurement");
        TableColumn<MorphologyParameterViewModel, String> tableColumnParameterValue = new TableColumn<>("Image");
        TableColumn<MorphologyParameterViewModel, String> tableColumnParameterValueMM = new TableColumn<>("Physical");

        tableColumnParameterName.setCellValueFactory(item -> item.getValue().parameterName);
        tableColumnParameterValue.setCellValueFactory(item -> item.getValue().parameterValue);
        tableColumnParameterValueMM.setCellValueFactory(item -> item.getValue().parameterValueMM);

        tableColumnParameterName.setPrefWidth(160);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().addAll(tableColumnParameterName, tableColumnParameterValue, tableColumnParameterValueMM);

        selectionViewModel.currentParticleImageProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.morphology != null && selectionViewModel.getCurrentParticle() != null) {
                update(newValue.morphology, newValue.morphology.convertToMM(selectionViewModel.getCurrentParticle().getResolution()));
            }
            else {
                update(new Morphology(), new Morphology());
            }
        });

        tableView.setItems(items);
    }


    public void update(Morphology m, Morphology n) {
        ArrayList<MorphologyParameterViewModel> newItems = new ArrayList<>();

        newItems.add(new MorphologyParameterViewModel(
                "Area",
                m.area,
                n.area,
                0,
                6,
                2));

        newItems.add(new MorphologyParameterViewModel(
                "Perimeter",
                m.perimeter,
                n.perimeter,
                0,
                3,
                1));

        newItems.add(new MorphologyParameterViewModel(
                "Solidity",
                m.solidity,
                n.solidity,
                3,
                0,
                0));

        newItems.add(new MorphologyParameterViewModel(
                "Roundness",
                m.roundness,
                n.roundness,
                3,
                0,
                0));

        newItems.add(new MorphologyParameterViewModel(
                "Circularity",
                m.circularity,
                n.circularity,
                3,
                0,
                0));

        newItems.add(new MorphologyParameterViewModel(
                "Convex Area",
                m.convexArea,
                n.convexArea,
                0,
                6,
                2));

        newItems.add(new MorphologyParameterViewModel(
                "Convex Perimeter",
                m.convexPerimeter,
                n.convexPerimeter,
                0,
                3,
                1));

        newItems.add(new MorphologyParameterViewModel(
                "Mean Circle Diameter",
                m.meanDiameter,
                n.meanDiameter,
                0,
                3,
                1));

        newItems.add(new MorphologyParameterViewModel(
                "Major Axis Length",
                m.majorAxisLength,
                n.majorAxisLength,
                0,
                3,
                1));

        newItems.add(new MorphologyParameterViewModel(
                "Minor Axis Length",
                m.minorAxisLength,
                n.minorAxisLength,
                0,
                3,
                1));

        newItems.add(new MorphologyParameterViewModel(
                "Mean",
                m.mean,
                n.mean,
                1,
                0,
                0));
        newItems.add(new MorphologyParameterViewModel(
                "Std dev",
                m.stddev,
                n.stddev,
                3,
                0,
                0));
        newItems.add(new MorphologyParameterViewModel(
                "Skew",
                m.skew,
                n.skew,
                3,
                0,
                0));
        newItems.add(new MorphologyParameterViewModel(
                "Kurtosis",
                m.kurtosis,
                n.kurtosis,
                3,
                0,
                0));
        newItems.add(new MorphologyParameterViewModel(
                "5th Moment",
                m.moment5,
                n.moment5,
                3,
                0,
                0));
        newItems.add(new MorphologyParameterViewModel(
                "6th Moment",
                m.moment6,
                n.moment6,
                3,
                0,
                0));

        items.clear();
        items.addAll(newItems);
    }

    public class MorphologyParameterViewModel {

        StringProperty parameterName = new SimpleStringProperty("");
        StringProperty parameterValue = new SimpleStringProperty("");
        StringProperty parameterValueMM = new SimpleStringProperty("");

        public MorphologyParameterViewModel(String parameter, double value, double valueMM, int precision, int precisionMM, int power) {

            parameterName.set(parameter);

            if (valueMM == 0.0) {
                if (power != 0) {
                    parameterValueMM.set("N/A");
                }
                else {
                    parameterValueMM.set("");
                }
            }
            else {
                String physical = String.format(String.format("%%.%df", precisionMM), valueMM);
                if (power == 0) {
                    parameterValueMM.set("");
                }
                else if (power == 1) {
                    parameterValueMM.set(physical + " mm");
                }
                else {
                    parameterValueMM.set(physical + " mm\u00B2");
                }
            }
            if (value == 0.0) {
                parameterValue.set("N/A");
            }
            else {
                String pixel = String.format(String.format("%%.%df", precision), value);
                if (power == 0) {
                    parameterValue.set(pixel);
                }
                else if (power == 1) {
                    parameterValue.set(pixel + " px");
                }
                else {
                    parameterValue.set(pixel+" px\u00B2");
                }
            }
        }
    }
}
