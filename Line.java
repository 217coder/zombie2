import java.awt.*;
import java.util.*;

public class Line extends Component
{
	double xloc1, xloc2, yloc1, yloc2, lifespan;
	double slope, xIntercept, yIntercept;
	double minX, maxX, minY, maxY;
	int quadrant=0;//kinda confusing. Can be 1 or 2, but nothing else
	Color color;
	
	public Line(double x1,double x2, double y1, double y2){
		xloc1=x1;
		xloc2=x2;
		yloc1=y1;
		yloc2=y2;
		lifespan=3;
		color=Color.RED;
		setSlopeAndIntercepts();
		setMinAndMax();
		setQuadrant();
	}
	public Line(DrawingPanel p){
		Random r = new Random();
		xloc1=r.nextInt(p.getWidth());
		xloc2=r.nextInt(p.getWidth());
		yloc1=r.nextInt(p.getHeight());
		yloc2=r.nextInt(p.getHeight());
		//System.out.println("xloc1: "+xloc1+" | xloc2: "+xloc2+" | yloc1: "+yloc1+" | yloc2: "+yloc2);
		setSlopeAndIntercepts();
		setMinAndMax();
		setQuadrant();
		color=Color.RED;
	}
	public double getSlope(){
		return slope;
	}
	public double getXIntercept(){
		return xIntercept;
	}
	public double getYIntercept(){
		return yIntercept;
	}
	public double getMinX(){
		return minX;
	}
	public double getMaxX(){
		return maxX;
	}
	public double getMinY(){
		return minY;
	}
	public double getMaxY(){
		return maxY;
	}
	public double getFirstXLocation(){
		return xloc1;
	}
	public double getFirstYLocation(){
		return yloc1;
	}
	public double getSecondXLocation(){
		return xloc2;
	}
	public double getSecondYLocation(){
		return yloc2;
	}
	public int getQuadrant(){
		return quadrant;
	}
	
	private void setSlopeAndIntercepts(){
		slope = (yloc1-yloc2)/(xloc1-xloc2);
		xIntercept = (-yloc2/slope)+xloc2;
		yIntercept = slope * (-xloc2) + yloc2;
		System.out.println("xIntercept - isInfinite: "+Double.isInfinite( xIntercept ));
		System.out.println("slope: "+slope+" | xIntercept: "+xIntercept+" | yIntercept: "+yIntercept);
	}
	
	private void setMinAndMax(){
		if(xloc1<xloc2){
			minX=xloc1;
			maxX=xloc2;
		}
		else{
			minX=xloc2;
			maxX=xloc1;
		}
		if(yloc1<yloc2){
			minY=yloc1;
			maxY=yloc2;
		}
		else{
			minY=yloc2;
			maxY=yloc1;
		}
	}
	
	private void setQuadrant(){
		if(xloc1<xloc2){ //xloc1 is behind xloc2
			if(yloc1<yloc2){ //yloc1 is above yloc2
				quadrant=2;// `-.
			}
			else {//yloc1 is above yloc2
				quadrant=1;// ,-'
			}
		}
		else { //xloc2 is behind xloc1
			if(yloc1<yloc2){ //yloc1 is above yloc2
				quadrant=1;// ,-'
			}
			else {//yloc2 is above yloc1
				quadrant=2;// `-.
			}
		}
	}
	
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.drawLine((int)xloc1,(int)yloc1,(int)xloc2,(int)yloc2);
		g.setColor(Color.BLUE);
		g.fillOval((int)xloc1,(int)yloc1,5,5);
		g.setColor(Color.GREEN);
		g.fillOval((int)xloc2,(int)yloc2,5,5);
		//System.out.println("xloc1: "+(int)xloc1+" | xloc2: "+(int)xloc2+" | yloc1: "+(int)yloc1+" | yloc2: "+(int)yloc2);
	}
		
}
