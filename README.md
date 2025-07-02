# Sudoku Solver

A desktop application to solve sudoku puzzles... but you have to solve a mini 4x4 sudoku puzzle before you get your solution!

### This project was made for the Hack Club Athena Award.

After summer break started and I caught myself doing sudoku puzzles until midnight, I had the great idea to tie together my problem-solving hobbies of coding and doing puzzles! Yet, there are already so many sudoku solvers out there, and some extremely intricate, like [this one by Andrew Stuart](https://www.sudokuwiki.org/sudoku.htm), which covers lots of complex sudoku-solving techniques, many of which I've never even heard of. I wanted to make something unique and fun, and what better than a sudoku solver where you still have to work for the solution? 


This was my first time developing a desktop application and using JavaFX and Maven build tools, so I followed along [this tutorial](https://www.youtube.com/watch?v=wa4ky1ARDkw&list=PLix7MmR3doRqF712ItSp4IhKwJcvDf5M2) by Professor Donald J. Patterson at Westmont College and put my own twist on things! In addition to the mini sudoku mechanic, I also ...

- Added arrow key navigation
- Added a "Solve Cell" button
- Limited textfield character input
    - You'll only be able to type digits 1-9 and should only be able to type in 1 character at a time
    - If another digit is already in a box, typing another digit will easily replace it
- Modified the appearance of the sudoku grid & other UI
- Converted code to an executable file
    -This repository includes a windows installer
- Fixed other minor logic, organization, and UI issues
