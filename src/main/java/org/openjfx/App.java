package org.openjfx;

import org.openjfx.miniSudoku.MiniSolver;

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
    TextField[][] miniTextFields = new TextField[4][4];
    Solver s = new Solver();
    Board boardInput;
    Board solution;

    //Row Selection
    int mainSelectedRow = -1;
    int mainSelectedCol = -1;
    int selectedRow = -1;
    int selectedCol = -1;

    //Fonts
    Font textFont = Font.font("Helvetica");
    Font headerFont = Font.font("Helvetica", FontWeight.BOLD, 16);

    //test
    MiniSolver miniSolver = new MiniSolver();
    Board miniInput;
    Board miniSolution;

    //styletextfield


    private void clearBoard()
    {
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {   
                UI.styleTextField(textFields,c, r, "white");
                textFields[c][r].setText("");
            } 
        }
        solution = null; //Reset the solution
    }

    private void setSolution()
    {
        boardInput = UI.getBoardFromTextFields(textFields);
        solution = s.solve(boardInput);
    }

    //test
    private void setMiniSolution()
    {
        miniInput = UI.getMiniFromTextFields(miniTextFields);
        miniSolution = miniSolver.solve(miniInput);
    }

    private void solveSelectedCell()
    {
        setSolution();

        //Updating the selected cell with the solution
        if (mainSelectedRow >= 0 && mainSelectedCol >= 0)
        {
            if(solution != null)
            { 
                UI.styleTextField(textFields,mainSelectedCol, mainSelectedRow, "green");
                textFields[mainSelectedCol][mainSelectedRow].setText(""+solution.get(mainSelectedCol,mainSelectedRow));  
            } 
            else
            {
                //No solution available;
                UI.styleTextField(textFields,mainSelectedCol, mainSelectedRow, "red");
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
                UI.styleTextField(textFields,c,r,"white");
                if(solution!= null)
                {     
                    if(textFields[c][r].getText().trim().equals(""))
                    {
                        UI.styleTextField(textFields,c, r, "green");
                        textFields[c][r].setText(""+solution.get(c,r));
                    }
                }
                else
                {
                    UI.styleTextField(textFields,c, r, "red");
                }
            }
        }
    }

    //boardreterival methods

    private GridPane createGrid(TextField[][] fields, int gridLength)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

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
                    int cellSize = 30;
                    t.setPrefSize(cellSize, cellSize);
                    t.setMaxSize(cellSize, cellSize);
                    grid.setPadding(new Insets(10,100,10,100));
                }
                else //9x9 Board Properties
                {
                    grid.setPadding(new Insets(10,40,10,40));
                }

                //Limiting character input
                t.setTextFormatter(new TextFormatter<String>(change ->
                {
                    String newText = change.getControlNewText();
                    if(newText.matches("[1-"+gridLength+"]?")) {return change;}
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
                final int row = r;
                final int col = c;
                t.setOnKeyPressed(event -> {
                    if(event.getCode().isArrowKey())
                    {
                        switch(event.getCode())
                        {
                            case UP:
                                if (row > 0) fields[col][row-1].requestFocus();
                                break;
                            case DOWN:
                                if (row<gridLength-1) fields[col][row+1].requestFocus();
                                break;
                            case LEFT:
                                if (col > 0) fields[col-1][row].requestFocus();
                                break;
                            case RIGHT:
                                if (col < gridLength-1) fields[col+1][row].requestFocus();
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
                        UI.styleTextField(fields,selectedCol, selectedRow, "grey");
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

    private void initMiniSudoku(String toSolve) //toSolve = cell, board
    {
        Stage stage = new Stage();

        GridPane grid = createGrid(miniTextFields, 4);
        
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
            
            
            setMiniSolution();
            for(int r = 0; r<4; r++)
            {
                for(int c = 0; c<4; c++)
                {               
                    UI.styleTextField(miniTextFields,c,r,"white");
                    if(miniSolution!= null)
                    {     
                        if(miniTextFields[c][r].getText().trim().equals(""))
                        {
                            UI.styleTextField(miniTextFields,c, r, "green");
                            miniTextFields[c][r].setText(""+miniSolution.get(c,r));
                        }
                    }
                    else
                    {
                        UI.styleTextField(miniTextFields,c, r, "red");
                    }
                }
            }
            
        });

        //Instruction Text
        Label textLabel = new Label("Solve this mini sudoku to get your solution!");
        textLabel.setFont(textFont);
        textLabel.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(textLabel,grid,testBtn);
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
            if(selectedRow>=0 && selectedCol>=0)
            {
                mainSelectedRow = selectedRow;
                mainSelectedCol = selectedCol;
                initMiniSudoku("cell");
            }
            //else{System.out.println("No cell selected");}
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
        textLabel.setAlignment(Pos.CENTER);
        
        GridPane grid = createGrid(textFields, 9);
        HBox buttonBox = createButtonBox();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25,25,25,25));
        vbox.getChildren().addAll(textLabel, grid, buttonBox);

        setStage(stage, vbox);
        vbox.requestFocus(); //Removing initial focus from text fields
    }


    public static void main(String[] args) {
        launch();
    }

}