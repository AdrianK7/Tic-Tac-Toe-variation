package tic_tac_toe_variation;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class GamePlay implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8240330393165542364L;
	private final Player playerEllipse;
	private final Player playerCross;
	private Set<Point2D> movesPlayerCross;
	private Set<Point2D> movesPlayerEllipse;
	private Point pointToDrawWinLineOne;
	private Point pointToDrawWinLineTwo;
	private int pointsToWin;
	private int counterToWin;
	private int fieldsCountVertical;
	private int fieldsCountHorizontal;
	public static final int GAME_FIELDS_VERTICAL = 20;
	public static final int GAME_FIELDS_HORIZONTAL = 20;
	public static final int POINTS_TO_WIN = 5;
	private static final int OFFSET_TO_PROPER_DRAW_WIN_LINE = 1;

	public GamePlay(Player playerEllipse, Player playerCross) {
		fieldsCountHorizontal = GAME_FIELDS_HORIZONTAL;
		fieldsCountVertical = GAME_FIELDS_VERTICAL;	
		movesPlayerCross = new HashSet<>();
		movesPlayerEllipse = new HashSet<>();
		pointToDrawWinLineOne = null;
		pointToDrawWinLineTwo = null;
		pointsToWin = POINTS_TO_WIN;
		this.playerEllipse = playerEllipse;
		this.playerCross = playerCross;
		counterToWin = 0;
	}
	
	public void setPointsToWin(int pointsToWin) throws ValueToLowExpection {
		if(pointsToWin < 3) {
			throw new ValueToLowExpection("Cause: pointsToWin < 3");
		}
		this.pointsToWin = pointsToWin;
	}
	
	public int getPointsToWin() {
		return pointsToWin;
	}
	
	public int getFieldsCountVertical() {
		return fieldsCountVertical;
	}

	public void setFieldsCountVertical(int fieldsCountVertical) throws ValueToLowExpection {
		if(pointsToWin < 3) {
			throw new ValueToLowExpection("Cause: fieldsCountVertical < 3");
		}
		this.fieldsCountVertical = fieldsCountVertical;
	}

	public int getFieldsCountHorizontal() {
		return fieldsCountHorizontal;
	}

	public void setFieldsCountHorizontal(int fieldsCountHorizontal) throws ValueToLowExpection {
		if(pointsToWin < 3) {
			throw new ValueToLowExpection("Cause: fieldsCountHorizontal < 3");
		}
		this.fieldsCountHorizontal = fieldsCountHorizontal;
	}
	
	public Player getPlayerEllipse() {
		return playerEllipse;
	}

	public Player getPlayerCross() {
		return playerCross;
	}
	
	public Set<Point2D> getListCross(boolean editable) 
	{
		if(editable)
		{
			return movesPlayerCross;
		}
		else
		{
			Set<Point2D> tempList = new HashSet<>(movesPlayerCross.size());
			for(Point2D point : movesPlayerCross) 
			{
				tempList.add((Point2D) point.clone());
			}
			
			return tempList;
		}
		
	}
	
	public Set<Point2D> getListEllipse(boolean editable) 
	{
		if(editable)
		{
			return movesPlayerEllipse;
		}
		else
		{
			Set<Point2D> tempList = new HashSet<>(movesPlayerEllipse.size());
			for(Point2D point : movesPlayerEllipse) 
			{
				tempList.add((Point2D) point.clone());
			}
			
			return tempList;
		}
	}
	
	public Point2D getWinPointOne() 
	{
		if(pointToDrawWinLineOne == null)
			return null;
		
		return (Point) pointToDrawWinLineOne.clone();

	}
	
	public Point2D getWinPointTwo() 
	{
		if(pointToDrawWinLineTwo == null)
			return null;
		
		return (Point) pointToDrawWinLineTwo.clone();
	}
	
	public Point2D move(Point2D clickedPoint) 
	{
		if(!isPointOutsideGameField(clickedPoint) && !isPointAlreadyPlayed(clickedPoint)) 
		{
			if(playerEllipse.isPlayerMove())
			{
				movesPlayerEllipse.add(clickedPoint);
				playerEllipse.setPlayerMove(false);
				playerCross.setPlayerMove(true);
			}
			else
			{
				movesPlayerCross.add(clickedPoint);
				playerEllipse.setPlayerMove(true);
				playerCross.setPlayerMove(false);
			}	
		}
		return clickedPoint;
	}
	
	private boolean isPointAlreadyPlayed(Point2D clickedPoint) {
		boolean pointPlayed = false;
		for(Point2D p : movesPlayerEllipse) 
		{
			if(p.getX() == clickedPoint.getX() && p.getY() == clickedPoint.getY())
			{
				pointPlayed = true;
			}
		}
		for(Point2D p : movesPlayerCross) 
		{
			if(p.getX() == clickedPoint.getX() && p.getY() == clickedPoint.getY())
			{
				pointPlayed = true;
			}
		}
		return pointPlayed;
	}
	
	private boolean isPointOutsideGameField(Point2D clickedPoint) {
		
		return clickedPoint.getX() < 0 || clickedPoint.getY() < 0;
	}

	/*
	 * return false when lastMove is null
	 */
	public boolean checkVictory(Point2D lastMove)
	{
		return this.checkVictory(pointsToWin, lastMove);
	}
	
	public boolean checkVictory(int pointsToWin, Point2D lastMove) 
	{
		if(lastMove == null) {
			return false;
		}
		
		Set<Point2D> movesOfPreviousPlayer = getMovesOfPreviousPlayer();
		return checkVictoryVertical(movesOfPreviousPlayer, pointsToWin, lastMove) ||
				checkVictoryHorizontal(movesOfPreviousPlayer, pointsToWin, lastMove) ||
				checkVictoryBendLeft(movesOfPreviousPlayer, pointsToWin, lastMove) ||
				checkVictoryBendRight(movesOfPreviousPlayer, pointsToWin, lastMove);
	}
	
	
	private Set<Point2D> getMovesOfPreviousPlayer() {
		if(!playerEllipse.isPlayerMove())
		{
			if(movesPlayerEllipse.size() >= pointsToWin)
			{
				return movesPlayerEllipse;
			}
		}
		else
		{
			if(movesPlayerCross.size() >= pointsToWin)
			{
				return movesPlayerCross;
			}
		}
		return new HashSet<>();
	}
	
	private boolean checkVictoryVertical(Set<Point2D> movesOfCurrentPlayer, int pointsToWin, Point2D lastMove) {
		if(counterToWin == pointsToWin - 1) {
			return false;
		}
		counterToWin = 0;
		int coordReferenceForWinLine = (int) lastMove.getY() + OFFSET_TO_PROPER_DRAW_WIN_LINE;
		boolean verticalBottomReached = true;
		boolean verticalTopReached = true;
		Point2D prospector = new Point(0,0);

		for(int i = 1; i < pointsToWin && 
				counterToWin < pointsToWin - 1 && 
				(verticalTopReached || verticalBottomReached); i++) {
			prospector.setLocation(lastMove.getX(), lastMove.getY() + i);
			if(movesOfCurrentPlayer.contains(prospector) && verticalBottomReached) {
				coordReferenceForWinLine = (int) lastMove.getY() + i + OFFSET_TO_PROPER_DRAW_WIN_LINE;
				counterToWin++;
			}
			else {
				verticalBottomReached = false;
			}
			prospector.setLocation(lastMove.getX(), lastMove.getY() - i);
			if(movesOfCurrentPlayer.contains(prospector) && verticalTopReached) {
				counterToWin++;
			}
			else {
				verticalTopReached = false;
			}
		}
		if(counterToWin == pointsToWin - 1) {
			pointToDrawWinLineOne = new Point((int) lastMove.getX(), coordReferenceForWinLine - pointsToWin);
			pointToDrawWinLineTwo = new Point((int) lastMove.getX() + OFFSET_TO_PROPER_DRAW_WIN_LINE, coordReferenceForWinLine);
			return true;
		}
		return false;

	}
	
	private boolean checkVictoryHorizontal(Set<Point2D> movesOfCurrentPlayer, int pointsToWin, Point2D lastMove) {
		if(counterToWin == pointsToWin - 1) {
			return false;
		}
		counterToWin = 0;
		int coordReferenceForWinLine = (int) lastMove.getX() + OFFSET_TO_PROPER_DRAW_WIN_LINE;
		boolean horizontalLeftReached = true;
		boolean horizontalRightReached = true;
		Point2D prospector = new Point(0,0);
		
		for(int i  = 1; i < pointsToWin && 
				counterToWin < pointsToWin - 1 && 
				(horizontalLeftReached || horizontalRightReached); i++) {
			prospector.setLocation(lastMove.getX() + i, lastMove.getY());
			if(movesOfCurrentPlayer.contains(prospector) && horizontalRightReached) {
				coordReferenceForWinLine = (int) lastMove.getX() + i + OFFSET_TO_PROPER_DRAW_WIN_LINE;
				counterToWin++;
			}
			else {
				horizontalRightReached= false;
			}
			prospector.setLocation(lastMove.getX() - i, lastMove.getY());
			if(movesOfCurrentPlayer.contains(prospector) && horizontalLeftReached) {
				counterToWin++;
			}
			else {
				horizontalLeftReached = false;
			}
		}
		if(counterToWin == pointsToWin - 1) {
			pointToDrawWinLineOne = new Point(coordReferenceForWinLine - pointsToWin, (int) lastMove.getY());
			pointToDrawWinLineTwo = new Point(coordReferenceForWinLine, (int) lastMove.getY() + OFFSET_TO_PROPER_DRAW_WIN_LINE);
			return true;
		}
		return false;
	}
	
	private boolean checkVictoryBendLeft(Set<Point2D> movesOfCurrentPlayer, int pointsToWin, Point2D lastMove) {
		if(counterToWin == pointsToWin - 1) {
			return false;
		}
		counterToWin = 0;
		Point2D coordsReferenceForWinLine = new Point((int) lastMove.getX() + 1, (int) lastMove.getY());
		boolean bendLeftTopReached = true;
		boolean bendLeftBottomReached = true;
		Point2D prospector = new Point(0,0);

		for(int i  = 1; i < pointsToWin && 
				counterToWin < pointsToWin - 1 && 
				(bendLeftTopReached || bendLeftBottomReached); i++) {
			prospector.setLocation(lastMove.getX() + i, lastMove.getY() - i);
			if(movesOfCurrentPlayer.contains(prospector) && bendLeftTopReached) {
				coordsReferenceForWinLine.setLocation(lastMove.getX() + i + OFFSET_TO_PROPER_DRAW_WIN_LINE, lastMove.getY() - i);
				counterToWin++;
			}
			else {
				bendLeftTopReached = false;
			}
			prospector.setLocation(lastMove.getX() - i, lastMove.getY() + i);
			if(movesOfCurrentPlayer.contains(prospector) && bendLeftBottomReached) {
				counterToWin++;
			}
			else {
				bendLeftBottomReached = false;
			}
		}
		if(counterToWin == pointsToWin - 1) {
			pointToDrawWinLineOne = new Point((int) coordsReferenceForWinLine.getX() - pointsToWin, (int) coordsReferenceForWinLine.getY() + pointsToWin);
			pointToDrawWinLineTwo = new Point((int) coordsReferenceForWinLine.getX(), (int) coordsReferenceForWinLine.getY());
			return true;
		}
		return false;
	}
	
	private boolean checkVictoryBendRight(Set<Point2D> movesOfCurrentPlayer, int pointsToWin, Point2D lastMove) {
		if(counterToWin == pointsToWin - 1) {
			return false;
		}
		counterToWin = 0;
		Point2D coordsReferenceForWinLine = new Point((int) lastMove.getX() + OFFSET_TO_PROPER_DRAW_WIN_LINE, 
				(int) lastMove.getY() + OFFSET_TO_PROPER_DRAW_WIN_LINE);
		boolean bendRightTopReached = true;
		boolean bendRightBottomReached = true;
		Point2D prospector = new Point(0,0);

		for(int i  = 1; i < pointsToWin && 
				counterToWin < pointsToWin - 1 && 
				(bendRightTopReached || bendRightBottomReached); i++) {			
			prospector.setLocation(lastMove.getX() + i, lastMove.getY() + i);
			if(movesOfCurrentPlayer.contains(prospector) && bendRightBottomReached) {
				coordsReferenceForWinLine.setLocation(lastMove.getX() + i + OFFSET_TO_PROPER_DRAW_WIN_LINE, 
						lastMove.getY() + i + OFFSET_TO_PROPER_DRAW_WIN_LINE);
				counterToWin++;
			}
			else {
				bendRightBottomReached = false;
			}
			prospector.setLocation(lastMove.getX() - i, lastMove.getY() - i);
			if(movesOfCurrentPlayer.contains(prospector) && bendRightTopReached) {
				counterToWin++;
			}
			else {
				bendRightTopReached = false;
			}
		}
		if(counterToWin == pointsToWin - 1) {
			pointToDrawWinLineOne = new Point((int) coordsReferenceForWinLine.getX() - pointsToWin, (int) coordsReferenceForWinLine.getY() - pointsToWin);
			pointToDrawWinLineTwo = new Point((int) coordsReferenceForWinLine.getX(), (int) coordsReferenceForWinLine.getY());
			return true;
		}
		return false;
	}
}