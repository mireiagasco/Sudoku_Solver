import java.util.*;
import java.util.stream.Collectors;

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

        int[][] solution = sudokuCopy();
        int iniZeros = countZeros(solution);
        int currZeros = iniZeros;

        do {
            fillSudoku(solution);
            iniZeros = currZeros;
            currZeros = countZeros(solution);

        }while (currZeros < iniZeros);

        solucions.add(solution);
        if (currZeros == 0) return 0;
        else return 1;
    }

    /**
     * Counts how many blank spaces has the sudoku
     * @param board - sudoku
     * @return number of empty cells
     */
    private int countZeros(int[][] board){
        int numZeros = 0;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (board[i][j] == 0) numZeros++;
            }
        }
        return numZeros;
    }

    /**
     * Fills the blanks that only have one viable option
     * @param board sudoku
     */
    private void fillSudoku(int[][] board){

        Map<Integer, Integer> usedNumbers = new HashMap<>();
        for (int i = 1; i < 10; i++){
            usedNumbers.put(i, 0);
        }

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){

                if (board[i][j] == 0){

                    //reset number map
                    for (int n = 1; n < 10; n++){
                        usedNumbers.put(n, 0);
                    }

                    //get numbers that are not valid
                    getSquareNumbers(board, i, j, usedNumbers);
                    getColumnNumbers(board, j, usedNumbers);
                    getRowNumbers(board, i, usedNumbers);

                    //count how many options are left
                    int options = (int) usedNumbers.values().stream()
                            .filter(value -> value == 0)
                            .count();

                    //if only one number is valid, assign it
                    if (options == 1){
                        board[i][j] = usedNumbers.entrySet().stream()
                                .filter(entry -> entry.getValue() == 0)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList())
                                .get(0);
                    }
                }

            }
        }
    }

    /**
     * Get the numbers already placed in a sudoku square and
     * add them to map (mark them as used assignin 1 to its value).
     * The i,j parameters indicate the coordinates of a cell in
     * the square we want to evaluate.
     * @param board sudoku
     * @param i row of the cell
     * @param j column of the cell
     * @param numbers map of numbers
     */
    private void getSquareNumbers(int[][] board, int i, int j, Map<Integer, Integer> numbers){

        //determine boundaries of the square
        int minRow = i - (i % 3);
        int maxRow = minRow + 2;
        int minCol = j - (j % 3);
        int maxCol = minCol + 2;

        //obtain numbers
        for (j = minRow; j <= maxRow; j++){
            for (i = minCol; i <= maxCol; i++){
                if (board[j][i] != 0){
                    numbers.put(board[j][i], 1);
                }
            }
        }
    }

    /**
     * Get the numbers already used in a column and
     * add them to a map (mark them as used assigning
     * 1 to its value)
     * @param board sudoku
     * @param column column we want to evaluate
     * @param numbers map with the numbers
     */
    private void getColumnNumbers(int[][] board, int column, Map<Integer, Integer> numbers){
        for (int i = 0; i < 9; i++){
            if (board[i][column] != 0){
                numbers.put(board[i][column], 1);
            }
        }
    }

    /**
     * Get the numbers already used in a row and
     * add them to a map (mark them as used assigning
     * 1 to its value)
     * @param board sudoku
     * @param row row we want to evaluate
     * @param numbers map of numbers
     */
    private void getRowNumbers(int[][] board, int row, Map<Integer, Integer> numbers){
        for (int i = 0; i < 9; i++){
            if (board[row][i] != 0){
                numbers.put(board[row][i], 1);
            }
        }
    }


    /**
     * Finds all possible solutions to the sudoku
     * @return 0 if any solution was found, 1 if it was not.
     */
    private int backtrackingSolver(){


        return 0;
    }


    /**
     * Makes a copy of the original sudoku
     * @return int[][] with a copy of the sudoku
     */
    private int[][] sudokuCopy(){
        int[][] newSudoku = new int[9][9];

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                newSudoku[i][j] = this.sudoku[i][j];
            }
        }

        return newSudoku;
    }

    @Override
    public String toString() {

        String text = "\nOriginal Sudoku:\n";
        text += printSudoku(sudoku);

        text += "\n\n\nSolutions:\n";
        if (solucions.size() > 0){
            for (int[][] sudoku : solucions){
                text += printSudoku(sudoku);
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
