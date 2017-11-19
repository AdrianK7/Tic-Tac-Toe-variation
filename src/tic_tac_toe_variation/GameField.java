package tic_tac_toe_variation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;

/**
* Object <code>gameFieldComponent</code> represent a game field panel
*/
public class GameField extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4397073919702124615L;
	private boolean isWindowResized;
	private int heightGame;
	private int widthGame;
	private SortedSet<PointComparable> squaresCoord;
	private Graphics2D comp;
	private GamePlay gPlay;
	private Point2D firstSqareInGrid;
	public int sizeSingleSquare;
	public static final int SIZE_SINGLE_SQUARE = 20;
	
	/**
	* Constructor.
	*/
	public GameField(GamePlay gPlay) 
	{
		this(gPlay, SIZE_SINGLE_SQUARE);
	}
	
	public GameField(GamePlay gPlay, int sizeSingleSquare) 
	{
		isWindowResized = false;
		squaresCoord = new TreeSet<>();
		this.addMouseListener(new MouseListener());
		this.addComponentListener(new CompListener());
		this.gPlay = gPlay;
		this.sizeSingleSquare = sizeSingleSquare;
		this.heightGame = gPlay.getFieldsCountVertical();
		this.widthGame = gPlay.getFieldsCountHorizontal();
	}
	
	public int getSizeSingleSquare()
	{
		return sizeSingleSquare;
	}
	
	public void setSizeSingleSquare(int sizeSingleSquare)
	{
		this.sizeSingleSquare = sizeSingleSquare;
	}
	
	/**
	* Method paint game field and squares/ellipses make by players.
	*/
	@Override
    protected void paintComponent(Graphics g) 
	{
		comp = (Graphics2D) g;
		super.paintComponent(comp); 
		super.setBackground(Color.BLACK);
		comp.setColor(Color.ORANGE);
		
		if(isWindowResized || squaresCoord.isEmpty()) {
			squaresCoord = new TreeSet<>();
			isWindowResized = false;
			paintNewGameFieldSquares(getSize());
		}
		else {
			repaintGameFieldSquares();
		}
		
		setFirstSquareInGrid();
		drawPlayerMovesCross(gPlay.getListCross(false));
		drawPlayerMovesEllipse(gPlay.getListEllipse(false));
		if(isSomeoneWinning(gPlay.getWinPointOne(), gPlay.getWinPointTwo())) {
			drawWinningSign(gPlay.getWinPointOne(), gPlay.getWinPointTwo());
		}
	}

	private void paintNewGameFieldSquares(Dimension panelSize) {
		int heightPanel = panelSize.getSize().height;
		int widthPanel = panelSize.getSize().width;
		for(int i = 0; i < widthGame; i++) 
		{
			for(int k = 0; k < heightGame; k++) 
			{
				PointComparable tempPoint = new PointComparable(widthPanel / 2 - widthGame * sizeSingleSquare / 2 + i * sizeSingleSquare,
						heightPanel / 2 - heightGame * sizeSingleSquare / 2 + k * sizeSingleSquare);
				squaresCoord.add(tempPoint);

				if(!squaresCoord.isEmpty()) 
				{
					comp.draw(new Rectangle2D.Double(tempPoint.getX(),
							tempPoint.getY(),
							sizeSingleSquare,
							sizeSingleSquare));
				}
			}
		}
	}
	
	private void repaintGameFieldSquares() {
		for(Point2D tempPoint : squaresCoord)
		{
			comp.draw(new Rectangle2D.Double(tempPoint.getX(), tempPoint.getY(), sizeSingleSquare, sizeSingleSquare));
		}
	}
	
	private void setFirstSquareInGrid() {
		Iterator<? extends Point2D> iter = squaresCoord.iterator();
		firstSqareInGrid = new Point2D.Double();
		if(iter.hasNext())
			firstSqareInGrid = iter.next();
	}
	
	private void drawWinningSign(Point2D winPointOne, Point2D winPointTwo) {
		comp.setColor(Color.RED);
		comp.draw(new Line2D.Double(firstSqareInGrid.getX() + winPointOne.getX() * sizeSingleSquare,
			firstSqareInGrid.getY() + winPointOne.getY() * sizeSingleSquare,
			firstSqareInGrid.getX() + winPointTwo.getX() * sizeSingleSquare,
			firstSqareInGrid.getY() + winPointTwo.getY() * sizeSingleSquare
		));
	}
	
	private boolean isSomeoneWinning(Point2D winPointOne, Point2D winPointTwo) {
		return winPointOne != null && winPointTwo != null;
	}
	
	private void drawPlayerMovesEllipse(Set<Point2D> playerMoves) {
		if(playerMoves != null) 
		{
			for(Point2D p : playerMoves) 
			{
				comp.setColor(gPlay.getPlayerEllipse().getColor());
				comp.draw(new Ellipse2D.Double((firstSqareInGrid.getX() + p.getX() * sizeSingleSquare) + 2,
						(firstSqareInGrid.getY() + p.getY() * sizeSingleSquare) + 2,
						sizeSingleSquare - 4,
						sizeSingleSquare - 4));
			}
		}
	}
	
	private void drawPlayerMovesCross(Set<Point2D> playerMoves) {
		if(playerMoves != null) 
		{
			for(Point2D p : playerMoves) 
			{
				comp.setColor(gPlay.getPlayerCross().getColor());
				comp.draw(new Line2D.Double(firstSqareInGrid.getX() + p.getX() * sizeSingleSquare + 4,
						firstSqareInGrid.getY() + p.getY() * sizeSingleSquare + 4,
						firstSqareInGrid.getX() + p.getX() * sizeSingleSquare + sizeSingleSquare - 4,
						firstSqareInGrid.getY() + p.getY() * sizeSingleSquare + sizeSingleSquare - 4));
				comp.draw(new Line2D.Double(firstSqareInGrid.getX() + p.getX() * sizeSingleSquare + sizeSingleSquare - 4,
						firstSqareInGrid.getY() + p.getY() * sizeSingleSquare + 4,
						firstSqareInGrid.getX() + p.getX() * sizeSingleSquare + 4,
						firstSqareInGrid.getY() + p.getY() * sizeSingleSquare + sizeSingleSquare - 4));
			}
		}
	}
	
	
	
	/*
	 * If top left corner field is clicked coordinates returned are (0,0) 
	 */
	private Point findClickedField(MouseEvent e, SortedSet<? extends Point2D> coordOfSquares)
	{
		return this.findClickedField(new Point(e.getX(), e.getY()), coordOfSquares);
	}
	
	private Point findClickedField(Point2D clickedSquare, SortedSet<? extends Point2D> coordOfSquares)
	{
		Point coordsOfClickedSquere = null;
		if(coordOfSquares == null || coordOfSquares.isEmpty() || sizeSingleSquare <= 0 || clickedSquare == null)
			return coordsOfClickedSquere;
		coordsOfClickedSquere = getRealSquareCoordsInsideGameField(clickedSquare, coordOfSquares);

		return changeCoordsFromRealToGrid(coordsOfClickedSquere, coordOfSquares);
	}
	
	private Point getRealSquareCoordsInsideGameField(Point2D clickedSquare, SortedSet<? extends Point2D> coordOfSquares) {
		int coordXClick = -1;
		int coordYClick = -1;

		for (Point2D pointRectCoord : coordOfSquares) 
		{
			for(int i = 0; i < sizeSingleSquare; i++) 
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
			coordXClick = (coordXClick - coordXFirstSquere) / sizeSingleSquare;
		}
		else
		{
			coordXClick = 0;
		}
		if(coordYFirstSquere != coordYClick)
		{
			coordYClick = (coordYClick - coordYFirstSquere) / sizeSingleSquare;	
		}
		else
		{
			coordYClick = 0;
		}
		return new Point(coordXClick, coordYClick);
	}
	
	/**
	* Inner class MouseListener.
	*/
	class MouseListener extends MouseAdapter 
	{	
		private boolean victory = false;
		
		public void mouseClicked(MouseEvent e) 
		{
			if(!victory)
			{
				victory = gPlay.checkVictory(gPlay.move(findClickedField(e, squaresCoord)));
			}

		}	
	}
	
	class CompListener extends ComponentAdapter
	{
		
	    public void componentResized(ComponentEvent e)
		{
	    	isWindowResized = true;
			repaint();
		}
	}
}
