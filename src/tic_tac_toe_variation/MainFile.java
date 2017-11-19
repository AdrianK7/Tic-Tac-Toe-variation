package tic_tac_toe_variation;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class MainFile {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GameFrame frame = new GameFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(900, 900);
				frame.setVisible(true);


			}
		});
	}
}
