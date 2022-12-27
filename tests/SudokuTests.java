import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuTests {

    @Test
     void greedySolverEasyTests(){
        File file = new File("src/sudokuFiles/easy.txt");
        List<GreedySudoku> sudokuList = readSudokuFromFile(file);
        file = new File("src/sudokuFiles/easySolutions.txt");
        List<GreedySudoku> sudokuSolutions = readSudokuFromFile(file);

        for (int i = 0; i < sudokuList.size(); i++){
            if(sudokuList.get(i).solve() == 0){
                Assert.assertEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
            }
            else{
                Assert.assertNotEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
            }
        }
    }

    @Test
    void greedySolverMediumTests(){
        File file = new File("src/sudokuFiles/medium.txt");
        List<GreedySudoku> sudokuList = readSudokuFromFile(file);
        file = new File("src/sudokuFiles/mediumSolutions.txt");
        List<GreedySudoku> sudokuSolutions = readSudokuFromFile(file);

        for (int i = 0; i < sudokuList.size(); i++){
            if(sudokuList.get(i).solve() == 0){
                Assert.assertEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
            }
            else{
                Assert.assertNotEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
            }
        }
    }





    public static List<GreedySudoku> readSudokuFromFile(File file){

        List<GreedySudoku> sudokuList = new ArrayList<>();
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

                GreedySudoku newSudoku = new GreedySudoku(board);
                sudokuList.add(newSudoku);

            }

        }catch (FileNotFoundException e){
            System.out.println(e);
        }

        return sudokuList;
    }

    @Test
    void backtrackingSolverEasyTests(){
        File file2 = new File("src/sudokuFiles/easy.txt");
        List<BacktrackingSudoku> sudokuList = readSudokuBtFromFile(file2);
        file2 = new File("src/sudokuFiles/easySolutions.txt");
        List<BacktrackingSudoku> sudokuSolutions = readSudokuBtFromFile(file2);

        for (int i = 0; i < sudokuList.size(); i++){
            sudokuList.get(i).solve();
            Assert.assertEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
        }
    }

    @Test
    void backtrackingSolverMediumTests(){
        File file2 = new File("src/sudokuFiles/medium.txt");
        List<BacktrackingSudoku> sudokuList = readSudokuBtFromFile(file2);
        file2 = new File("src/sudokuFiles/mediumSolutions.txt");
        List<BacktrackingSudoku> sudokuSolutions = readSudokuBtFromFile(file2);

        for (int i = 0; i < sudokuList.size(); i++){
            sudokuList.get(i).solve();
            Assert.assertEquals(sudokuSolutions.get(i).getSudoku(), sudokuList.get(i).getSolution());
        }
    }
    public static List<BacktrackingSudoku> readSudokuBtFromFile(File file){

        List<BacktrackingSudoku> sudokuList = new ArrayList<>();
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

                BacktrackingSudoku newSudoku = new BacktrackingSudoku(board);
                sudokuList.add(newSudoku);

            }

        }catch (FileNotFoundException e){
            System.out.println(e);
        }

        return sudokuList;
    }


}

