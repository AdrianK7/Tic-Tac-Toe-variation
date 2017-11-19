package tic_tac_toe_variation;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PlayerPanel  extends JPanel {
	private BufferedImage image;
	private JButton avatar;
	private JButton changeAvatar;
	private JLabel nameLabel;
	private JButton playerColorChanger;
	private Player player;
	private JPanel gameField;
	
	public PlayerPanel(Player player, JPanel gameField) {
		this.player = player;
		this.gameField = gameField;
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		changeAvatar = new JButton("Change avatar");
		changeAvatar.addActionListener(new AvatarChangeListener());
		nameLabel = new JLabel(player.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerColorChanger = new JButton("Change color");
		playerColorChanger.addActionListener(new ColorListener());
    	avatar = new JButton("Avatar");
	    try {     
	    	image = ImageIO.read(player.getAvatar());
	    	Image scaledImage = image.getScaledInstance(150, 200,  java.awt.Image.SCALE_SMOOTH);
	    	avatar.setIcon(new ImageIcon(scaledImage));
	    	avatar.setText("");
	    	avatar.addActionListener(new AvatarChangeListener());
	    	avatar.setContentAreaFilled(false);
	    	avatar.addActionListener(null);
	    	avatar.setBorder(null);

	    } 
	    catch (IOException ex) {
	    	ex.printStackTrace();
	    }
		add(nameLabel, makeConstraints(GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 		0, 0, 0, 0, 1, 1));
	    add(avatar, makeConstraints(GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 		0, 0, 0, 1, 1, 1));
		add(playerColorChanger, makeConstraints(GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 0, 0, 0, 2, 1, 1));
		add(changeAvatar, makeConstraints(GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 0, 1, 0, 3, 1, 1));
	}
    
	private GridBagConstraints makeConstraints(int fill, int anchor, int weightx, int weighty, int gridx, int gridy, 
			int gridwidth, int gridheight ) 
	{
		GridBagConstraints constraint = new GridBagConstraints();
		
		constraint.fill = fill;
		constraint.anchor = anchor;
		constraint.weightx = weightx;
		constraint.weighty = weighty;
		constraint.gridx = gridx;
		constraint.gridy = gridy;
		constraint.gridwidth = gridwidth;
		constraint.gridheight = gridheight;
		
		return constraint;
	
	}
	
	private void setAvatar() {
	    try {
	        JFileChooser fileChooser = new JFileChooser();
	        if (fileChooser.showOpenDialog(PlayerPanel.this) == JFileChooser.APPROVE_OPTION) {
	            player.setAvatar(fileChooser.getSelectedFile());
	        }
	    	image = ImageIO.read(player.getAvatar());
	    	Image scaledImage = image.getScaledInstance(150, 200,  java.awt.Image.SCALE_SMOOTH);
	    	avatar.setIcon(new ImageIcon(scaledImage));
	    } 
	    catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	}

	class ColorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			player.setColor(JColorChooser.showDialog(
					PlayerPanel.this,
	                "Choose figure color",
	                player.getColor()));
			gameField.repaint();
		}
	}
	
	class AvatarChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setAvatar();
		}
	}
}
