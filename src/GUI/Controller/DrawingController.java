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
        //Sets the size of the pen that is used to draw
        gc.setLineWidth(2);
        //Sets the color of the pen to black
        gc.setStroke(Color.BLACK);
    }

    /**
     * The method is called when the save button is clicked
     * This method is used to save the drawing as a png file.
     * The file is saved in the folder that the user chooses
     * @param actionEvent
     */
    public void handleSaveDrawing(ActionEvent actionEvent) {
        //Opens the windows explore and lets the user choose a place to save the drawn image.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Drawing");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                //Uses the writable image class to create a new image using the canvas size
                WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
                //Takes a snapshot pf the image created
                canvas.snapshot(null, writableImage);
                //Converts the datatype and stores it into a image file
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Saves the image as a png file in the selected folder.
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
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * This method is used to clear the whole canvas so the user can start over.
     * The method is called when the user clicks on the clear button
     */
    public void handleClearCanvas(ActionEvent actionEvent) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * This method is called when the user clicks inside the canvas
     * The method is used to start drawing
     * @param event
     */
    public void handleStartDrawing(MouseEvent event) {
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
    }

    /**
     * This method is used to handle drawing
     * @param event
     */
    public void handleDraw(MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
    }

    /**
     * The methods under are used to set the color of the pen based on which button is clicked
     * @param actionEvent
     */
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
