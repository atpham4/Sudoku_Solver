package com.project.sudoku9x9;

import java.util.ArrayList;



public class SudokuTable {
	
	private SudokuNumber[][] table = new SudokuNumber[9][9];
		
	public SudokuTable(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){
				table[row][col] = new SudokuNumber(row,col);
			}
		}
	}
	
	public SudokuNumber getNumber(int row, int col){
		if (row >= 0 && row <= 8 && col >= 0 && col <= 8)
			return table[row][col];
		else
			throw new IndexOutOfBoundsException("Table row and col values are integers from 0 to 8");
	}
	
	
	boolean checkNumber(SudokuNumber digit) {
		if (!digit.isSolved())
			return true; 
		
        for (int row = 0; row < 9; row++)
            if (digit.getAnswer() == table[row][digit.getCol()].getAnswer() && digit.getRow() != row)
                return false;

        for (int col = 0; col < 9; col++) 
            if (digit.getAnswer() == table[digit.getRow()][col].getAnswer() && digit.getCol() != col)
                return false;

        int startPosX = (digit.getRow()/3)*3;
		int startPosY = (digit.getCol()/3)*3;
		for (int col = startPosY; col < startPosY + 3; col++){
			for (int row = startPosX; row < startPosX + 3; row++){
				if (digit.getAnswer() == table[row][col].getAnswer() && digit.getRow() != row && digit.getCol() != col)
					return false;
			}
		}
        return true;
    }

	
	boolean solve(int position) {
		if (position > 81)
	    	return true;
		
		int row = (position-1)%9;
		int col = (position-1)/9;
		
	    if (table[row][col].isSolved())
	    	return solve(position+1);
	    
	    ArrayList<Integer> posssibleValues = new ArrayList<Integer>(table[row][col].getPossibleValues()); 
	    for (int possibleValue : posssibleValues) {
	    	SudokuNumber digit = new SudokuNumber(table[row][col]); 
	    	digit.setAnswer(possibleValue);
		    if (checkNumber(digit)) { 
		        table[row][col] = digit; 
		       	if (solve(position+1)) 
		       		return true;
		    }
	    }
	    table[row][col].setAnswer(0); 
	    return false;
	}
	
	
	public int getNumOfNumbersSolved(){
		int numOfNumbersSolved = 0;
		for (SudokuNumber[] column : table){
			for (SudokuNumber sudokuNumber : column){
				if (sudokuNumber.isSolved())
					numOfNumbersSolved += 1;
			}
		}
		return numOfNumbersSolved;
	}
	
	public boolean checkOK(){
		for (SudokuNumber[] column : table){
			for (SudokuNumber sudokuNumber : column){
				if (checkNumber(sudokuNumber) && sudokuNumber.getAnswer() >= 0 && sudokuNumber.getAnswer() <= 9)
					continue;
				return false;
			}
		}
		return true;
	}
	
	public void setSolvedToSafe(){
		for (SudokuNumber[] column : table){
			for (SudokuNumber sudokuNumber : column){
				if (sudokuNumber.isSolved())
					sudokuNumber.setSafe(true);
			}
		}
	}
	
	public String toString(){
		String str = "";
		for (int col = 0; col < 9; col++){
			for (int row = 0; row < 9; row++){
				str = str + table[row][col].getAnswer() + " ";
			}
			str = str + "\n";
		}
		return str;
	}

}