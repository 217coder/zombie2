import java.awt.*;
import java.util.*;

public class Laser extends Component
{
	double xloc1, xloc2, yloc1, yloc2, lifespan;
	Color color;
	
	public Laser(double x1,double x2, double y1, double y2){
		xloc1=x1;
		xloc2=x2;
		yloc1=y1;
		yloc2=y2;
		lifespan=3;
		color=Color.PINK;
	}
	public Laser(double x1,double x2, double y1, double y2, Color c){
		xloc1=x1;
		xloc2=x2;
		yloc1=y1;
		yloc2=y2;
		lifespan=500;
		color=c;
	}
	
	private void move(){
		xloc1-=1;
		yloc1-=1;
	}
	
	public void paint(Graphics g)
	{
		if(xloc2>=0){
			g.setColor(color);
			g.drawLine((int)xloc1,(int)xloc2,(int)yloc1,(int)yloc2);
			move();
		}
	}
		
}
