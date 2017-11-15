package tic_tac_toe_variation;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;


public class GamePlay
{
	private boolean nextPlayerToMove;
	private Set<Point2D> listCross;
	private Set<Point2D> listEllipse;
	private Point winPointOne;
	private Point winPointTwo;
	private int pointsToWin;
	private final int sizeSingleSquere;
	public static final int DEFAULT_SIZE_SINGLE_SQUERE = 1; 
	public static final int DEFAULT_POINTS_TO_WIN = 3;
	public static final boolean DEFAULT_START_FIGURE = true;

	public GamePlay()
	{
		this(DEFAULT_SIZE_SINGLE_SQUERE, DEFAULT_POINTS_TO_WIN, DEFAULT_START_FIGURE);
	}
	
	public GamePlay(int pointsToWin)
	{
		this(DEFAULT_SIZE_SINGLE_SQUERE, pointsToWin, DEFAULT_START_FIGURE);
	}
	
	public GamePlay(boolean startFigure)
	{
		this(DEFAULT_SIZE_SINGLE_SQUERE, DEFAULT_POINTS_TO_WIN, startFigure);
	}
	
	public GamePlay(int pointsToWin, boolean startFigure)
	{
		this(DEFAULT_SIZE_SINGLE_SQUERE, pointsToWin, startFigure);
	}
	
	public GamePlay(int sizeSingleSquere, int pointsToWin)
	{
		this(sizeSingleSquere, pointsToWin, DEFAULT_START_FIGURE);
	}
	
	public GamePlay(int sizeSingleSquere, int pointsToWin, boolean startingPlayer)
	{
		listCross = new HashSet<>();
		listEllipse = new HashSet<>();
		winPointOne = null;
		winPointTwo = null;
		this.pointsToWin = pointsToWin;
		this.sizeSingleSquere = sizeSingleSquere;
		this.nextPlayerToMove = startingPlayer;
	}
	
	public int getSizeSingleSquere()
	{
		return sizeSingleSquere;
	}
	
	public boolean getNextPlayerToMove() {
		return nextPlayerToMove;
	}
	
	public Set<Point2D> getListCross(boolean editable) 
	{
		if(editable)
		{
			return listCross;
		}
		else
		{
			Set<Point2D> tempList = new HashSet<>(listCross.size());
			for(Point2D point : listCross) 
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
			return listEllipse;
		}
		else
		{
			Set<Point2D> tempList = new HashSet<>(listEllipse.size());
			for(Point2D point : listEllipse) 
			{
				tempList.add((Point2D) point.clone());
			}
			
			return tempList;
		}
	}
	
	public Point2D getWinPointOne() 
	{
		if(winPointOne == null)
			return null;
		
		return (Point) winPointOne.clone();

	}
	
	public Point2D getWinPointTwo() 
	{
		if(winPointTwo == null)
			return null;
		
		return (Point) winPointTwo.clone();
	}
	
	public void move(Point2D clickedPoint, boolean whoseMove) 
	{
		if(!isPointOutsideGameField(clickedPoint) && !isPointAlreadyPlayed(clickedPoint)) 
		{
			if(whoseMove)
			{
				listEllipse.add(clickedPoint);
			}
			else
			{
				listCross.add(clickedPoint);
			}	
			this.nextPlayerToMove = !whoseMove;
		}
	}
	
	private boolean isPointAlreadyPlayed(Point2D clickedPoint) {
		boolean pointPlayed = false;
		for(Point2D p : listEllipse) 
		{
			if(p.getX() == clickedPoint.getX() && p.getY() == clickedPoint.getY())
			{
				pointPlayed = true;
			}
		}
		for(Point2D p : listCross) 
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
	
	public Point findClickedField(MouseEvent e, SortedSet<? extends Point2D> coordOfSquares)
	{
		return this.findClickedField(new Point(e.getX(), e.getY()), coordOfSquares);
	}
	
	public Point findClickedField(Point2D clickedSquare, SortedSet<? extends Point2D> coordOfSquares)
	{
		Point coordsOfClickedSquere = null;
		if(coordOfSquares == null || coordOfSquares.isEmpty() || sizeSingleSquere <= 0)
			return coordsOfClickedSquere;
		coordsOfClickedSquere = getRealSquareCoordsInsideGameField(clickedSquare, coordOfSquares);

		return changeCoordsFromRealToGrid(coordsOfClickedSquere, coordOfSquares);
	}
	
	private Point getRealSquareCoordsInsideGameField(Point2D clickedSquare, SortedSet<? extends Point2D> coordOfSquares) {
		int coordXClick = -1;
		int coordYClick = -1;

		for (Point2D pointRectCoord : coordOfSquares) 
		{
			for(int i = 0; i < sizeSingleSquere; i++) 
			{
				if(pointRectCoord.getX() == clickedSquare.getX() - i && coordXClick == -1)
				{
					coordXClick = (int) clickedSquare.getX() - i;
				}
				if(pointRectCoord.getY() == clickedSquare.getY() - i && coordYClick == -1)
				{
					coordYClick = (int) clickedSquare.getY() - i;
				}
			}
			if(coordXClick != -1 && coordYClick != -1)
			{
				break;
			}
		}
		return new Point(coordXClick, coordYClick);
	}
	
	private Point changeCoordsFromRealToGrid(Point clickedSquare, SortedSet<? extends Point2D> coordOfSquares) {
		int coordXClick = (int) clickedSquare.getX();
		int coordYClick = (int) clickedSquare.getY();
		
		Iterator<? extends Point2D> iter = coordOfSquares.iterator();
		Point2D firstInSet = new PointComparable();
		if(iter.hasNext())
			firstInSet = iter.next();
		int coordXFirstSquere = (int) firstInSet.getX();
		int coordYFirstSquere = (int) firstInSet.getY();
		if(coordXFirstSquere != coordXClick)
		{
			coordXClick = (coordXClick - coordXFirstSquere) / sizeSingleSquere;
		}
		else
		{
			coordXClick = 0;
		}
		if(coordYFirstSquere != coordYClick)
		{
			coordYClick = (coordYClick - coordYFirstSquere) / sizeSingleSquere;	
		}
		else
		{
			coordYClick = 0;
		}
		return new Point(coordXClick, coordYClick);
	}

	public boolean checkVictory()
	{
		return this.checkVictory(this.nextPlayerToMove, pointsToWin);
	}
	
	public boolean checkVictory(int pointsToWin)
	{
		return this.checkVictory(this.nextPlayerToMove, pointsToWin);
	}
	
	public boolean checkVictory(boolean nextPlayerToMove, int pointsToWin) 
	{
		Set<Point2D> list = getMovesOfCurrentPlayer(nextPlayerToMove);

		int counterVertical = 0;
		int counterHorizontal = 0;
		int counterLeft = 0;
		int counterRight = 0;

		for(Point2D p : list) 
		{
			counterHorizontal = 0;
			counterVertical = 0;
			counterLeft = 0;
			counterRight = 0;
			for(Point2D p2 : list) 
			{
				if(p.getY() == p2.getY() && p2.getX() - p.getX() >= 0 && p2.getX() - p.getX() <= pointsToWin - 1) 
				{
					counterHorizontal++;
					if(counterHorizontal >= pointsToWin) 
					{
						winPointOne = new Point((int)p.getX(), (int)p.getY());
						winPointTwo = new Point((int)p.getX() + pointsToWin, (int)p.getY() + 1);
						return true;
					}
				}
				if(p.getX() == p2.getX() && p2.getY() - p.getY() >= 0 && p2.getY() - p.getY() <= pointsToWin - 1) 
				{
					counterVertical++;
					if(counterVertical >= pointsToWin) 
					{
						winPointOne = new Point((int) p.getX(), (int)p.getY());
						winPointTwo = new Point((int) p.getX() + 1, (int)p.getY() + pointsToWin);
						return true;
					}
				}
				if(p2.getX()-p.getX() >= 0 && p2.getX()-p.getX() <= pointsToWin - 1 &&
						p2.getY() - p.getY() >= 0 && p2.getY() - p.getY() <= pointsToWin - 1 &&
						p2.getY() - p.getY() == p2.getX() - p.getX()) 
				{
					counterLeft++;
					if(counterLeft >= pointsToWin) 
					{
						winPointOne = new Point((int)p.getX(), (int)p.getY());
						winPointTwo = new Point((int)p.getX() + pointsToWin, (int)p.getY() + pointsToWin);
						return true;
					}
				}
				if(p2.getX()-p.getX() >= 0 && p2.getX()-p.getX() <= pointsToWin - 1 &&
						p2.getY() - p.getY() <= 0 && p2.getY() - p.getY() >= - pointsToWin + 1 &&
						-(p2.getY() - p.getY()) == p2.getX() - p.getX()) 
				{
					counterRight++;
					if(counterRight >= pointsToWin) 
					{
						winPointOne = new Point((int)p.getX() + pointsToWin, (int)p.getY() - pointsToWin + 1);
						winPointTwo = new Point((int)p.getX(), (int)p.getY() + 1);
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	private Set<Point2D> getMovesOfCurrentPlayer(boolean nextPlayerToMove) {
		if(!nextPlayerToMove)
		{
			if(listEllipse.size() >= pointsToWin)
			{
				return listEllipse;
			}
		}
		else
		{
			if(listCross.size() >= pointsToWin)
			{
				return listCross;
			}
		}
		return new HashSet<>();
	}
}