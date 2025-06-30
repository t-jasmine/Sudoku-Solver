package org.openjfx;

import org.openjfx.miniSudoku.MiniBoard;

import javafx.scene.control.TextField;
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

    //Create Button Box

    //Set Stage

    //Mini Sudoku Window


}
