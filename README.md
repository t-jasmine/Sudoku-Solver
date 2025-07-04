# Sudoku Solver
[![Athena Award Badge](https://img.shields.io/endpoint?url=https%3A%2F%2Faward.athena.hackclub.com%2Fapi%2Fbadge)](https://award.athena.hackclub.com?utm_source=readme)

A desktop application to solve sudoku puzzles... but you have to solve a mini 4x4 sudoku puzzle before you get your solution!

To start solving, run the App class, or type this in your terminal:

```
mvn clean javafx:run
```

If you want to run using the executable jar, type this in your terminal:

```
java -jar target/sudoku-solver-1.0-jar-with-dependencies.jar
```

If you run into missing JavaFX runtime components errors like I did, run this line instead:
```
java --module-path "C:\replace\this\with\your\path\to\javafx-sdk-21.0.7\lib" --add-modules javafx.controls -jar target/sudoku-solver-1.0-jar-with-dependencies.jar
```

Please note that if your solved board turns red, what you input does not have a valid solution.

## This project was made for the Hack Club Athena Award.

After summer break started and I caught myself doing sudoku puzzles until midnight, I had the great idea to tie together my problem-solving hobbies of coding and doing puzzles! Yet, there are already so many sudoku solvers out there, and some extremely intricate, like [this one by Andrew Stuart](https://www.sudokuwiki.org/sudoku.htm), which covers lots of complex sudoku-solving techniques, many of which I've never even heard of. I wanted to make something unique and fun, and what better than a sudoku solver where you still have to work for the solution? 

To make this project, I used JavaFX and Maven. This was my first time developing a desktop application and using JavaFX and Maven build tools, so I followed along [this video series](https://www.youtube.com/watch?v=wa4ky1ARDkw&list=PLix7MmR3doRqF712ItSp4IhKwJcvDf5M2) by Professor Donald J. Patterson at Westmont College and put my own twist on things! However, I didn't want to follow a tutorial step-by-step, so I deviated a little and started adding my own features. I thought it'd be fun to make an obstacle for you to tackle before getting the solution. In addition to the mini sudoku mechanic, I also ...

- Added arrow key navigation
- Added a "Solve Cell" button
- Limited textfield character input
    - You'll only be able to type digits 1-9 and should only be able to type in 1 character at a time
    - If another digit is already in a box, typing another digit will easily replace it
- Modified the appearance of the sudoku grid & other UI
- Fixed other minor logic, organization, and UI issues

This was an exciting first desktop app project! It was definitely a struggle trying to learn how to use different libraries and figuring out how everything works together. At first, I tried to create an executable file, but I ran into a lot of errors getting it to work on other computers, so I ditched that idea...I think I'll explore it in another project! This project took me more hours than I expected, considering its simplicity, but it was well worth it testing out different features of JavaFX throughout development.

### Screenshots

![sudoku1](https://github.com/user-attachments/assets/1a12337f-a7bc-446d-97c2-52e716f11972)

![minisudoku](https://github.com/user-attachments/assets/0268f32c-504a-434c-a51e-e76472f29add)

![sudoku2](https://github.com/user-attachments/assets/973b8cbe-7ccb-4777-af7e-6ad9a331d447)



