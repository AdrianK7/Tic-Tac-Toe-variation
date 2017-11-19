package tic_tac_toe_variation;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;

public enum Player implements Serializable{
	CROSS(Color.MAGENTA, new File("src/tic_tac_toe_variation/res/cross.jpg")), 
	ELLIPSE(Color.GREEN, new File("src/tic_tac_toe_variation/res/ellipse.jpg"));
	
	public static final File PRIVATE_CROSS_IMAGE = new File("src/tic_tac_toe_variation/res/cross.jpg");
	public static final File PRIVATE_ELLIPSE_IMAGE = new File("src/tic_tac_toe_variation/res/ellipse.jpg");
	private boolean isPlayerMove = true;
	private Color color;
	private File avatar;
	private StringBuilder name;
	private static int playersCount;
		
	private Player(Color color, File avatar) {
		name = new StringBuilder();
		name.append("Player ");
		name.append(getPlayersCount());
		this.avatar = avatar;
		this.color = color;
	}
	
	private int getPlayersCount() {
		return ++playersCount;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isPlayerMove() {
		return isPlayerMove;
	}

	public void setPlayerMove(boolean isPlayerMove) {
		this.isPlayerMove = isPlayerMove;
	}
	public File getAvatar() {
		return avatar;
	}
	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name.toString();
	}
	public void setName(String name) {
		this.name = new StringBuilder(name);
	}	
}
