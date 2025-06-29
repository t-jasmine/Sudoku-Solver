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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
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
    TextField[][] miniTextFields = new TextField[4][4];
    Solver s = new Solver();
    Board boardInput;
    Board solution;
    int selectedRow = -1;
    int selectedCol = -1;

    //Fonts
    Font textFont = Font.font("Helvetica");
    Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

    private void styleTextField(int c, int r, String color, boolean mini)
    {
        TextField t;
        int gridLength;
        int boxesPerRow;
        if(mini)
        {
            t = miniTextFields[c][r];
            gridLength = 4;
            boxesPerRow = 2;
        }
        else
        {
            t = textFields[c][r];
            gridLength = 9;
            boxesPerRow = 3;
        }
        
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
        if (c == gridLength-1) {style += " 2";} else {style += " 0.5";}
        if (r == gridLength-1) {style += " 2";} else {style += " 0.5";}
        if (c % boxesPerRow == 0) {style += " 2";} else {style += " 0.5";}

        t.setFont(textFont);
        t.setStyle(style);
    }

    private void clearBoard()
    {
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {   
                styleTextField(c, r, "white", false);
                textFields[c][r].setText("");
            } 
        }
        solution = null; //Reset the solution
    }

    private void setSolution()
    {
        boardInput = getBoardFromTextFields();
        solution = s.solve(boardInput);
    }

    private void solveSelectedCell()
    {
        setSolution();

        //Updating the selected cell with the solution
        if (selectedRow >= 0 && selectedCol >= 0)
        {
            if(solution != null)
            { 
                styleTextField(selectedCol, selectedRow, "green", false);
                textFields[selectedCol][selectedRow].setText(""+solution.get(selectedCol,selectedRow));  
            } 
            else
            {
                //No solution available;
                styleTextField(selectedCol, selectedRow, "red", false);
            }
        }
        else
        {
            //No cell selected
            System.out.println("No cell selected");
        }
    }

    private void solveBoard()
    {
        setSolution();

        //Updating board with the solution

        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {               
                styleTextField(c,r,"white", false);
                if(solution!= null)
                {     
                    if(textFields[c][r].getText().trim().equals(""))
                    {
                        styleTextField(c, r, "green", false);
                        textFields[c][r].setText(""+solution.get(c,r));
                    }
                }
                else
                {
                    styleTextField(c, r, "red", false);
                }
            }
        }
    }

    private Board getBoardFromTextFields()
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

    private GridPane create9x9Grid()
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
                textFields[c][r] = t; //columns represent x axis, rows are y-axis

                //Setting text field properties
                t.setAlignment(Pos.CENTER);
                styleTextField(c, r, "white", false);
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
                        styleTextField(selectedCol, selectedRow, "grey", false);
                    } else {
                        //Reset the style when focus is lost
                        styleTextField(col, row, "white", false);
                    }
                });

                grid.add(t,c,r);
            }
        }
        return grid;
    }

    private GridPane create4x4Grid()
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(160,160);
        grid.setPadding(new Insets(10,100,10,100));

        int cellSize = 40;
        for(int r = 0; r<4; r++)
        {
            for(int c = 0; c<4; c++)
            {
                TextField t = new TextField();
                miniTextFields[c][r] = t;
                t.setAlignment(Pos.CENTER);
                t.setFont(textFont);
                t.setPrefSize(cellSize, cellSize);
                t.setMaxSize(cellSize, cellSize);
                styleTextField(c,r,"white",true);

                //Limiting character input
                t.setTextFormatter(new TextFormatter<String>(change ->
                {
                    String newText = change.getControlNewText();

                    if(newText.matches("[1-4]?"))
                    {
                        return change;
                    }
                    if (newText.length()>1) {
                        //If another number is entered, only keep the last one
                        String lastChar = newText.replaceAll("[^1-4]","");
                        if (!lastChar.isEmpty()) { 
                            change.setRange(0, change.getControlText().length()); //get range of text to replace
                            change.setText(lastChar.substring(lastChar.length() - 1)); //set new text to last digit
                            return change;
                        }
                        return null;
                    }
                    return null;
                }));

                final int row = r;
                final int col = c;
                t.setOnKeyPressed(event -> {
                    if(event.getCode().isArrowKey())
                    {
                        switch(event.getCode())
                        {
                            case UP:
                                if (row > 0) miniTextFields[col][row-1].requestFocus();
                                break;
                            case DOWN:
                                if (row<3) miniTextFields[col][row+1].requestFocus();
                                break;
                            case LEFT:
                                if (col > 0) miniTextFields[col-1][row].requestFocus();
                                break;
                            case RIGHT:
                                if (col < 3) miniTextFields[col+1][row].requestFocus();
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
                        styleTextField(selectedCol, selectedRow, "grey", true);
                    } else {
                        //Reset the style when focus is lost
                        styleTextField(col, row, "white", true);
                    }
                });

                grid.add(t,c,r);
            }
        }
        
        for(int i = 0; i<4; i++)
        {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(25); //4 columns, 25% each width
            grid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(25); //4 rows, 25% each height                grid.getRowConstraints().add(rowConst);
        }
        
        return grid;
    }

    private void initMiniSudoku(String toSolve) //toSolve = cell, board
    {
        Stage stage = new Stage();
        
        Button testBtn = new Button("Test");
        testBtn.setOnAction((ActionEvent event) ->
        {
            stage.close();
            if(toSolve.equals("cell"))
            {
                solveSelectedCell();
            }
            else
            {
                solveBoard();
            }
        });

        GridPane grid = create4x4Grid();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        //vbox.setPadding(new Insets(10,10,10,10));
        vbox.getChildren().addAll(grid,testBtn);
        var scene = new Scene(vbox,350,350);
        stage.setScene(scene);
        
        stage.setResizable(false);
        stage.setTitle("Mini Sudoku!");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Sudoku.jpg")));
        stage.show();
    }

    private HBox createButtonBox()
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
            if (selectedRow>=0 && selectedCol>=0)
            {
                initMiniSudoku("cell");
            }
            else
            {
                System.out.println("No cell selected");
            }
        });

        //Solve Button
        Button solveBtn = new Button("Solve");
        solveBtn.setFont(textFont);

        solveBtn.setOnAction((ActionEvent event) -> {
            initMiniSudoku("board");
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
        
        GridPane grid = create9x9Grid();
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