import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {

    private int[][] sudoku;
    private List<int[][]> solucions;

    public Sudoku(int[][] sudoku) {
        this.sudoku = sudoku;
        this.solucions = new ArrayList<>();
    }

    /**
     * Tries to find a solution to the sudoku
     * @return 0 if a solution was found, 1 if it was not.
     */
    public int greedySolver(){

        return 0;
    }

    /**
     * Finds all possible solutions to the sudoku
     * @return 0 if any solution was found, 1 if it was not.
     */
    public int backtrackingSolver(){


        return 0;
    }


    @Override
    public String toString() {

        String text = "\nOriginal Sudoku:\n";
        text += printSudoku(sudoku);

        if (solucions.size() > 0){
            for (int[][] sudoku : solucions){
                text += "\n\n" + printSudoku(sudoku);
            }
        }
        return text;
    }


    private String printSudoku(int[][] sudoku){

        String text = "";

        for (int i = 0; i < 9; i++) {
            text += ("\n");
            if (i % 3 == 0)
                text += ("\n");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0){
                    text += ("   ");
                }
                if (sudoku[i][j] == 0){
                    text += "_";
                }
                else {
                    text += sudoku[i][j];
                }
                text += " ";
            }
        }

        return text;
    }
}
