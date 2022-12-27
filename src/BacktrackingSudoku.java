import java.util.ArrayList;
import java.util.List;

public class BacktrackingSudoku extends Sudoku {
    private List<int[][]> solucions;
    private int nRes=0;
    private int[][] sudokuSol;

    public BacktrackingSudoku(int[][] sudoku) {
        super(sudoku);
        solucions = new ArrayList<>();
    }


    /**
     * Finds all possible solutions to the sudoku
     * @return 0 if any solution was found, 1 if it was not.
     */
    @Override
    public int solve() {
        sudokuSol = sudokuCopy();
        return assignacio(0, 0);
    }

    private int assignacio(int row, int col) {
        /*if we have reached the 8th
           row and 9th column (0
           indexed matrix) ,
           we are returning true to avoid further
           backtracking       */
        if (row == 8 && col > 8)
            return 1;

        // Check if column value  becomes 9 ,
        // we move to next row
        // and column start from 0
        if (col == 9) {
            row++;
            col = 0;
        }

        // Check if the current position
        // of the grid already
        // contains value >0, we iterate
        // for next column
        if (sudokuSol[row][col] != 0)
            return assignacio( row, col + 1);

        for (int num = 1; num < 10; num++) {

            // Check if it is safe to place
            // the num (1-9)  in the
            // given row ,col ->we move to next column
            if (isSafe( row, col, num)) {

                /*  assigning the num in the current
                (row,col)  position of the grid and
                assuming our assigned num in the position
                is correct */
                sudokuSol[row][col] = num;

                // Checking for next
                // possibility with next column
                if (assignacio( row, col + 1)==1)
                    return 1;
            }
            /* removing the assigned num , since our
               assumption was wrong , and we go for next
               assumption with diff num value   */
            sudokuSol[row][col] = 0;
        }
        return 0;
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
    public String printSoluc(){
        String str="";
        for(int i=0; i<solucions.size();i++){
            str+= printSudoku(solucions.get(i));
        }
        return str;
    }

    public int[][] getSolution() {
        return sudokuSol;
    }

    @Override
    public String toString() {

        String text = "\nOriginal Sudoku:\n";
        text += printSudoku(super.getSudoku());

        text += "\n\n\nSolution:\n";
        text += printSudoku(sudokuSol);

        return text;
    }
}
