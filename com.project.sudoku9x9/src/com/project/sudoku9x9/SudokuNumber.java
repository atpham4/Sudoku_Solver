package com.project.sudoku9x9;

import java.util.ArrayList;

public class SudokuNumber {
	private int answer;
	private int rowLocation;
	private int colLocation;
	private ArrayList<Integer> possibleValues = new ArrayList<Integer>();
	private boolean isSafe = false;

	SudokuNumber(int answer){
		this.answer = answer;
	}
	
	SudokuNumber(int rowLocation, int colLocation){
		for (int i = 0; i < 9; i++){
			possibleValues.add(i+1);
		}
		this.rowLocation = rowLocation;
		this.colLocation = colLocation;
	}
	
	SudokuNumber(SudokuNumber sudokuNumber){;
		this.answer = sudokuNumber.answer;
		this.rowLocation = sudokuNumber.rowLocation;
		this.colLocation = sudokuNumber.colLocation;
		this.possibleValues = sudokuNumber.possibleValues;
		this.isSafe = sudokuNumber.isSafe;
	}

	
	public ArrayList<Integer> getPossibleValues(){
		return possibleValues;
	}
	
	public boolean isSolved(){
		return (answer != 0);
	}
	
	public boolean isSafe(){
		return isSafe;
	}
	
	public int getAnswer(){
		return answer;
	}
	
	public int getRow(){
		return rowLocation;
	}
	
	public int getCol(){
		return colLocation;
	}
	
	public void setSafe(boolean isSafe){
		this.isSafe = isSafe;
	}
	
	public void setAnswer(int answer){
		this.answer = answer;
		possibleValues.clear();
		if (answer == 0) { 
			for (int i = 1; i < 10; i ++)
				this.possibleValues.add(i);
		}
	}

	public void removePossibleValue(int possibleValue){
		if (answer == 0) {
			if (possibleValues.contains(possibleValue))
				possibleValues.remove(possibleValue);
			if (possibleValues.size() == 1)
				setAnswer(possibleValues.get(0));
		}
	}
	
	public String toString(){
		if (answer == 0){
			String str = "Possible values for location (" + rowLocation + "," + colLocation + "): ";
			for (int value : possibleValues)
				str = str + value + " ";
			return str;
		}
		else
			return "Answer for location (" + rowLocation + "," + colLocation + "): " + answer;
	}

}
