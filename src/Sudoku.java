import java.util.*;
import java.util.stream.Collectors;

public abstract class Sudoku {

    private int[][] sudoku;

    public Sudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public abstract int solve();

    /**
     * Makes a copy of the original sudoku
     * @return int[][] with a copy of the sudoku
     */
    protected int[][] sudokuCopy(){
        int[][] newSudoku = new int[9][9];

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                newSudoku[i][j] = this.sudoku[i][j];
            }
        }

        return newSudoku;
    }

    /**
     * Getter for the sudoku
     * @return the original sudoku (int[][])
     */
    public int[][] getSudoku() {
        return sudoku;
    }


    protected String printSudoku(int[][] sudoku){

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
