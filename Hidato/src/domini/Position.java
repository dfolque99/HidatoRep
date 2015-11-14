/**
 * @author felix.axel.gimeno
 * @since 2015-11-14
 * position class, stores x,y position
 */
package domini;
public class Position{
	private Integer x;
	private Integer y;
	public Integer getX() {return x;}
	public Integer getY() {return y;}
	public void setX(Integer x) {this.x = x;}
	public void setY(Integer y) {this.y = y;}
	@SuppressWarnings("unused")
	private Position(){throw new UnsupportedOperationException();}
	public Position(Integer x, Integer y){this.x=x; this.y=y;}
	public boolean equals(Position p) {
		if (this.x == p.x && this.y == p.y) return true;
		return false;
	}
	public static boolean notEnoughDistance(int n, Position p1, int m, Position p2){
		return (Math.min(Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y)) > Math.abs(n-m));	
	}
}