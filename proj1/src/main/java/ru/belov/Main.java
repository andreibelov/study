package ru.belov;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Date;

public class Main extends Application {

    private TextArea txt,t;
    private String cht;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World!");
        cht = new java.util.Date().toString()+"\n    "+"HW!";
        setTxt();
        Circle crl = getCircle();
        Button btn = getButton();
        AnchorPane root = new AnchorPane();
        AnchorPane.setTopAnchor(txt, 10.0);
        AnchorPane.setLeftAnchor(txt, 10.0);
        AnchorPane.setRightAnchor(txt, 10.0);
        AnchorPane.setTopAnchor(t, 350.0);
        AnchorPane.setLeftAnchor(t, 10.0);
        AnchorPane.setRightAnchor(t, 10.0);
        AnchorPane.setTopAnchor(btn, 400.0);
        AnchorPane.setRightAnchor(btn, 10.0);
        root.getChildren().add(t);
        root.getChildren().add(txt);
        root.getChildren().add(crl);
        root.getChildren().add(btn);
        Scene sc1 = new Scene(root, 640, 480);
        primaryStage.setScene(sc1);
        primaryStage.setMaxHeight(480);
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(350);
        primaryStage.setMaxWidth(640);
        primaryStage.show();
    }

    private void setTxt() {
        txt = new TextArea();
        txt.setText(cht);
        txt.setEditable(false);
        txt.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                txt.setScrollTop(Double.MAX_VALUE);
                //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });
        txt.setMinHeight(320.0);
        txt.setMaxHeight(320.0);
        t = new TextArea();
        t.setMaxHeight(40.0);
        t.setOnKeyReleased(keyPressed());
    }

    private Button getButton() {
        Button btn = new Button();
        btn.setText("Tweet!");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println();
                doTweet();
            }
        });
        return btn;
    }

    private EventHandler<KeyEvent> keyPressed(){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent KEY_RELEASED) {
                //System.out.println();
                if (KEY_RELEASED.getCode()== KeyCode.ENTER) {
                    doTweet();
                }
            }
        };
    }

    private void doTweet() {
        String n = "";
        String p = t.getText().replace("\n","");
        if(!p.equals(n)) {
            cht += "\n" + new Date().toString()+"\n    "+t.getText();
            txt.setText(cht);
            txt.appendText("");
            t.clear();
        } else {
            t.clear();
        }
    }

    private Circle getCircle() {
        Circle circle;
        circle=new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(415.0f);
        circle.setRadius(15.0f);
        circle.setFill(Color.rgb(155,158,64,0.15));
        return circle;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
