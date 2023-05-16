package GUI.Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class DrawingController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
    }

    public void handleSaveDrawing(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Drawing");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", selectedFile);
                alertUser(Alert.AlertType.INFORMATION, "Saved", "The image has successfully saved");
            } catch (IOException e) {
                e.printStackTrace();
                alertUser(Alert.AlertType.ERROR, "Error", "Could not save the image.");
            }
        }
    }

    private void alertUser(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleClearCanvas(ActionEvent actionEvent) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void handleStartDrawing(MouseEvent event) {
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
    }

    public void handleDraw(MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }
    public void handleSetRed(ActionEvent actionEvent) {
        gc.setStroke(Color.RED);
    }

    public void handleSetBlue(ActionEvent actionEvent) {
        gc.setStroke(Color.BLUE);
    }

    public void handleSetGreen(ActionEvent actionEvent) {
        gc.setStroke(Color.GREEN);
    }

    public void handleSetBrown(ActionEvent actionEvent) {
        gc.setStroke(Color.BROWN);
    }

    public void handleSetWhite(ActionEvent actionEvent) {
        gc.setStroke(Color.WHITE);
    }

    public void handleSetBlack(ActionEvent actionEvent) {
        gc.setStroke(Color.BLACK);
    }

    public void handleSetYellow(ActionEvent actionEvent) {
        gc.setStroke(Color.YELLOW);
    }
}
