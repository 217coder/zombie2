import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Zombie extends Component
{
	private double x, y, height, width, diam;
	private double xSpeed, ySpeed, xDirection, yDirection, xBoundry, yBoundry, speedTotal, speedSteady;
	private double life, killCount, areaOfEffect, sightDistance, nearestTarget=-1;
	private int testColision=0,player=0;
	private Color color;
	private DrawingPanel panel;
	private Vector lines;
	
	Zombie(DrawingPanel p, int d,Vector l)
	{
		Random r = new Random();
		panel = p;
		lines = l;
		x = r.nextInt(panel.getWidth()); //random starting x
		y = r.nextInt(panel.getHeight()); //random starting y
		
		diam=d;
		
		color = Color.GREEN;
		
		killCount = 0;

		xSpeed=1;
		ySpeed=1;
		
		if(r.nextInt(2)==1){
			xDirection = -1;	
		}
		else{
			xDirection = 1;
		}
		if(r.nextInt(2)==1){
			yDirection = -1;	
		}
		else{
			yDirection = 1;
		}
		
		sightDistance = diam * 5;//* 8
		areaOfEffect = diam;
		
		speedTotal=2;
		speedSteady=speedTotal;
		life = r.nextInt(1000) + 500;
			
		height = diam;
		width = diam;
		
		System.out.println("xSpeed: "+xSpeed+" | ySpeed: "+ySpeed+" | speedTotal: "+speedTotal);
	}
	
	Zombie(DrawingPanel p, int d, Vector l, double xloc, double yloc)
	{
		Random r = new Random();
		panel = p;
		lines = l;
		x = xloc;
		y = yloc;
		
		diam=d;
		
		color = Color.GREEN;
		
		killCount = 0;

		xSpeed=1;
		ySpeed=1;
		
		if(r.nextInt(2)==1){
			xDirection = -1.0;	
		}
		else{
			xDirection = 1.0;
		}
		if(r.nextInt(2)==1){
			yDirection = -1.0;	
		}
		else{
			yDirection = 1.0;
		}
		
		sightDistance = diam * 50; //*8
		areaOfEffect = diam;
		
		speedTotal=2;
		speedSteady=speedTotal;
		
		life = r.nextInt(1000) + 200;
			
		height = diam;
		width = diam;
	}
	
	Zombie(DrawingPanel p, int d,Vector l, int playerNumber)
	{
		Random r = new Random();
		panel = p;
		lines = l;
		x = r.nextInt(panel.getWidth()); //random starting x
		y = r.nextInt(panel.getHeight()); //random starting y
		
		diam=d;
		
		color = Color.GREEN;
		
		killCount = 0;

		xSpeed=1;
		ySpeed=1;
		
		xDirection=0;
		yDirection=0;
		
		sightDistance = diam * 5;//* 8
		areaOfEffect = diam;
		
		speedTotal=2;
		speedSteady=speedTotal;
		life = r.nextInt(1000) + 500;
			
		height = diam;
		width = diam;
		
		System.out.println("xSpeed: "+xSpeed+" | ySpeed: "+ySpeed+" | speedTotal: "+speedTotal);
		player=playerNumber;
        /*addKeyListener(new KeyAdapter()
            {
            public void keyPressed(KeyEvent ke)
                {
                if(ke.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    yDirection=1;
                }
                if(ke.getKeyCode() == KeyEvent.VK_UP)
                {
                    yDirection=-1;
                }
                if(ke.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    xDirection=-1;
                }
                if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                   	xDirection=1;
                }
             }
    	});*/
	}
	
    
    // Add the action to the component
	
	
	public void moveInSpace()
	{
		
		if(x+diam>panel.getWidth()||x-diam<0)//bounce
		{
			xDirection *= -1;
			if(x>diam){
				x=panel.getWidth()-diam;
			}
			else
			{
				x=diam;
			}
		}
		if(y+diam>panel.getHeight()||y-diam<0)//bounce
		{
			yDirection *= -1;
			if(y>diam){
				y=panel.getHeight()-diam;	
			}
			else{
				y=diam;
			}
		}
		x+=xSpeed*xDirection;
		y+=ySpeed*yDirection;
		life-=1;
		nearestTarget=-1;
		//System.out.println("Life:"+life);
	}
	
	public void collisionBounce()
	{
		xDirection *= -1;
		yDirection *= -1;
	}
	
	public double getXLocation()
	{
		return x;
	}
	
	public double getYLocation()
	{
		return y;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public double getSight()
	{
		return sightDistance;
	}
	
	public double getEffectArea()
	{
		return areaOfEffect;
	}
	
	public void increaseXDirection()
	{
		if(xDirection<1){
			xDirection+=1;
		}
	}
	
	public void decreaseXDirection()
	{
		if(xDirection>-1){//I don't want any multipliers
			xDirection-=1;
		}
	}
	
	public void increaseYDirection()
	{
		if(yDirection<1){
			yDirection+=1;
		}
	}
	
	public void decreaseYDirection()
	{
		if(yDirection>-1){//I don't want any multipliers
			yDirection-=1;
		}
	}
	
	public void increaseKillCount()
	{
		killCount++;
		//also, increase speed
		if(killCount%5==4)
		{
			//xSpeed++; //blood hunger grows me!
			//ySpeed++;
			speedSteady++;
			System.out.println("The brains give me strength!"+speedSteady);
		}
		speedTotal=speedSteady;
		Random r = new Random();
		life += r.nextInt(200);			
		
	}
	public void improvedSeek(Human target)
	{
		if(player==0){
			double tx, ty, theta;
			tx = target.getXLocation()-x;
			ty = target.getYLocation()-y;
			if(tx*tx+ty*ty<nearestTarget||nearestTarget==-1)
			{
				nearestTarget = tx*tx+ty*ty;
				//System.out.println("xSpeed: "+xSpeed+" | ySpeed: "+ySpeed);
				if(!Double.isNaN(tx)&&!Double.isNaN(ty)){
					theta = Math.atan(Math.abs(ty/tx));
					//System.out.println("theta: "+theta+" | abs(ty): "+Math.abs(ty)+" | abs(tx): "+Math.abs(tx));
					xSpeed = Math.abs(speedTotal * Math.cos(theta));
					ySpeed = Math.abs(speedTotal * Math.sin(theta));
					/*System.out.println("xSpeed: "+xSpeed+" | ySpeed: "+ySpeed+
						" | cos(theta): "+Math.cos(theta)+
						" | sin(theta): "+Math.sin(theta));*/
				}
				
				//System.out.println("tx: "+tx+" | ty: "+ty);
				if((tx<=0)&&(ty<=0))//upper left of zomb
				{
					xDirection=-1.0;
					yDirection=-1.0;
				}
				else if(tx<=0) //y must be greater, lower left of zombe
				{
					xDirection=-1.0;
					yDirection=1.0;
		
				}
				else if((tx>0)&&(ty<=0))//upper right of zomb
				{
					xDirection=1.0;
					yDirection=-1.0;
		
				}
				else //lower right of zombie
				{
					xDirection=1.0;
					yDirection=1.0;
		
				}
			}//end if nearest
		}		
	}
	public void seek(Human target)
	{
		double tx, ty, quad;
		tx = target.getXLocation()-x;
		ty = target.getYLocation()-y;
		System.out.println("x: "+x+" | y: "+y+" | tx: "+tx+" | ty: "+ty);
		if(Math.abs(ty)<=Math.abs(tx/2))
		{
			xSpeed=speedTotal;
			ySpeed=0;
			//1
		}
		else if(Math.abs(tx)<=Math.abs(ty/2))
		{
			xSpeed=0;
			ySpeed=speedTotal;
			//3
		}
		else
		{
			xSpeed=speedTotal/2;
			ySpeed=speedTotal/2;
			//2
		}
		if((tx<=0)&&(ty<=0))//upper left of zomb
		{
			xDirection=-1.0;
			yDirection=-1.0;
			System.out.println("Quad: Upperleft");
		}
		else if(tx<=0) //y must be greater, lower left of zombe
		{
			xDirection=-1.0;
			yDirection=1.0;
			System.out.println("Quad: Lowerleft");

		}
		else if((tx>0)&&(ty<=0))//upper right of zomb
		{
			xDirection=1.0;
			yDirection=-1.0;
			System.out.println("Quad: Upperright");

		}
		else //lower right of zombie
		{
			xDirection=1.0;
			yDirection=1.0;
			System.out.println("Quad: Lowerright");

		}
		
	}
	
	public int lifeStatus() 
	{
		int dead=0; //rather than undead?
		if(life<0)
		{
			dead = 1;
		}
		return dead;

	}
	
	public void paint(Graphics g)
	{
		if(player==1){
			g.setColor(Color.RED);
		}
		else{
			g.setColor(color);
		}

		g.fillOval((int)x, (int)y, (int)height, (int)width);
	}	
	
}
