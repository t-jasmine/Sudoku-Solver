package org.openjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    

    TextField[][] textFields = new TextField[9][9];

    public void clearTextField(TextField t)
    {
        t.setText("");
        t.setStyle("-fx-background-color:rgb(255, 255, 255); -fx-background-radius:0; -fx-border-width: 1; -fx-border-color: rgb(180, 165, 180);");
    }

    @Override
    public void start(Stage stage) {

        //Fonts & Customization
        Font textFont = Font.font("Helvetica");
        Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

        //9x9 Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(25,50,25,50));
        //Inserting text fields into grid
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                TextField t = new TextField();
                t.setAlignment(Pos.CENTER);
                textFields[c][r] = t; //not [r][c]  because columns represent x axis, rows are y-axis
                clearTextField(textFields[c][r]);
                grid.add(t,c,r);
            }
        }

        //Sudoku Text Label
        Label textLabel = new Label("Sudoku Solver");
        textLabel.setFont(headerFont);
        HBox hbBox = new HBox(10);
        hbBox.getChildren().add(textLabel);
        hbBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(hbBox,0,9,9,1);

        //Clear Button
        Button clearBtn = new Button("Clear");
        clearBtn.setFont(textFont);
        hbBox = new HBox(10);
        hbBox.getChildren().add(clearBtn);
        hbBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(hbBox,0,10,3,1);

        clearBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    clearTextField(textFields[c][r]);
                } 
            }
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        solveBtn.setFont(textFont);
        hbBox = new HBox(10);
        hbBox.getChildren().add(solveBtn);
        hbBox.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(hbBox,6,10,3,1);

        solveBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    //testing, temp values, will add functionality later :3
                    textFields[c][r].setText("");
                    textFields[c][r].setStyle("-fx-background-color:rgb(150, 235, 100); -fx-background-radius:0; -fx-border-width: 1; -fx-border-color: rgb(60, 185, 60);");
                }
            }
        });

        var scene = new Scene(grid,400,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sudoku Solver");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}