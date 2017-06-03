package com.project.sudoku9x9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SudokuGUI {
	private SudokuTable sudokuTable = new SudokuTable();
	
	private JFrame frame = new JFrame("Sudoku");
	private JTextField textField[][] = new JTextField[9][9];
	private GridPanel gridPanel = new GridPanel(new GridLayout(9,9,1,1));
	
	SudokuGUI(){
		for (int col = 0; col < 9; col++){
			for (int row = 0; row < 9; row++){
				textField[row][col] = new JTextField();
				textField[row][col].setForeground(Color.BLUE);
				textField[row][col].setHorizontalAlignment(JTextField.CENTER);
				textField[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				textField[row][col].setFont(new Font("SansSerif", Font.BOLD, 20));
				gridPanel.add(textField[row][col]);
			}
		}
	}
	
	boolean checkText(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){
				if (!textField[row][col].getText().equals("")){
					try {
						int digit = Integer.parseInt(textField[row][col].getText());
						if (digit <= 0 || digit >= 10)
							return false;
					}
					catch (NumberFormatException e){
						return false;
					}
				}
			}
		}
		return true;
	}

	
	public void sudokuTableToGUI(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){		
				if (sudokuTable.getNumber(row,col).isSolved()){
					textField[row][col].setText(String.valueOf(sudokuTable.getNumber(row,col).getAnswer()));
					textField[row][col].setForeground(Color.BLACK);
					textField[row][col].setEditable(false);
				}
				else{
					textField[row][col].setForeground(Color.BLUE);
					textField[row][col].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
	}

	public void clearGrid(){
		for (int row = 0; row < 9; row++){
			for (int col = 0; col < 9; col++){
				textField[row][col].setText("");
				textField[row][col].setForeground(Color.BLUE);
				textField[row][col].setBackground(Color.WHITE);
				textField[row][col].setEditable(true);
			}
		}
	}
	
	public void createGUI(){
		JLabel topLabel = new JLabel();
		topLabel.setText("Sudoku");
		topLabel.setOpaque(true);
		topLabel.setHorizontalAlignment(JLabel.CENTER);
		topLabel.setBackground(Color.LIGHT_GRAY);
		topLabel.setForeground(Color.BLACK);
		topLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		frame.add(topLabel, BorderLayout.NORTH);
		
		
		frame.getContentPane().add(gridPanel, BorderLayout.CENTER);

		JPanel outerPanel = new JPanel();
		JPanel innerPanel = new JPanel();
		
		frame.add(outerPanel, BorderLayout.EAST);
		innerPanel.setLayout(new GridLayout(10, 1, 15, 15));
		
		JButton newButton = new JButton("New Game");
		innerPanel.add(newButton);
		newButton.setSize(new Dimension(10, 40));
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				

				int yes_no = JOptionPane.showConfirmDialog(frame, "Are you sure?", "New Game", JOptionPane.YES_NO_OPTION);
				if (yes_no == JOptionPane.YES_OPTION){
					Object [] difficultyButtons = {"Easy", "Medium", "Hard"};
					int result = JOptionPane.showOptionDialog(null, "Choose Difficulty", "Difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, difficultyButtons, difficultyButtons[0]);
					if (result == JOptionPane.YES_OPTION){
						Random rand = new Random();
						int easyOption = rand.nextInt(2);
						int difficulty = easyOption;
						SudokuPuzzles example = new SudokuPuzzles();
						sudokuTable = example.callPuzzle(difficulty);
					}
					else if (result == JOptionPane.NO_OPTION){
						int difficulty = 2;
						SudokuPuzzles example = new SudokuPuzzles();
						sudokuTable = example.callPuzzle(difficulty);
					}
					else if (result == JOptionPane.CANCEL_OPTION){
						int difficulty = 3;
						SudokuPuzzles example = new SudokuPuzzles();
						sudokuTable = example.callPuzzle(difficulty);
					}
				}
				clearGrid();
				sudokuTableToGUI();
			}
		});
		
		JButton resetButton = new JButton("Reset");
		resetButton.setSize(new Dimension(10, 40));
		innerPanel.add(resetButton);
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearGrid();
				sudokuTableToGUI();
			}
		});		
		
		JButton clearButton = new JButton("Clear Table");
		clearButton.setSize(new Dimension(10, 40));
		innerPanel.add(clearButton);
		clearButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sudokuTable = new SudokuTable();
				clearGrid();
			}
		});
		
		JButton solveButton = new JButton("Solve");
		solveButton.setSize(new Dimension(10, 40));
		innerPanel.add(solveButton);
		solveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (!checkText())
					JOptionPane.showMessageDialog(frame,"Invalid input. Values must be integers from 1 to 9","Error",JOptionPane.ERROR_MESSAGE);
				else {
					if (!sudokuTable.solve(1))
						JOptionPane.showMessageDialog(frame,"This puzzle cannot be solved.","Error",JOptionPane.ERROR_MESSAGE);
					else
						sudokuTableToGUI();
				}
			}
		});
		
		
		outerPanel.add(innerPanel);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(150, 150, 497, 455);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public class GridPanel extends JPanel{
		private static final long serialVersionUID = -6157041650150998205L;

		GridPanel(GridLayout layout){
			super(layout);
		}
		
		public void paintComponent(Graphics g){
			g.fillRect(getWidth()/3 - 1,0,3,getHeight());
			g.fillRect(2*getWidth()/3 - 1,0,3,getHeight());
			g.fillRect(0,getHeight()/3 - 1,getWidth(),3);
			g.fillRect(0,2*getHeight()/3 - 2,getWidth(),3);
			g.fillRect(getWidth()-3, 0,  3, getHeight());
			g.fillRect(0, 0,  3, getHeight());
			g.fillRect(0, 0,  getWidth(), 3);
			g.fillRect(0, getHeight()-3,  getWidth(), 3);	

		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuGUI gui = new SudokuGUI();
					gui.createGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
