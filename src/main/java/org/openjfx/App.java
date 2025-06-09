package org.openjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    TextField[][] textFields = new TextField[9][9];

    @Override
    public void start(Stage stage) {

        //9x9 Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        //Inserting text fields into grid
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                TextField t = new TextField();
                t.setAlignment(Pos.CENTER);
                textFields[c][r] = t; //not [r][c]  because columns represent x axis, rows are y-axis
                textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
                grid.add(t,c,r);
            }
        }

        //Clear Button
        Button clearBtn = new Button("Clear");
        HBox hbBox = new HBox(10);
        hbBox.getChildren().add(clearBtn);
        hbBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(hbBox,0,9,3,1);

        clearBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    textFields[c][r].setText("");
                    textFields[c][r].setStyle("-fx-background-color:rgb(255,255,255)");
                } 
            }
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        hbBox = new HBox(10);
        hbBox.getChildren().add(solveBtn);
        hbBox.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(hbBox,6,9,3,1);

        solveBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    //testing, temp values, will add functionality later :3
                    textFields[c][r].setText("");
                    textFields[c][r].setStyle("-fx-background-color:rgb(144, 209, 128)");
                } 
            }
        });

        var scene = new Scene(grid, 400,400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}