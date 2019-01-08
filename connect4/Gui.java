package connect4;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Gui extends JPanel implements ActionListener { 
	
	private JButton[][] coins;
	private JButton dropButtons[];
	private JPanel gameBoard; 
	private JPanel checkersPanel;
	private JPanel dropPanel;
	private int[] cointsCount;
	private String currentPlayer = "Player 1";
	private JLabel displayLabel;
	private Icon white;
	private Icon player1Icon;
	private Icon player2Icon;

	public Gui() { 
		displayLabel=new JLabel("");
		gameBoard = new JPanel();
		gameBoard.setLayout(new BorderLayout());
		checkersPanel = new JPanel();
		checkersPanel.setLayout(new GridLayout(6, 7));
		dropPanel = new JPanel(new GridLayout(1, 7));
		coins = new JButton[6][7];
		dropButtons = new JButton[7];
		setLayout(new BorderLayout());
		add(header(), BorderLayout.NORTH);
		add(gridBoard(), BorderLayout.CENTER);
		cointsCount = new int[7];
		imageIcons();
		setRowCount();

	}
	public void imageIcons(){
		white = new ImageIcon(this.getClass().getResource("/connect4/white.png"));
		this.player1Icon=new ImageIcon(this.getClass().getResource("/connect4/player1.png"));
		this.player2Icon=new ImageIcon(this.getClass().getResource("/connect4/player2.png"));
		
	}

	private void setRowCount() {  
		for (int i = 0; i < cointsCount.length; i++) {
			cointsCount[i] = 5;
		}

	}
	public void updateOut(String msg){  
		//displayLabel.setText("<HTML><H1 color=red>"+msg+"</H1></HTML>");
		displayLabel.setText(msg);
	}

	public JPanel header() {  
		JPanel header = new JPanel();
		displayLabel=new JLabel("");
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		updateOut(this.currentPlayer+" goes first");
		header.add(displayLabel);
		return header;

	}

	public JPanel gridBoard() {
		
		for (int i = 0; i < dropButtons.length; i++) {
			dropButtons[i] = new JButton("" + (i + 1));
			dropButtons[i].addActionListener(this);
			dropPanel.add(dropButtons[i]);

		}
		for (int row = 0; row < coins.length; row++) {  
			for (int col = 0; col < coins[row].length; col++) {
				coins[row][col] = new JButton(".");
				coins[row][col].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 0));
				coins[row][col].setIcon(white);
				checkersPanel.add(coins[row][col]);
			}
		}
		gameBoard.add(dropPanel, BorderLayout.NORTH);  
		gameBoard.add(checkersPanel, BorderLayout.CENTER);

		return gameBoard;
	}
	public void updateCurrPlayer(){
		if(this.currentPlayer.equalsIgnoreCase("Player 1")){
			this.currentPlayer="Player 2";
		}else{
			this.currentPlayer="Player 1";
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int col = Integer.parseInt(e.getActionCommand()) - 1;
		if(cointsCount[col]==0){
			updateIcon(col);
			dropButtons[col].removeActionListener(this);
		}else{
			updateIcon(col);
			cointsCount[col]-=1;
		}
		
		if(isWinner()){
			updateOut(currentPlayer + " WINS");
			playAnotherGame();
			
		}else if(isFull()){
			updateOut(" It's a DRAW");
			playAnotherGame();
		}
		else{
			updateCurrPlayer();
		    updateOut(currentPlayer + " goes");
		}
	}  

   
	public void playAnotherGame(){  
		int yesNo = JOptionPane.showConfirmDialog(null, "Do you want to play another game?");
		if(yesNo == 0){
			reBuild();
			updateOut(currentPlayer+" goes first!");
		}
		else{
			updateOut("Thanks for playing");
			JOptionPane.showMessageDialog(null, "BYE");
			System.exit(0);
		}
		System.out.println(yesNo);
	}

	private void reBuild() {
		
		  setRowCount() ; 
		for (int i = 0; i < dropButtons.length; i++) {
			dropPanel.remove(dropButtons[i]);  
			dropButtons[i] = new JButton("" + (i + 1));  
			dropButtons[i].addActionListener(this);        
			dropPanel.add(dropButtons[i]);
			dropPanel.validate();   

		}
		for (int row = 0; row < coins.length; row++) {  
			for (int col = 0; col < coins[row].length; col++) {
				checkersPanel.remove(coins[row][col]);  
				coins[row][col] = new JButton(".");  
				coins[row][col].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 0));
				coins[row][col].setIcon(white);
				checkersPanel.add(coins[row][col]);
				checkersPanel.validate();
			}
		}
		
		
	}

	private void updateIcon(int col) {  
		if(currentPlayer.equalsIgnoreCase("Player 1")){
		coins[cointsCount[col]][col].setIcon(this.player1Icon);
		}else{
			coins[cointsCount[col]][col].setIcon(player2Icon);
			}
		coins[cointsCount[col]][col].setText(currentPlayer);
	}
	
	public boolean isWinner() {
		
	  
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 4; col++) {
				if (coins[row][col].getText().equalsIgnoreCase(currentPlayer)
						
						&& coins[row + 1][col + 1].getText().equalsIgnoreCase(currentPlayer)
						&& coins[row + 2][col + 2].getText().equalsIgnoreCase(currentPlayer)
						&& coins[row + 3][col + 3].getText().equalsIgnoreCase(currentPlayer)) {
					return true;
				}

			}
	}
		
		for (int row = 0; row < 3; row++) {
			for (int col = coins[row].length - 1; col >= 3; col--) {
				if (coins[row][col].getText().equalsIgnoreCase(currentPlayer)
						&& coins[row + 1][col - 1].getText().equalsIgnoreCase(currentPlayer)
						&& coins[row + 2][col - 2].getText().equalsIgnoreCase(currentPlayer)
						&& coins[row + 3][col - 3].getText().equalsIgnoreCase(currentPlayer)) {
					return true;
				}

			}
		}
		
		for (int col = 0; col < 7; col++) {
			int count = 0;
			for (int row = 0; row < 6; row++) {
				if(coins[row][col].getText().equalsIgnoreCase(currentPlayer)){
					 count++;
				}else{
					count=0;
				}
				if(count==4){
					return true;
				}
				}
		}
		
		for(int row=0;row<6;row++){
			int count=0;
			for(int col=0;col<7;col++){
				if(coins[row][col].getText().equalsIgnoreCase(currentPlayer)){
					count++;
				}else{
					count=0;
				}
				if(count==4){
					return true;
				}
			}
			
		}
		
		
	return false;
	

	}
	public boolean isFull(){
		for(int i=0;i<cointsCount.length;i++){
			if(cointsCount[i]>0){
				return false;
			}
			
		}
		return true;
	}
	}




