import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        System.out.println("\nSudokus facils:\n");

        File file1 = new File("src/sudokuFiles/easy.txt");
        List<Sudoku> sudokuList = readSudokuFromFile(file1,0);

        System.out.println("\n--------------- Greedy ---------------\n");
        for (int i = 0; i < sudokuList.size(); i++){
            System.out.println("\n--> Sudoku #" + i + "\n");
            Sudoku sudoku = sudokuList.get(i);
            sudoku.solve();

            System.out.println(sudoku);
        }

        sudokuList = readSudokuFromFile(file1, 1);

        System.out.println("\n--------------- Backtracking ---------------\n");
        for (int i = 0; i < sudokuList.size(); i++){
            System.out.println("\n--> Sudoku #" + i + "\n");
            Sudoku sudoku = sudokuList.get(i);
            sudoku.solve();
            System.out.println(sudoku);
        }


        System.out.println("\nSudokus dificils:\n");

        File file2 = new File("src/sudokuFiles/medium.txt");
        List<Sudoku> sudokuList2 = readSudokuFromFile(file2,0);

        System.out.println("\n--------------- Greedy ---------------\n");
        for (int i = 0; i < sudokuList2.size(); i++){
            System.out.println("\n--> Sudoku #" + i + "\n");
            Sudoku sudoku = sudokuList2.get(i);
            sudoku.solve();

            System.out.println(sudoku);
        }

        sudokuList = readSudokuFromFile(file1, 1);

        System.out.println("\n--------------- Backtracking ---------------\n");
        for (int i = 0; i < sudokuList.size(); i++){
            System.out.println("\n--> Sudoku #" + i + "\n");
            Sudoku sudoku = sudokuList.get(i);
            sudoku.solve();
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
                for (String datum : data) {

                    board[j][k] = Integer.parseInt(datum);
                    k++;
                    if (k >= 9) {
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
            e.printStackTrace();
        }

        return sudokuList;
    }

}
