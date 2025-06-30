package org.openjfx;

import java.util.function.BiConsumer;

import org.openjfx.miniSudoku.MiniBoard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UI {
    //Fonts
    public static Font textFont = Font.font("Helvetica");
    public static Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

    //Style Text Field
    public static void styleTextField(TextField[][] fields, int c, int r, String color)
    {
        TextField t = fields[c][r];
        int boxesPerRow;
        if(fields.length==4) {boxesPerRow = 2;} else {boxesPerRow = 3;}
        
        String style = "-fx-background-radius:0; ";

        //Styling color
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

        if (r % boxesPerRow == 0) {style += " 2";} else {style += " 0.5";}
        if (c == fields.length-1) {style += " 2";} else {style += " 0.5";}
        if (r == fields.length-1) {style += " 2";} else {style += " 0.5";}
        if (c % boxesPerRow == 0) {style += " 2";} else {style += " 0.5";}

        t.setFont(textFont);
        t.setStyle(style);
    }

    //Board Retrieval Methods
    
    public static Board getBoardFromTextFields(TextField[][] fields)
    {
        Board b = new Board();

        //Reading the text fields and setting the board
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                try
                {
                    Integer i = Integer.valueOf(fields[c][r].getText().trim());
                    b.set(c,r,i);
                } catch (NumberFormatException e) {}
            }
        }
        return b;
    }

    public static MiniBoard getMiniFromTextFields(TextField[][] fields)
    {
        MiniBoard b = new MiniBoard();

        //Reading the text fields and setting the board
        for(int r = 0; r<4; r++)
        {
            for(int c = 0; c<4; c++)
            {
                try
                {
                    Integer i = Integer.valueOf(fields[c][r].getText().trim());
                    b.set(c,r,i);
                } catch (NumberFormatException e) {}
            }
        }
        return b;
    }
    
    //Create Grid Method

    public static GridPane createGrid(TextField[][] fields, int gridLength, BiConsumer<Integer, Integer> onCellFocus)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        if(gridLength==4) { 
            grid.setPadding(new Insets(10,100,10,100));
        } else {
            grid.setPadding(new Insets(10,40,10,40));
        }

        int cellSize = (gridLength ==4 ) ? 30:0;

        for(int r = 0; r<gridLength; r++)
        {
           for(int c = 0; c<gridLength; c++)
            {
                TextField t = new TextField();
                fields[c][r] = t;

                //Setting text field properties
                t.setAlignment(Pos.CENTER);
                UI.styleTextField(fields,c, r, "white");
                t.setFont(textFont);

                if (gridLength==4) { //4x4 Board Properties
                    t.setPrefSize(cellSize, cellSize);
                    t.setMaxSize(cellSize, cellSize);
                }

                //Limiting character input
                t.setTextFormatter(new TextFormatter<String>(change ->
                {
                    String newText = change.getControlNewText();
                    if(newText.matches("[1-"+gridLength+"]?")) return change;
                    if (newText.length()>1)
                    {
                        //If another number is entered, only keep the last one
                        String lastChar = newText.replaceAll("[^1-"+gridLength+"]", "");
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
                //Final Variables for Lambda Expression
                final int row = r, col = c;
                t.setOnKeyPressed(event -> {
                    if(event.getCode().isArrowKey())
                    {
                        switch(event.getCode())
                        {
                            case UP: if (row > 0) fields[col][row-1].requestFocus(); break;
                            case DOWN: if (row<gridLength-1) fields[col][row+1].requestFocus(); break;
                            case LEFT: if (col > 0) fields[col-1][row].requestFocus(); break;
                            case RIGHT: if (col < gridLength-1) fields[col+1][row].requestFocus(); break;
                            default: break;
                        }
                    }
                });

                //When a text field is clicked, set it as the selected cell
                t.focusedProperty().addListener((observable, oldVal, newVal) -> {
                    if (newVal) {
                        onCellFocus.accept(col,row);
                        //Style the selected cell
                        UI.styleTextField(fields, col, row, "grey");
                    } else {
                        //Reset the style when focus is lost
                        UI.styleTextField(fields, col, row, "white");
                    }
                });

                grid.add(t,c,r);
            }         
        }
        return grid;
    }

    //Create Button Box

    //Set Stage
    

    //Mini Sudoku Window


}
