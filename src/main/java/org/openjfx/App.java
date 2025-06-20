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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    

    TextField[][] textFields = new TextField[9][9];

    public void styleTextField(int c, int r, boolean clear)
    {
        //When clear is true, it will set the text field to white (default)
        //When clear is false, it will set the text field to a light green color (solved)
        TextField t = textFields[c][r];
        String style = "-fx-background-radius:0; ";

        if(clear)
        {
            style +=
            "-fx-background-color:rgb(255, 255, 255); " +
            "-fx-border-color: rgb(180, 165, 180);";
        }
        else
        {
            style +=
            "-fx-background-color:rgb(100, 200, 100); " +
            "-fx-border-color: rgb(50, 100, 50);";
        }

        style += "-fx-border-width:";
        
        if (r % 3 == 0) {style += " 2";} else {style += " 0.5";}
        if (c == 8) {style += " 2";} else {style += " 0.5";}
        if (r == 8) {style += " 2";} else {style += " 0.5";}
        if (c % 3 == 0) {style += " 2";} else {style += " 0.5";}

        t.setText("");
        t.setStyle(style);
    }

    @Override
    public void start(Stage stage) {

        //Fonts & Customization
        Font textFont = Font.font("Helvetica");
        Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

        //9x9 Grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10,40,10,40));

        //Inserting text fields into the grid
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                TextField t = new TextField();
                t.setAlignment(Pos.CENTER);
                textFields[c][r] = t; //columns represent x axis, rows are y-axis
                styleTextField(c, r, true);
                grid.add(t,c,r);
            }
        }

        //Sudoku Text Label
        Label textLabel = new Label("Sudoku Solver");
        textLabel.setFont(headerFont);
        HBox titleBox = new HBox();
        titleBox.getChildren().add(textLabel);
        titleBox.setAlignment(Pos.CENTER);

        //Clear Button
        Button clearBtn = new Button("Clear");
        clearBtn.setFont(textFont);

        clearBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    styleTextField(c, r, true);
                } 
            }
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        solveBtn.setFont(textFont);

        solveBtn.setOnAction((ActionEvent event) -> {
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    //testing, temp values, will add functionality later :3
                    textFields[c][r].setText("");
                    styleTextField(c, r, false);
                }
            }
        });

        //Adding buttons to the grid
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(clearBtn, solveBtn);
        buttonBox.setAlignment(Pos.CENTER);
        
        //Setting up the stage
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25,25,25,25));
        vbox.getChildren().addAll(titleBox, grid, buttonBox);

        var scene = new Scene(vbox,400,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sudoku Solver");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}