import java.util.*;
import java.util.stream.Collectors;

public class Sudoku {

    private int[][] sudoku;
    private List<int[][]> solutions;

    public Sudoku(int[][] sudoku) {
        this.sudoku = sudoku;
        this.solutions = new ArrayList<>();
    }

    /**
     * Tries to find a solution to the sudoku
     * @return 0 if a solution was found, 1 if it was not.
     */
    public int greedySolver(){

        Map<Integer, List<Integer>> blanksInEachBlock = new HashMap<>();
        blanksInEachBlock.put(0, new ArrayList<>());
        blanksInEachBlock.put(1, new ArrayList<>());
        blanksInEachBlock.put(2, new ArrayList<>());
        blanksInEachBlock.put(10, new ArrayList<>());
        blanksInEachBlock.put(11, new ArrayList<>());
        blanksInEachBlock.put(12, new ArrayList<>());
        blanksInEachBlock.put(20, new ArrayList<>());
        blanksInEachBlock.put(21, new ArrayList<>());
        blanksInEachBlock.put(22, new ArrayList<>());

        int[][] solution = sudokuCopy();
        int iniZeros = countZeros(solution, blanksInEachBlock);
        int currZeros = iniZeros;

        do {
            fillSudoku(solution, blanksInEachBlock);
            iniZeros = currZeros;
            currZeros = countZeros(solution, blanksInEachBlock);

        }while (currZeros < iniZeros);

        solutions.add(solution);

        if (currZeros > 0) return 1;
        else return 0;
    }

    /**
     * Counts how many blank spaces has the sudoku
     * @param board - sudoku
     * @return number of empty cells
     */
    private int countZeros(int[][] board, Map<Integer, List<Integer>> blanksInEachBlock){
        int numZeros = 0;
        int blockIndex = 0;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (board[i][j] == 0){
                    numZeros++;
                    blockIndex = getBlockIndex(i, j);
                    blanksInEachBlock.get(blockIndex).add(getCellIndex(i, j));
                }
            }
        }
        return numZeros;
    }

    /**
     * Calculates the index of the bloc given a cell in that block
     * @param row row index of the cell
     * @param column column index of the cell
     * @return the index of the block containing the cell
     *          Possible indexs: 00,01,02,10,11,12,20,21,22
     */
    private int getBlockIndex(int row, int column){
        return (column / 3 + 10 * (row / 3));
    }

    /**
     * Calculates the global index of the cell
     * @param row row index
     * @param column row column
     * @return index of the cell
     */
    private int getCellIndex(int row, int column){
        return row * 9 + column;
    }

    /**
     * Fills the blanks that only have one viable option
     * @param board sudoku
     */
    private void fillSudoku(int[][] board, Map<Integer, List<Integer>> blanksInEachBlock){

        Map<Integer, Integer> viableNumbers;

        //for each empty cell in the sudoku
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){

                if (board[i][j] == 0){

                    viableNumbers = getViableFits(board, getCellIndex(i, j));

                    //count how many numbers can be placed in the cell
                    int options = (int) viableNumbers.values().stream()
                            .filter(value -> value == 1)
                            .count();

                    //if only one number is valid, assign it
                    if (options == 1){
                        board[i][j] = viableNumbers.entrySet().stream()
                                .filter(entry -> entry.getValue() == 1)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList())
                                .get(0);
                        int blockIndex = getBlockIndex(i, j);
                        int cellIndex = getCellIndex(i,j);
                        cellIndex = blanksInEachBlock.get(blockIndex).indexOf(cellIndex);
                        blanksInEachBlock.get(blockIndex).remove(cellIndex);
                    }

                    //if more than one number is possible, check if
                    //there's any number that can only be placed in
                    //the cell (considering the square)
                    else{
                        int number = checkIfAnyNumberMustBePlacedInCell(board, i, j, blanksInEachBlock);
                        if (number != -1){
                            board[i][j] = number;

                            int blockIndex = getBlockIndex(i, j);
                            blanksInEachBlock.get(blockIndex).remove(new Integer[]{i,j});
                        }
                    }
                }
            }
        }
    }

    /**
     * Given a cell, checks if it must contain a value,
     * taking into account all the values of the square.
     * @param board sudoku
     * @param row row index of the cell
     * @param column column index of the cell
     * @param blanksInEachBlock map indicating all blank cells in each square
     * @return -1 if more than one value fits, a number from 1-9 if it is the only possible fit
     */
    private int checkIfAnyNumberMustBePlacedInCell(int[][] board, int row, int column, Map<Integer, List<Integer>> blanksInEachBlock){

        int blockIndex = getBlockIndex(row, column);    //index of the square
        List<Integer> blanksInBlock = blanksInEachBlock.get(blockIndex);  //list of blank cells
        Map<Integer, Set<Integer>> fitsPerBlank = new HashMap<>();        //map of blank cells with possible numbers
        Map<Integer, Integer> viableNumbers;
        Set<Integer> numbers;
        Set<Integer> cellPosibilities = new HashSet<>();

        //for each blank cell in the square
        for (Integer cell : blanksInBlock){
            fitsPerBlank.put(cell, new HashSet<>());

            //get numbers that can be placed in the cell
            //and add them to the map
            viableNumbers = getViableFits(board, cell);
            numbers = viableNumbers.entrySet().stream()
                                    .filter(entry -> entry.getValue() == 1)
                                    .map(Map.Entry::getKey)
                                    .collect(Collectors.toSet());

            for (Integer number : numbers){

                if (cell == getCellIndex(row, column)) cellPosibilities.add(number);
                else fitsPerBlank.get(cell).add(number);
            }
        }

        int fit = 0;
        int numberOfFits = 0;

        for (int number : cellPosibilities){
            int count = (int)fitsPerBlank.values().stream()
                    .filter(set -> set.contains(number))
                    .count();

            if (count == 0){
                numberOfFits++;
                fit = number;
            }
        }

        if (numberOfFits == 1) return fit;
        else return -1;
    }

    /**
     * Calculates which numbers can be placed in a cell
     * @param board sudoku
     * @param cell index from the cell we need to work with
     * @return a map indicating which numbers can be placed in the cell
     *          0 -> the number cannot be placed in the cell
     *          1 -> the number can be placed in the cell
     */
    private Map<Integer, Integer> getViableFits(int[][] board, int cell){
        Map<Integer, Integer> viableFits = new HashMap<>();
        Set<Integer> usedNumbers = new HashSet<>();

        //get row and column from cell
        int row = cell / 9;
        int column = cell % 9;


        //get numbers that are not valid for the cell
        usedNumbers.addAll(getSquareNumbers(board, row, column));
        usedNumbers.addAll(getColumnNumbers(board, column));
        usedNumbers.addAll(getRowNumbers(board, row));

        for (int i = 1; i <= 9; i++){
            if (usedNumbers.contains(i)) viableFits.put(i, 0);
            else viableFits.put(i, 1);
        }

        return viableFits;
    }


    /**
     * Get the numbers already placed in a sudoku square and
     * add them to map (mark them as used assignin 1 to its value).
     * The i,j parameters indicate the coordinates of a cell in
     * the square we want to evaluate.
     * @param board sudoku
     * @param i row of the cell
     * @param j column of the cell
     * @return a set with the numbers already used in the square
     */
    private Set<Integer> getSquareNumbers(int[][] board, int i, int j){

        Set<Integer> numbers = new HashSet<>();

        //determine boundaries of the square
        int minRow = i - (i % 3);
        int maxRow = minRow + 2;
        int minCol = j - (j % 3);
        int maxCol = minCol + 2;

        //obtain numbers
        for (j = minRow; j <= maxRow; j++){
            for (i = minCol; i <= maxCol; i++){
                if (board[j][i] != 0){
                    numbers.add(board[j][i]);
                }
            }
        }
        return numbers;
    }

    /**
     * Get the numbers already used in a column and
     * add them to a map (mark them as used assigning
     * 1 to its value)
     * @param board sudoku
     * @param column column we want to evaluate
     * @return a set with the numbers already used in the column
     */
    private Set<Integer> getColumnNumbers(int[][] board, int column){

        Set<Integer> numbers = new HashSet<>();

        for (int i = 0; i < 9; i++){
            if (board[i][column] != 0){
                numbers.add(board[i][column]);
            }
        }
        return numbers;
    }

    /**
     * Get the numbers already used in a row and
     * add them to a map (mark them as used assigning
     * 1 to its value)
     * @param board sudoku
     * @param row row we want to evaluate
     * @return a set with the numbers already used in the row
     */
    private Set<Integer> getRowNumbers(int[][] board, int row){

        Set<Integer> numbers = new HashSet<>();

        for (int i = 0; i < 9; i++){
            if (board[row][i] != 0){
                numbers.add(board[row][i]);
            }
        }

        return numbers;
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

    /**
     * Getter for the first solution found
     * @return a solution for the sudoku (int[][])
     */
    public int[][] getFirstSolution(){
        return this.solutions.get(0);
    }

    /**
     * Getter for the sudoku
     * @return the original sudoku (int[][])
     */
    public int[][] getSudoku() {
        return sudoku;
    }

    @Override
    public String toString() {

        String text = "\nOriginal Sudoku:\n";
        text += printSudoku(sudoku);

        text += "\n\n\nSolutions:\n";
        if (solutions.size() > 0){
            for (int[][] sudoku : solutions){
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
