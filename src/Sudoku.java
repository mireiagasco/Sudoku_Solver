import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {

    private int[][] sudoku;
    private List<int[][]> solucions;
    private int nRes=0;
    private int[][] sudokuSol;
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
    public int backtrackingSolver( int row, int col){

        assignacio(0, 0);
        if (solucions.isEmpty()) return 0;
        else return 1;
    }

    private void assignacio(int row, int col) {
        if (col == 0) {
            sudokuSol = sudoku.clone();
        }
            for (int num = 1; num < 10; num++) {
                if (col < 8 && row < 8) {
                    if (isSafe(row, col, num)) {
                        assignacio(row, col+1);
                        if(sudokuSol[row][col]==0){
                            sudokuSol[row][col] = num;
                        }
                    } else {
                        if (sudokuSolved()) {
                            solucions.add(sudokuSol);
                            nRes++;
                        }
                    }
                }
            }
    }


    private boolean sudokuSolved(){
        for(int j=0; j<9; j++){
            for (int i=0; i<9; i++){
                if(sudokuSol[j][i]==0){
                    return false;
                }
            }
        }
        return true;
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    private boolean isSafe(int row, int col,
                          int num)
    {

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x <= 8; x++)
            if (sudokuSol[row][x] == num)
                return false;

        // Check if we find the same num
        // in the similar column ,
        // we return false
        for (int x = 0; x <= 8; x++)
            if (sudokuSol[x][col] == num)
                return false;

        // Check if we find the same num
        // in the particular 3*3
        // matrix, we return false
        int startRow = row - row % 3, startCol
                = col - col % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (sudokuSol[i + startRow][j + startCol] == num)
                    return false;

        return true;
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

    public String printSoluc(){
        String str="";
        for(int i=0; i<solucions.size();i++){
           str+= printSudoku(solucions.get(i));
        }
        return str;
    }

    public String printSudoku(int[][] sudoku){

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
