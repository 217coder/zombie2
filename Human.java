import java.awt.*;
import java.util.*;
import java.awt.geom.Point2D;

public class Human extends Component
{
	private double x, y, height, width, diam;
	private double xSpeed, ySpeed, xDirection=1, yDirection=1, xBoundry, yBoundry, speedTotal;
	private double xVectorSpeed, yVectorSpeed;
	private double life, killCount=0, areaOfEffect, sightDistance, nearestTarget;
	private int breedCount=0, ammoCount=0, meleeCount=0;
	private Color color;
	private DrawingPanel panel;
	private Vector lines;
	
	Human(DrawingPanel p, int d, Vector l)
	{
		Random r = new Random();
		panel = p;
		lines = l;
		x = r.nextInt(panel.getWidth()); //random starting x
		y = r.nextInt(panel.getHeight()); //random starting y
		
		color = Color.ORANGE;
		diam=d;
		//set life based on color
		life = r.nextInt(200)+10000;

		xSpeed = r.nextInt(3)+1;
		ySpeed = r.nextInt(3)+1;
		speedTotal=xSpeed+ySpeed;
		breedCount = r.nextInt(10)+2; //can generate 2-3 more survivors before turning into a gunsman
		sightDistance = diam * 3;
		areaOfEffect = diam;
				
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
		checkLineBounce();
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
	public void improvedSeek(Zombie target)
	{
		
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
	
	
	public void checkLineBounce(){
		int i;
		int aboveOrBelow;
		for (i=0;i<lines.size();i++){
			//System.out.println("I am in a loop of some sort.");
			if(checkIfWithinLineBox(i)==1){
				//System.out.println("i within the paramiters of "+i+" | x: "+x+" | y: "+y+" |xs:"+xSpeed+" |yx:"+ySpeed);
				if( Double.isInfinite(((Line)lines.elementAt(i)).getXIntercept()) == false ){
					aboveOrBelow=checkXInterceptCross(i);
					if(aboveOrBelow!=0){
						System.out.println("I walk the X line");
						lineBounce(i,1);
					}
				}
				else {
					//System.out.println("xi: "+((Line)lines.elementAt(i)).getXIntercept()+" | inf: "+ Double.isInfinite(((Line)lines.elementAt(i)).getXIntercept()));
					aboveOrBelow=checkYInterceptCross(i);
					if(aboveOrBelow==1){
						System.out.println("I walk the Y line");
						lineBounce(i,2);
					}
				}
					
			}
			//is it within the box needed to bounce?
			//check wall bounce on i
			//initial intercept = formula
			//if moved, would intercept pass line intercept
			//if yes, bounce
		}
	}
	
	
	public int checkIfWithinLineBox(int lineIndex){
		int within=0;
		double maxX, maxY, minX, minY;
		maxX = ((Line)lines.elementAt(lineIndex)).getMaxX();
		minX = ((Line)lines.elementAt(lineIndex)).getMinX();
		maxY = ((Line)lines.elementAt(lineIndex)).getMaxY();
		minY = ((Line)lines.elementAt(lineIndex)).getMinY();
		
		if(x<maxX&&x>minX && y<maxY&&y>minY){
				within=1;					
		}
		else if(Double.isInfinite(((Line)lines.elementAt(lineIndex)).getXIntercept()) == true){
			if(x<maxX&&x>minX){
				within=1;
			}
		}
		else if(Double.isInfinite(((Line)lines.elementAt(lineIndex)).getYIntercept()) == true){
			if(y<maxY&&y>minY){
				within=1;
			}
		}
		else{
			/*System.out.println("x: "+x+" | y: "+y+
			" | maxX: "+((Line)lines.elementAt(lineIndex)).getMaxX()+
			" | minX: "+((Line)lines.elementAt(lineIndex)).getMinX()+
			" | maxY: "+((Line)lines.elementAt(lineIndex)).getMaxY()+
			" | minY: "+((Line)lines.elementAt(lineIndex)).getMinY());*/
		}
		return within;
	}
	
	public int checkXInterceptCross(int lineIndex){
		int result=0;
		double slope, initialXIntercept, finalXIntercept, lineXIntercept;
		slope = ((Line)lines.elementAt(lineIndex)).getSlope();
		initialXIntercept = (-y/slope)+x;
		finalXIntercept = (-(y+ySpeed*yDirection)/slope)+(x+xSpeed*xDirection);
		lineXIntercept = ((Line)lines.elementAt(lineIndex)).getXIntercept();
		if( ( lineXIntercept<initialXIntercept&&lineXIntercept>=finalXIntercept ) ||
			( lineXIntercept>initialXIntercept&&lineXIntercept<=finalXIntercept ) ){
				if(initialXIntercept>lineXIntercept){ //below line
			 		result = -1;
				}
				else{
					result = 1;
				}
		}
		
		return result;	 
	}
	
	public int checkYInterceptCross(int lineIndex){
		int result=0;
		double slope, initialYIntercept, finalYIntercept, lineYIntercept;
		slope = ((Line)lines.elementAt(lineIndex)).getSlope();
		initialYIntercept = slope * (-x) + y;
		finalYIntercept = slope * -(x+xSpeed*xDirection) + (y+ySpeed*yDirection);
		lineYIntercept = ((Line)lines.elementAt(lineIndex)).getYIntercept();
		if( ( lineYIntercept<initialYIntercept&&lineYIntercept>=finalYIntercept ) ||
			( lineYIntercept>initialYIntercept&&lineYIntercept<=finalYIntercept ) ){
			 	if(initialYIntercept>lineYIntercept){ //below line
			 		result = -1;
				}
				else{
					result = 1;
				}
		}
		
		return result;	 
	}
	
	public void lineBounce(int lineIndex, int whichIntercept){
		double theta, thetaLine,cosTheta,sinTheta, lineQuadrant, particleQuadrant,intercept,pSlope;
		double thetaA, thetaB;
		double xv1, xv2, yv1, yv2; //vectors
		
		
		xv1=xSpeed*xDirection;
		yv1=ySpeed*yDirection;
		xv2=((Line)lines.elementAt(lineIndex)).getFirstXLocation()-((Line)lines.elementAt(lineIndex)).getSecondXLocation();
		yv2=((Line)lines.elementAt(lineIndex)).getFirstYLocation()-((Line)lines.elementAt(lineIndex)).getSecondYLocation();
		
		theta=Math.acos(
			(xv1*xv2+yv1*yv2)/
			Math.sqrt( (quad(xv1)+quad(yv1))*(quad(xv2)+quad(yv2)) )
			);
		System.out.println("Theta: "+Math.toDegrees(theta));
		if(Math.toDegrees(theta)>90){ //if it it is more than 90 degrees, than we have the bigger of the two angles
		
		}
		else{ //it is less than 90 degrees, and is the angle that is easy, I think
			pSlope=getParticleSlope();
			intercept=getParticleYIntercept(pSlope);
			if(Double.isInfinite(intercept)!=true){
				Point a=new Point((int)x,(int)y);
				Point b=new Point(0,(int)intercept);
				Point c=new Point((int)x,(int)intercept);
				thetaB=getAngle(a,b,c);
				thetaB+=Math.toRadians(90);
				thetaB=thetaB-90-theta;
				xv1 = speedTotal * Math.cos(thetaB);
				yv1 = speedTotal * Math.sin(thetaB);
				xSpeed=Math.abs(xv1);
				ySpeed=Math.abs(yv1);
				if(xv1>0){
					xDirection=1;
				}
				else{
					xDirection=-1;
				}
				if(yv1>0){
					yDirection=1;
				}
				else{
					yDirection=-1;
				}
			}
			/*theta+=Math.toRadians(180)-2*theta;
			xv1 = speedTotal * Math.cos(theta);
			yv1 = speedTotal * Math.sin(theta);
			xSpeed=Math.abs(xv1);
			ySpeed=Math.abs(yv1);
			if(xv1>0){
				xDirection=1;
			}
			else{
				xDirection=-1;
			}
			if(yv1>0){
				yDirection=1;
			}
			else{
				yDirection=-1;
			}*/								
		}
		/*double xl1, xl2;
		xl1 = ((Line)lines.elementAt(lineIndex)).getFirstXLocation();
		xl2 = ((Line)lines.elementAt(lineIndex)).getSecondXLocation();
		y1 = ((Line)lines.elementAt(lineIndex)).getFirstYLocation();
		y2 = ((Line)lines.elementAt(lineIndex)).getSecondYLocation();
		if(xDirection==1){
			if(xl1<xl2){//we want xl2
				b=new Point2D.Double(xl2,((Line)lines.elementAt(lineIndex)).getSecondYLocation());
				System.out.println("Second");
			}
			else{
				b=new Point2D.Double(xl1,((Line)lines.elementAt(lineIndex)).getFirstYLocation());
				System.out.println("First");
			}
		}
		else{
			if(xl1>xl2){//we want xl2
				b=new Point2D.Double(xl2,((Line)lines.elementAt(lineIndex)).getSecondYLocation());
				System.out.println("Second");
			}
			else{
				b=new Point2D.Double(xl1,((Line)lines.elementAt(lineIndex)).getFirstYLocation());
				System.out.println("First");
			}
		}
		Point2D c=new Point2D.Double(b.getX(),a.getY());
		
		thetaA = getAngle(a, b, c);
		b = new Point2D.Double((x-(xSpeed*xDirection)),(y-(50*ySpeed*yDirection)));
		c = new Point2D.Double(b.getX(),a.getY());
		thetaB = getAngle(a, b, c);
		theta=180-Math.toDegrees(thetaA)-Math.toDegrees(thetaB);
		//theta=180-Theta;
		System.out.println("Theta: "+theta);*/
		
		
		
		/*if (whichIntercept==1){//use xIntercept
			intercept = ((Line)lines.elementAt(lineIndex)).getXIntercept();
			thetaLine = Math.acos(b.distance(b.getX(),0)/b.distance(intercept,0));	
		}
		else if (whichIntercept==2){ //use yIntercept
			
		}
		
		theta = Math.acos(
			(a.getX() * b.getX() + a.getY() * b.getY())
			/ a.distance(0,0) / b.distance(0,0) );
		System.out.println("Theta: "+Math.toDegrees(theta));
		cosTheta=Math.cos(theta);
		sinTheta=Math.sin(theta);
		xSpeed = Math.abs(speedTotal * cosTheta);
		ySpeed = Math.abs(speedTotal * sinTheta);
		System.out.println("cosTheata: "+cosTheta+" | sinTheata: "+sinTheta);
		lineQuadrant=((Line)lines.elementAt(lineIndex)).getQuadrant();
		if(lineQuadrant!=0){
			particleQuadrant=getParticleQuadrant();
			if(lineQuadrant==1){// ,-'
				if(particleQuadrant==1){// ,-'
					
				}
				else { // `-.
					xDirection*=-1;
					yDirection*=-1;
				}
			}
			else{ // `-.
				if(particleQuadrant==1){// ,-'
					xDirection*=-1;
					yDirection*=-1;
				}
				else { // `-.
				
				}
			}*/
			
		
		//below = higher intercept
		//above = lower intercept
	}
	
	private double getParticleSlope(){
		double slope=0,yloc2, xloc2;
		yloc2=y+ySpeed*yDirection;
		xloc2=x+xSpeed*xDirection;
		slope = (x-yloc2)/(x-xloc2);
		return slope;
	}
	private double getParticleYIntercept(double slope){
		double yloc2=y+ySpeed*yDirection;
		double xloc2=x+xSpeed*xDirection;
		double yIntercept = slope * (-xloc2) + yloc2;
		return yIntercept;
	}
	
	public double getAngle(Point a, Point b, Point c){
		double side_a = Math.sqrt( quad(b.x-c.x)+quad(b.y-c.y) );
		double side_b = Math.sqrt( quad(a.x-c.x)+quad(a.y-c.y) );
		double side_c = Math.sqrt( quad(a.x-b.x)+quad(a.y-b.y) );
		
		return Math.acos( (quad(side_b) - quad(side_a) - quad(side_c) )/ (-2 *side_a * side_c));
		
	}
	
	private double quad(double x){
		return x*x;
	}
	
	public int getParticleQuadrant(){
		int quadrant=0;
		if(xDirection>0){
			if(yDirection>0){
				quadrant=2;// `-.
			}
			else{
				quadrant=1;// ,-'
			}
		}
		else{
			if(yDirection>0){
				quadrant=1;// ,-'
			}
			else{
				quadrant=2;// `-.
			}
		}
		return quadrant;
	}
	
	//not implimented yet if at all
	public void flee(Zombie target)
	{
		if(target.getX()<x) //go right
		{
			xDirection = 1;
		}
		else //go left
		{
			xDirection = -1;
		}
		if(target.getY()<y) //go down
		{
			yDirection = 1;
		}
		else //go up
		{
			yDirection = -1;
		}	
	}

	
	public void improvedFlee(Zombie target){
		double tx, ty, theta;
		tx = target.getXLocation()-x;
		ty = target.getYLocation()-y;
		//if(tx*tx+ty*ty<nearestTarget||nearestTarget==-1)
		if(1==2)
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
			if((tx>=0)&&(ty>=0))//upper left of zomb
			{
				xDirection=-1.0;
				yDirection=-1.0;
			}
			else if(tx>=0) //y must be greater, lower left of zombe
			{
				xDirection=-1.0;
				yDirection=1.0;
	
			}
			else if((tx<0)&&(ty>=0))//upper right of zomb
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
	
	public int lifeStatus() //can also be used to see how blues are doing
	{
		int deathstatus=0;
		
		if(color==Color.ORANGE)//if human
		{
			if(breedCount<1)//they are read to fight
			{
				convertToBasicMillitary();
				deathstatus=2;
			}
			else if(life<0)
			{
				//convert to mech
				deathstatus=4;
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
		}
		
		return deathstatus;

	}
	
	private void convertToBasicMillitary()
	{
		Random r = new Random();
		color = Color.BLUE;
		life = r.nextInt(200)+100;
		areaOfEffect = diam * 3; //they have guns
		ammoCount = r.nextInt(6); //they don't have infiinite ammo/ranged shots
		meleeCount = r.nextInt(5);//they will eventually get bitten in hand to hand
		xSpeed++;
		ySpeed++;
		sightDistance = diam * 6;
		speedTotal=xSpeed+ySpeed;
		nearestTarget=-1;
	}
	
	public void bitByZombie()
	{
		Random r = new Random();
		life = r.nextInt(50)+10;
		color = Color.GRAY;
		xSpeed = 0;
		ySpeed = 0;
		sightDistance = 0;
		areaOfEffect = 0;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(color);

		g.fillOval((int)x, (int)y, (int)height, (int)width);
		
		/*g.setColor(Color.RED);
		
		g.drawLine((int)(x+(diam/2)),(int)(y+(diam/2)),
				   (int)(x+(diam/2)-(xSpeed*xDirection*50)),(int)(y+(diam/2)-(50*ySpeed*yDirection)));*/
		
		/*if(color==Color.BLUE)
		{
			g.setColor(Color.RED);
			//center = x + diam/2
			//x = x+diam/2-areaOfEffect/2
			g.drawOval( x + diam/2 - areaOfEffect/2 , y + diam/2 - areaOfEffect/2,areaOfEffect,areaOfEffect);
		}*/
	}	
	
}
