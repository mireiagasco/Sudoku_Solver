import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        File file1 = new File("src/sudokuFiles/medium.txt");
        List<Sudoku> sudokuList = readSudokuFromFile(file1,0);
        File file2 = new File("src/sudokuFiles/mediumSolutions.txt");
        List<Sudoku> sudokuSolutions = readSudokuFromFile(file2,0);


        System.out.println("\n--------------- Greedy ---------------\n");
        for (int i = 0; i < sudokuList.size(); i++){
            System.out.println("\n--------------- Sudoku #" + i + " ---------------\n");
            Sudoku sudoku = sudokuList.get(i);
            int result = sudoku.solve();

            System.out.println(sudoku);
        }

        sudokuList = readSudokuFromFile(file1, 1);
        sudokuSolutions = readSudokuFromFile(file2,1);

        System.out.println("\n--------------- Backtracking ---------------\n");
        for (int i = 0; i < sudokuList.size(); i++){
            System.out.println("\n--------------- Sudoku #" + i + " ---------------\n");
            Sudoku sudoku = sudokuList.get(i);
            int result = sudoku.solve();
            System.out.println(sudoku);
        }
    }

    public static List<Sudoku> readSudokuFromFile(File file,int type){

        List<Sudoku> sudokuList = new ArrayList<>();
        String[] data;
        int j, k;


        try {

            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()){
                int[][] board = new int[9][9];
                j = 0;
                k = 0;

                data = fileReader.nextLine().split(",");
                for (int i = 0; i < data.length; i++){

                    board[j][k] = Integer.valueOf(data[i]);
                    k++;
                    if (k >= 9){
                        k = 0;
                        j++;
                    }
                }
                Sudoku newSudoku;
                if (type == 0) {
                    newSudoku = new GreedySudoku(board);
                }
                else {
                    newSudoku = new BacktrackingSudoku(board);
                }
                sudokuList.add(newSudoku);
            }

        }catch (FileNotFoundException e){
            System.out.println(e);
        }

        return sudokuList;
    }

}
