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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    //test
    MiniSolver miniSolver = new MiniSolver();
    Board miniInput;

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

    private boolean miniComplete()
    {
        for(int r = 0; r<4; r++)
        {
            for(int c = 0; c<4; c++)
            {
                if(miniTextFields[c][r].getText().trim().equals(""))
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateSelectedCell(int c, int r)
    {
        selectedRow = r;
        selectedCol = c;
    }

    private void initMiniSudoku(String toSolve) //toSolve = cell, board
    {
        Stage stage = new Stage();

        GridPane grid = UI.createGrid(miniTextFields, 4, this::updateSelectedCell);
        UI.fillMiniBoard(miniTextFields);

        for(int r = 0; r<4; r++)
        {
            for(int c = 0; c<4; c++)
            {
                miniTextFields[c][r].textProperty().addListener((obs, oldVal, newVal) -> {
                    if(miniComplete())
                    {
                        //Checking if the board is correct
                        miniInput = UI.getMiniFromTextFields(miniTextFields);
                        if(miniSolver.validBoard(miniInput))
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
                        }

                    }
                });
            }
        }

        //Instruction Text
        Label textLabel = new Label("Solve this mini sudoku to get your solution!");
        textLabel.setFont(UI.textFont);
        textLabel.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(textLabel,grid);
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
        clearBtn.setFont(UI.textFont);

        clearBtn.setOnAction((ActionEvent event) -> {
            clearBoard();
        });

        //Solve Cell Button
        Button solveCellBtn = new Button("Solve Cell");
        solveCellBtn.setFont(UI.textFont);

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
        solveBtn.setFont(UI.textFont);
        solveBtn.setOnAction((ActionEvent event) -> {
            initMiniSudoku("board");
        });

        //Adding buttons to the button box
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(clearBtn, solveCellBtn, solveBtn);
        buttonBox.setAlignment(Pos.CENTER);

        return buttonBox;
    }

    @Override
    public void start(Stage stage) {
        Label textLabel = new Label("Sudoku Solver");
        textLabel.setFont(UI.headerFont);
        textLabel.setAlignment(Pos.CENTER);
        
        GridPane grid = UI.createGrid(textFields, 9, this::updateSelectedCell);
        HBox buttonBox = createButtonBox();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25,25,25,25));
        vbox.getChildren().addAll(textLabel, grid, buttonBox);

        var scene = new Scene(vbox,400,500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sudoku Solver");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Sudoku.jpg")));
        stage.show();
        vbox.requestFocus();
    }


    public static void main(String[] args) {
        launch();
    }

}