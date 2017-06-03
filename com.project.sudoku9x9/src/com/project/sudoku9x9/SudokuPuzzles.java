package com.project.sudoku9x9;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SudokuPuzzles {
	private static SudokuTable[] puzzlesTables = new SudokuTable[4];
	private static int tableNum = (int) (Math.random() * puzzlesTables.length);
	
	public void setTableNum(int num){
		SudokuPuzzles.tableNum = num;
	}
	
	public SudokuTable callPuzzle(int input){
		createPuzzles();
		return puzzlesTables[input];
	}


	public SudokuTable createPuzzles(){
		SudokuTable table = puzzlesTables[tableNum];
		tableNum = (tableNum + 1) % puzzlesTables.length;
		return table;
	}
	
	public SudokuPuzzles(){
		for (int i = 0; i < puzzlesTables.length; i++) {
			puzzlesTables[i] = new SudokuTable();
		}
		String txtfiles[] = {"samplesudoku1.txt", "samplesudoku2.txt", "samplesudoku4.txt", "samplesudoku3.txt"};
		for (int i = 0; i < puzzlesTables.length; i++) {
			puzzlesTables[i] = new SudokuTable();
			
			int sudokuPuzzle[][] = getPuzzleMatrix(txtfiles[i]);
			for (int row = 0; row < 9; row++){
				for (int col = 0; col < 9; col ++){
					if (sudokuPuzzle[row][col] != 0)
						puzzlesTables[i].getNumber(col, row).setAnswer(sudokuPuzzle[row][col]);
					else
						puzzlesTables[i].getNumber(col, row).setAnswer(0);
						
				}
			}
		}
	}
	


	
	private int[][] getPuzzleMatrix(String txt){
		int sudokuNumber[][] = new int[9][9];
		try{
			Scanner sc = new Scanner(new File(txt));
			
			while(sc.hasNext())
				for (int row = 0; row < 9; row++){
					for (int col = 0; col < 9; col ++){
						sudokuNumber[row][col] = sc.nextInt();
					}
					}
			sc.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return sudokuNumber;
	}
}

