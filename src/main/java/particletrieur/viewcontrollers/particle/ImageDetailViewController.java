/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package particletrieur.viewcontrollers.particle;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import particletrieur.App;
import particletrieur.controls.SymbolLabel;
import particletrieur.models.Supervisor;
import particletrieur.models.project.Particle;
import particletrieur.AbstractController;
import particletrieur.FxmlLocation;
import particletrieur.viewmodels.SelectionViewModel;
import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Ross Marchant <ross.g.marchant@gmail.com>
 */
@FxmlLocation("/views/particle/ImageDetailView.fxml")
public class ImageDetailViewController extends AbstractController implements Initializable {

    @FXML
    SymbolLabel symbolValidated;
    @FXML
    Label labelValidator;
    @FXML
    Label labelClassifierId;
    @FXML
    ScrollPane scrollPane;
    @FXML
    GridPane gridPane;
    @FXML
    Label labelImageLabel;
    @FXML
    ImageView imageView;
    @FXML
    Label labelImageNumber;

    @FXML
    Label labelError;
    @FXML
    Label labelClass;
    @FXML
    Label labelTag;
    @FXML
    Label labelSample;
    @FXML
    Label labelFilename;
    @FXML
    Label labelPath;
    @FXML
    Label labelInfo;
    @FXML
    Label labelGUID;

    @Inject
    private SelectionViewModel selectionViewModel;
    @Inject
    private Supervisor supervisor;

    public ImageDetailViewController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        ((Pane) root).setMinWidth(360);
//        ((Pane) root).setPrefWidth(360);
        imageView.setFitWidth(360);
        imageView.setFitHeight(360);
//        imageView.fitWidthProperty().bind(Bindings.subtract(((Pane) root).widthProperty(),14));
//        imageView.fitHeightProperty().bind(Bindings.subtract(((Pane) root).widthProperty(),14));
//        gridPane.prefWidthProperty().bind(Bindings.subtract(((Pane) root).widthProperty(),14));
//        ((Pane) root).maxWidthProperty().bind(Bindings.add(imageView.fitWidthProperty(),14));
        scrollPane.maxWidthProperty().bind(Bindings.add(imageView.fitWidthProperty(),14));
        gridPane.maxWidthProperty().bind(imageView.fitWidthProperty());
        scrollPane.minWidthProperty().bind(Bindings.add(imageView.fitWidthProperty(),14));
        gridPane.minWidthProperty().bind(imageView.fitWidthProperty());
        selectionViewModel.currentParticleProperty().addListener((observable, oldValue, newValue) -> {
            setData(newValue);
        });
        gridPane.setVisible(false);
        symbolValidated.setVisible(false);

        labelValidator.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals("")) symbolValidated.setVisible(false);
            else symbolValidated.setVisible(true);
        }));
    }    
    
    public void setImage(Image image) {
        imageView.setImage(image);
    }

    //TODO on error write error image
    public void setData(Particle particle) {
        if (particle == null) {
            setImage(new Image(App.class.getResourceAsStream("/icons/icon-thin.png"),512, 512,true,true));
            gridPane.setVisible(false);
            labelImageNumber.setText("No Images");
            try {
                labelImageLabel.textProperty().unbind();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            labelImageLabel.setText("");
            return;
        }
        gridPane.setVisible(true);
        try {
            if (particle.getFile().exists()) {
                Image image = particle.getImage();
                supervisor.project.setParticleShape(particle, (int) image.getWidth(), (int) image.getHeight());
                setImage(image);
            }
            else {
                setImage(new Image(App.class.getResourceAsStream("/icons/missing-image-512.png"),512, 512,true,true));
            }
//            labelError.setVisible(false);
//            imageView.setVisible(true);
        } catch (IOException e) {
            setImage(new Image(App.class.getResourceAsStream("/icons/missing-image-512.png"),512, 512,true,true));
//            e.printStackTrace();
//            labelError.setVisible(true);
//            imageView.setVisible(false);
        }

        labelImageNumber.setText(String.format("Image #%d", supervisor.project.particles.indexOf(particle) + 1));

        //Had some weird intermittent bug with unbinding that makes no sense (ArrayIndexOutOfBounds)
        try {
            labelClass.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelClass.textProperty().bind(particle.classification);

        try {
            labelTag.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelTag.textProperty().bind(particle.tagUIProperty);

        try {
            labelImageLabel.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelImageLabel.textProperty().bind(particle.classification);

        try {
            labelSample.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelSample.textProperty().bind(particle.sampleIDProperty());

        try {
            labelClassifierId.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelClassifierId.textProperty().bind(particle.classifierIdProperty);

        try {
            labelValidator.textProperty().unbind();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        labelValidator.textProperty().bind(particle.validatorProperty());

        labelFilename.setText(particle.getShortFilename());
        labelPath.setText(particle.getFolder());

        labelInfo.setText(String.format("%d x %d pixels", particle.getImageHeight(), particle.getImageWidth()));

        labelGUID.setText(particle.getGUID());

    }

    @FXML
    private void handleIncreaseSize(ActionEvent event) {
        double width = imageView.getFitWidth();
        if (width < 900) width += 60;
        imageView.setFitWidth(width);
        imageView.setFitHeight(width);
//        ((Pane) root).setPrefWidth(width);
//        ((Pane) root).setMinWidth(width);
    }

    @FXML
    private void handleDecreaseSize(ActionEvent event) {
        double width = imageView.getFitWidth();
        if (width > 180) width -= 60;
//        ((Pane) root).setMinWidth(width);
//        ((Pane) root).setPrefWidth(width);
        imageView.setFitWidth(width);
        imageView.setFitHeight(width);
    }
}
