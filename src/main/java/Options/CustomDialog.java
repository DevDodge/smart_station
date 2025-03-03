/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Options;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomDialog extends Stage {

    private static final Interpolator EXP_IN = new Interpolator() {
        @Override
        protected double curve(double t) {
            return (t == 1.0) ? 1.0 : 1 - Math.pow(2.0, -10 * t);
        }
    };

    private static final Interpolator EXP_OUT = new Interpolator() {
        @Override
        protected double curve(double t) {
            return (t == 0.0) ? 0.0 : Math.pow(2.0, 10 * (t - 1));
        }
    };

    private ScaleTransition scale1 = new ScaleTransition();
    private ScaleTransition scale2 = new ScaleTransition();

    private SequentialTransition anim = new SequentialTransition(scale1, scale2);

    public CustomDialog(String header, String content, boolean showButton) {
    Pane root = new Pane();

    scale1.setFromX(0.01);
    scale1.setFromY(0.01);
    scale1.setToY(1.0);
    scale1.setDuration(Duration.seconds(0.33));
    scale1.setInterpolator(EXP_IN);
    scale1.setNode(root);

    scale2.setFromX(0.01);
    scale2.setToX(1.0);
    scale2.setDuration(Duration.seconds(0.33));
    scale2.setInterpolator(EXP_OUT);
    scale2.setNode(root);

    initStyle(StageStyle.TRANSPARENT);
    initModality(Modality.APPLICATION_MODAL);

    Rectangle bg = new Rectangle(350, 150, Color.WHITESMOKE);
    bg.setStroke(Color.BLACK);
    bg.setStrokeWidth(1.5);

    Label headerLabel = new Label(header);
    headerLabel.setFont(Font.font(20));
    headerLabel.setWrapText(true);  // Enable text wrapping

    Label contentLabel = new Label(content);
    contentLabel.setFont(Font.font(16));
    contentLabel.setWrapText(true);  // Enable text wrapping

    VBox box = new VBox(10,
            headerLabel,
            new Separator(Orientation.HORIZONTAL),
            contentLabel
    );
    box.setPadding(new Insets(15));
    

    // Make the dialog's size flexible based on the content
    bg.widthProperty().bind(box.widthProperty().add(100));
    bg.heightProperty().bind(box.heightProperty().add(50));

    root.getChildren().addAll(bg, box);

    if (showButton) {
        Button btn = new Button("OK");
        btn.setOnAction(e -> closeDialog());
        root.getChildren().add(btn);

        // Position the button dynamically
        btn.layoutXProperty().bind(bg.widthProperty().subtract(60));
        btn.layoutYProperty().bind(bg.heightProperty().subtract(40));
    }

    // Handle Enter and Esc key presses
    root.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
            closeDialog();
        }
    });

    setScene(new Scene(root, null));
}


    public void openDialog() {
        show();
        anim.play();
    }

    private void closeDialog() {
        anim.setOnFinished(e -> close());
        anim.setAutoReverse(true);
        anim.setCycleCount(2);
        anim.playFrom(Duration.seconds(0.66));
    }
}
