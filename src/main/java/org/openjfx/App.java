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
import javafx.scene.image.Image;
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
    
    //Variables
    TextField[][] textFields = new TextField[9][9];
    Solver s = new Solver();
    Board boardInput;
    Board solution;
    int selectedRow = -1;
    int selectedCol = -1;

    //Fonts
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
            case "grey":
                style +=
                "-fx-background-color:rgb(200, 200, 200); " +
                "-fx-border-color: rgb(115, 100, 115);";
                break;
            case "green":
                style +=
                "-fx-background-color:rgb(150, 250, 150); " +
                "-fx-border-color: rgb(50, 100, 50);";
                break;
            case "red":
                style +=
                "-fx-background-color:rgb(225, 95, 95); " +
                "-fx-border-color: rgb(115, 25, 25);";
                break;
            default:
                break;
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
        solution = null; //Reset the solution
    }

    public void setSolution()
    {
        boardInput = getBoardFromTextFields();
        solution = s.solve(boardInput);
    }

    public Board getBoardFromTextFields()
    {
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
        return b;
    }

    public GridPane createGrid()
    {
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

                    if(newText.matches("[1-9]?"))
                    {
                        return change;
                    }
                    if (newText.length()>1) {

                        //If another number is entered, only keep the last one
                        String lastChar = newText.replaceAll("[^1-9]","");
                        if (!lastChar.isEmpty()) { 
                            change.setRange(0, change.getControlText().length()); //get range of text to replace
                            change.setText(lastChar.substring(lastChar.length() - 1)); //set new text to last digit
                            return change;
                        }
                        return null;
                    }
                    return null;
                    
                }));

                //Arrow Key Navigation
                //allows the user to navigate through the grid using arrow keys
                
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
                
                //When a text field is clicked, set it as the selected cell
                t.focusedProperty().addListener((observable, oldVal, newVal) -> {
                    if (newVal) {
                        selectedRow = row;
                        selectedCol = col;
                        //Style the selected cell
                        styleTextField(selectedCol, selectedRow, "grey");
                    } else {
                        //Reset the style when focus is lost
                        styleTextField(col, row, "white");
                    }
                });

                grid.add(t,c,r);
            }
        }
        return grid;
    }

    public HBox createButtonBox()
    {
        //Clear Button
        Button clearBtn = new Button("Clear");
        clearBtn.setFont(textFont);

        clearBtn.setOnAction((ActionEvent event) -> {
            clearBoard();
        });

        //Solve Cell Button
        Button solveCellBtn = new Button("Solve Cell");
        solveCellBtn.setFont(textFont);

        solveCellBtn.setOnAction((ActionEvent event) ->
        {
            setSolution();

            //Updating the selected cell with the solution
            if (selectedRow >= 0 && selectedCol >= 0)
            {
                if(solution != null)
                { 
                    styleTextField(selectedCol, selectedRow, "green");
                    textFields[selectedCol][selectedRow].setText(""+solution.get(selectedCol,selectedRow));  
                } 
                else
                {
                    //No solution available;
                    styleTextField(selectedCol, selectedRow, "red");
                }
            }
            else
            {
                //No cell selected
                System.out.println("No cell selected");
            }
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        solveBtn.setFont(textFont);

        solveBtn.setOnAction((ActionEvent event) -> {
            setSolution();

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

        //Adding buttons to the button box
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(clearBtn, solveCellBtn, solveBtn);
        buttonBox.setAlignment(Pos.CENTER);

        return buttonBox;
    }

    public void setStage(Stage stage, VBox vbox)
    {
        var scene = new Scene(vbox,400,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sudoku Solver");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Sudoku.jpg")));
        stage.show();
    }

    @Override
    public void start(Stage stage) {

        //Sudoku Header
        Label textLabel = new Label("Sudoku Solver");
        textLabel.setFont(headerFont);

        HBox titleBox = new HBox();
        titleBox.getChildren().add(textLabel);
        titleBox.setAlignment(Pos.CENTER);
        
        GridPane grid = createGrid();
        HBox buttonBox = createButtonBox();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25,25,25,25));
        vbox.getChildren().addAll(titleBox, grid, buttonBox);

        setStage(stage, vbox);
        vbox.requestFocus(); //Removing initial focus from text fields
    }


    public static void main(String[] args) {
        launch();
    }

}