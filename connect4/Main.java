package connect4;

import javax.swing.JFrame;

public class Main extends JFrame {
	public static void main(String[]args){
		new Main();
	}
	public Main(){
		Gui gui=new Gui();
		this.add(gui);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
	}

}
