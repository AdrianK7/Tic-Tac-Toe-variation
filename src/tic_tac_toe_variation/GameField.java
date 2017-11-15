package tic_tac_toe_variation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
@SuppressWarnings("serial")
public class GameField extends JPanel
{
	private boolean resize;
	private int heightGame;
	private int widthGame;
	private int sizeSingleSquare;
	private SortedSet<PointComparable> squaresCoord;
	private Graphics2D comp;
	private GamePlay gPlay;
	private Point2D firstSqareInGrid;
	
	/**
	* Constructor.
	*/
	public GameField(GamePlay gPlay) 
	{
		resize = false;
		squaresCoord = new TreeSet<>();
		heightGame = 20;
		widthGame = 20;
		this.addMouseListener(new MouseListener());
		this.addComponentListener(new CompListener());
		this.gPlay = gPlay;
		sizeSingleSquare = gPlay.getSizeSingleSquere();
	}
	
	/**
	* Constructor.
	* @param sizeGame.
	*/
	public GameField(GamePlay gPlay, int sizeGame) 
	{		
		this(gPlay);
		heightGame = sizeGame;
		widthGame = sizeGame;
	}
	
	public GameField(GamePlay gPlay, int heightGame, int widthGame) 
	{		
		this(gPlay);
		this.heightGame = heightGame;
		this.widthGame = widthGame;
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
		
		if(resize || squaresCoord.isEmpty()) {
			squaresCoord = new TreeSet<>();
			resize = false;
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
				comp.setColor(Color.GREEN);
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
				comp.setColor(Color.MAGENTA);
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
				gPlay.move(gPlay.findClickedField(e, squaresCoord), gPlay.getNextPlayerToMove());
				victory = gPlay.checkVictory();
			}

		}	
	}
	
	class CompListener extends ComponentAdapter
	{
		
	    public void componentResized(ComponentEvent e)
		{
	    	resize = true;
			repaint();
		}
	}
}

class PointComparable extends Point2D implements Comparable<PointComparable>
{
	double x;
	double y;
	
	public PointComparable()
	{
		super();
	}
	
	public PointComparable(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX() 
	{
		return x;
	}

	@Override
	public double getY() 
	{
		return y;
	}
	
	@Override
	public void setLocation(double x, double y) 
	{
		this.x = x;
		this.y = y;
	}
	@Override
	public int compareTo(PointComparable o) 
	{
		int compared = 0;
		if(this.getX() == o.getX() && this.getY() == o.getY())
			compared = 0;
		if(this.getX() < o.getX() || this.getX() == o.getX() && this.getY() < o.getY())
			compared = -1;
		if(this.getX() > o.getX() || this.getX() == o.getX() && this.getY() > o.getY())
			compared = 1;
	
		return compared;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = java.lang.Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = java.lang.Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointComparable other = (PointComparable) obj;
		if (java.lang.Double.doubleToLongBits(x) != java.lang.Double.doubleToLongBits(other.x))
			return false;
		if (java.lang.Double.doubleToLongBits(y) != java.lang.Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
