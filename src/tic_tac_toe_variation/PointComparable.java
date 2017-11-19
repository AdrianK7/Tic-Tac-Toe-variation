package tic_tac_toe_variation;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class PointComparable extends Point2D implements Comparable<PointComparable>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8351984064203745615L;
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
