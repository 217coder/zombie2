import java.awt.*;
import java.util.*;

public class Mech extends Component
{
	private double x, y, height, width, diam;
	private double xSpeed, ySpeed, xDirection=1, yDirection=1, xBoundry, yBoundry, speedTotal;
	private double life, killCount=0, areaOfEffect, sightDistance, nearestTarget, hasShot=0;
	private double targetX,targetY;
	private int breedCount=0, ammoCount=0, meleeCount=0;
	private Color color;
	private DrawingPanel panel;
	private Vector lasers, lines;
	
	Mech(DrawingPanel p, int d, Vector l)
	{
		lasers=new Vector();
		Random r = new Random();
		panel = p;
		lines = l;
		x = r.nextInt(panel.getWidth()); //random starting x
		y = r.nextInt(panel.getHeight()); //random starting y
		
		color = Color.darkGray;
		diam=d*3;
		//set life based on color
		life = r.nextInt(5)+10;

		xSpeed = r.nextInt(4)+1;
		ySpeed = r.nextInt(4)+1;
		speedTotal=xSpeed+ySpeed;
		breedCount = r.nextInt(10)+2; //can generate 2-3 more survivors before turning into a gunsman
		sightDistance = diam * 10;
		areaOfEffect = diam * 5;
		nearestTarget=-1;
				
		if(r.nextInt(2)==1) //random direction
		{
			xDirection = -1;
		}
		if(r.nextInt(2)==1) //random direction
		{
			yDirection = -1;
		}
		
		height = diam;
		width = diam;
	}
	
	Mech(DrawingPanel p, int d, Vector l, double xloc, double yloc)
	{
		lasers=new Vector();
		Random r = new Random();
		panel = p;
		lines = l;
		x = xloc;
		y = yloc;
		
		color = Color.darkGray;
		diam=d*3;
		//set life based on color
		life = r.nextInt(5)+10;

		xSpeed = r.nextInt(4)+1;
		ySpeed = r.nextInt(4)+1;
		speedTotal=xSpeed+ySpeed;
		breedCount = r.nextInt(10)+2; //can generate 2-3 more survivors before turning into a gunsman
		sightDistance = diam * 10;
		areaOfEffect = diam * 5;
				
		if(r.nextInt(2)==1) //random direction
		{
			xDirection = -1;
		}
		if(r.nextInt(2)==1) //random direction
		{
			yDirection = -1;
		}
		
		height = diam;
		width = diam;
	}
	
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
		nearestTarget=-1;
		hasShot=0;
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
	
	
	public void decreaseBreedCount()
	{
		breedCount--;
	}
	
	public void decreaseAmmunitions()
	{
		if(ammoCount>0)
		{
			ammoCount--;
		}
		else
		{
			meleeCount--;
		}
	}
	
	public int hasWeapon()//if they are millitary
	{
		int answer=0;
		if (ammoCount > 0 || meleeCount > 0)
		{
			answer = 1;
		}
		return answer;
	}
	
	public void fireLaser(double xt, double yt){
		//pew pew
		//System.out.println("x: "+x+" | y: "+y+" | xt: "+xt+" | yt: "+yt);
		if(hasShot==0)
		{
			lasers.addElement(new Laser(x,y,xt,yt));
			hasShot=1;
		}
	}
	
	public void isAttacked(){
		life--;
	}
	/*
	public void increaseKillCount()
	{
		killCount++;
		if(color==Color.GREEN)
		{
			//also, increase speed
			if(killCount%5==4)
			{
				//xSpeed++; //blood hunger grows me!
				//ySpeed++;
				speedTotal++;
			}			
		}
	}
	*/
	public void improvedSeek(Human target,double distanceToTarget)
	{
		//System.out.println("NearestTarget: "+nearestTarget);
		if(distanceToTarget<nearestTarget||nearestTarget==-1){
			nearestTarget=distanceToTarget;
			double tx, ty, theta;
			tx = target.getXLocation()-x;
			ty = target.getYLocation()-y;
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
		}//to check if closer
	}
	
	public void improvedSeek(Zombie target,double distanceToTarget)
	{
		if(distanceToTarget<nearestTarget||nearestTarget==-1){
			nearestTarget=distanceToTarget;
			double tx, ty, theta;
			tx = target.getXLocation()-x;
			ty = target.getYLocation()-y;
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
		}
	}
		
	public void seek(Zombie target)
	{
		double tx, ty, quad;
		tx = target.getX()-x;
		ty = target.getY()-y;
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
			xDirection=-1;
			yDirection=-1;
		}
		else if(tx<=0) //y must be greater, lower left of zombe
		{
			xDirection=-1;
			yDirection=1;

		}
		else if((tx>0)&&(ty<=0))//upper right of zomb
		{
			xDirection=1;
			yDirection=-1;

		}
		else //lower right of zombie
		{
			xDirection=1;
			yDirection=1;

		}
		
	}
	
	

	
	public int getStatus() //can also be used to see how blues are doing
	{
		int deathstatus=0;
		
		if(life<0){
			deathstatus=1;
		}
		/*if(color==Color.ORANGE)//if human
		{
			if(breedCount<1)//they are read to fight
			{
				convertToBasicMillitary();
				deathstatus=2;
			}
		}
		
		else if(color==Color.BLUE)//if millitary
		{
			if(ammoCount<1)//out of ammo
			{
				areaOfEffect=diam;
			}
			if(meleeCount<1)//their time is up
			{
				deathstatus=1;//death is imminet
			}
		}
		
		else if(color==Color.GRAY)//if corpse waiting to rise
		{
			if (life<0)//raise for the dead
			{
				//convertToBasicZombie();
				deathstatus=3;
			}
		}*/
		
		return deathstatus;

	}

	
	public void paint(Graphics g)
	{
		g.setColor(color);

		g.fillOval((int)x, (int)y, (int)height, (int)width);
		
		if(lasers!=null){
			for(int i=0;i<lasers.size();i++){
				((Laser)lasers.elementAt(i)).paint(g);
			}
		}
		/*if(color==Color.BLUE)
		{
			g.setColor(Color.RED);
			//center = x + diam/2
			//x = x+diam/2-areaOfEffect/2
			g.drawOval( x + diam/2 - areaOfEffect/2 , y + diam/2 - areaOfEffect/2,areaOfEffect,areaOfEffect);
		}*/
	}	
	
}
