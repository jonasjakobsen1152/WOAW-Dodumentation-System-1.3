package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawingController {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
    }

    public void handleSetRed(ActionEvent actionEvent) {
        gc.setStroke(Color.RED);
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
}
