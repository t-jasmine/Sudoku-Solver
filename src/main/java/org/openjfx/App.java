package org.openjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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

    //Fonts & Customization
    Font textFont = Font.font("Helvetica");
    Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

    public void styleTextField(int c, int r, String color)
    {
        //colors include white, green, & red
        TextField t = textFields[c][r];
        String style = "-fx-background-radius:0; ";

        switch(color) {
            case "white":
                style +=
                "-fx-background-color:rgb(255, 255, 255); " +
                "-fx-border-color: rgb(135, 125, 135);";
                break;
            case "green":
                style +=
                "-fx-background-color:rgb(100, 200, 100); " +
                "-fx-border-color: rgb(50, 100, 50);";
                break;
            case "red":
                style +=
                "-fx-background-color:rgb(225, 95, 95); " +
                "-fx-border-color: rgb(115, 25, 25);";
                break;
            default:
                break;
                //something
        }

        style += "-fx-border-width:";
        
        if (r % 3 == 0) {style += " 2";} else {style += " 0.5";}
        if (c == 8) {style += " 2";} else {style += " 0.5";}
        if (r == 8) {style += " 2";} else {style += " 0.5";}
        if (c % 3 == 0) {style += " 2";} else {style += " 0.5";}

        t.setFont(textFont);
        t.setStyle(style);
    }

    public void clearBoard()
    {
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {   
                styleTextField(c, r, "white");
                textFields[c][r].setText("");
            } 
        }
    }

    @Override
    public void start(Stage stage) {

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

                //Setting text field properties
                t.setAlignment(Pos.CENTER);
                textFields[c][r] = t; //columns represent x axis, rows are y-axis
                styleTextField(c, r, "white");
                t.setFont(textFont);
                
                //Limiting character input
                t.setTextFormatter(new TextFormatter<String>(change ->
                {
                    String newText = change.getControlNewText();
                    if(!newText.matches("[1-9]*"))
                    {
                        return null;
                    }
                    if(newText.length()>1)
                    {
                        //If another number is entered, only keep the last one
                        String lastChar = change.getText();
                        if (!lastChar.isEmpty() && lastChar.matches("[1-9]*"))
                        {
                            change.setRange(0, change.getControlText().length()); //get range of text to replace
                            change.setText(lastChar.substring(lastChar.length() - 1)); //set new text to last digit
                            return change;
                        }
                        return null;
                    }   
                    return change;
                }));

                //When arrow keys are pressed, focus on the next text field
                
                //final variables for lambda expression, cuz lambda expressions cant access local variables directly :3
                final int row = r;
                final int col = c;
                t.setOnKeyPressed(event -> {
                    if(event.getCode().isArrowKey())
                    {
                        switch(event.getCode())
                        {
                            case UP:
                                if (row > 0) textFields[col][row-1].requestFocus();
                                break;
                            case DOWN:
                                if (row<8) textFields[col][row+1].requestFocus();
                                break;
                            case LEFT:
                                if (col > 0) textFields[col-1][row].requestFocus();
                                break;
                            case RIGHT:
                                if (col < 8) textFields[col+1][row].requestFocus();
                                break;
                            default:
                                break;
                        }
                    }
                });
                

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
            clearBoard();
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        solveBtn.setFont(textFont);

        solveBtn.setOnAction((ActionEvent event) -> {
            Solver s = new Solver();
            Board b = new Board();

            //Reading the text fields and setting the board
            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {
                    try
                    {
                        Integer i = Integer.valueOf(textFields[c][r].getText().trim());
                        b.set(c,r,i);
                    } catch (NumberFormatException e) {}
                }
            }

            Board solution = s.solve(b);

            //Updating board with the solution

            for(int r = 0; r<9; r++)
            {
                for(int c = 0; c<9; c++)
                {               
                    styleTextField(c,r,"white");
                    if(solution!= null)
                    {     
                        if(textFields[c][r].getText().trim().equals(""))
                        {
                            styleTextField(c, r, "green");
                            textFields[c][r].setText(""+solution.get(c,r));
                        }
                    }
                    else
                    {
                        styleTextField(c, r, "red");
                    }
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