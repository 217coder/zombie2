import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math;

public class GraphicsEngine extends Frame implements ActionListener
{
	private DrawingPanel drawingPanel;
	
	private Checkbox playBox;
	private Checkbox lifeBox;
	private Checkbox seekBox;
	
    private Label leftPrompt;
    private TextField leftInput;

    private Label rightPrompt;
    private TextField rightInput;
    
    private Label diamPrompt;
    private TextField diamInput;
    
    private Button runButton;
    private Button pauseButton;
    private Button resetButton;
    
    boolean running = false;
    //private Vector particles = new Vector();
    private Vector humans = new Vector();
    private Vector zombies = new Vector();
    private Vector mechs = new Vector();
    private Vector lines = new Vector();
    
    private Zombie playerControlledZombie;
    
    Random randGen;
    
    private int survivorCount,  millitaryCount;
//    private Vector zombie = new Vector();
    


	// you will probably want to change this for your project
	//private Ball ball;



    // This is an inner class
    // This inner class implements an ActionListener for the "Run" button 
    class ActionAdapter implements ActionListener 
    {
        public void actionPerformed(ActionEvent ev) 
        {
        	if (ev.getSource() == runButton)
        	{
        		//lines.addElement(new Line(drawingPanel));
        		//lines.addElement(new Line(drawingPanel));
        		//lines.addElement(new Line(5, 500, 200, 200));
        		//lines.addElement(new Line(200, 200, 5, 700));
				drawingPanel.setLines(lines);
        		try{
        			int count=0;
        			//System.out.println("leftInput:"+(Integer.parseInt(leftInput.getText())));
        			//make a survivor for how many they enter
        			for(int i=0;i<Integer.parseInt(leftInput.getText());i++){
        				humans.addElement(new Human(drawingPanel, Integer.parseInt(diamInput.getText()), lines));
        				survivorCount++;
        				//System.out.println("Count:"+count++);
					}
					drawingPanel.setHumans(humans);
					//make the zombies now
					
					if(playBox.getState()==true){ //create a human player
						playerControlledZombie=new Zombie(drawingPanel, Integer.parseInt(diamInput.getText()),lines,1);
						zombies.addElement(playerControlledZombie);	
					}
					
					for(int i=0;i<Integer.parseInt(rightInput.getText());i++){
        				zombies.addElement(new Zombie(drawingPanel, Integer.parseInt(diamInput.getText()), lines));
        			}
        			drawingPanel.setZombies(zombies);
        			pauseButton.setLabel("Pause");
        		}
        		catch(NumberFormatException ex)
				{
					System.out.println(ex.getMessage());
				}
				drawingPanel.setMechs(mechs);
	            running = true;
	            	            //for(int i;i<leftInput.ParamString)
	            //validate();
            }
            else if(ev.getSource() == pauseButton)
            {
            	//stuff
            	if(running == true){
	            	System.out.println("Pause");
	            	pauseButton.setLabel("Unpause");
	            	running = false;
            	}
            	else{
            		System.out.println("Resuming");
	            	pauseButton.setLabel("Pause");
	            	running = true;
            	}
            }
            
            else if (ev.getSource() == resetButton)
            {
            	humans.removeAllElements();
            	zombies.removeAllElements();
            	mechs.removeAllElements();
            	lines.removeAllElements();
            	survivorCount = 0;
            	millitaryCount = 0;
            	running=false;
            	drawingPanel.repaint();
            	System.out.println("Reset");
            }
        }
    }
	
	GraphicsEngine()
	{	

        addWindowListener
        (
            new WindowAdapter()
            {
                public void windowClosing(WindowEvent ev) 
                {
                    System.exit(0);
                }
            }
        ); 


        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu();
        MenuItem menuFileExit = new MenuItem();
        
        menuFile.setLabel("File");
        menuFileExit.setLabel("Exit");
        
        // Implement action, exit, for the "Exit" line in the "File" menu
        menuFileExit.addActionListener
        (
            new ActionListener() 
            {
                public void actionPerformed(ActionEvent ev) 
                {
                    System.exit(0);
                }
            }
        ); 
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        
        setMenuBar(menuBar);
        setSize(new Dimension(400, 400));
        
        // Implement action, exit, for the X button in the upper right corner 
        this.addWindowListener
        (
            new WindowAdapter() 
            {
                public void windowClosing(WindowEvent ev) 
                {
                    System.exit(0);
                }
            }
        );  

        setLayout(new BorderLayout());
        
		Panel topPanel = new Panel();
		add(topPanel, BorderLayout.NORTH);
		
		//seekPrompt = new Label();
		//seekPrompt.setText("Seek Mode");
		//topPanel.add(seekPrompt);
		
		playBox = new Checkbox("Interactive",null,false);
		topPanel.add(playBox);
		
		lifeBox = new Checkbox("Life Mode", null, false); //to detirm whether or not the zombies die of hunger
		topPanel.add(lifeBox);
		
		seekBox = new Checkbox("Seek Mode", null, true);
		topPanel.add(seekBox);
		
        leftPrompt = new Label();
        leftPrompt.setText("Survivors");
        topPanel.add(leftPrompt);
            
        leftInput = new TextField(6);
        leftInput.addActionListener(this);  
        topPanel.add(leftInput);
    
        rightPrompt = new Label();
        rightPrompt.setText("Zombies");
        topPanel.add(rightPrompt);
            
        rightInput = new TextField(6);
        rightInput.addActionListener(this);  
        topPanel.add(rightInput);
        
        diamPrompt = new Label();
        diamPrompt.setText("Diam");
        topPanel.add(diamPrompt);
        
        diamInput = new TextField(6);
        diamInput.addActionListener(this); 
        diamInput.setText("4"); 
        topPanel.add(diamInput);
        
        runButton = new Button("Run");
        topPanel.add(runButton);
        // Create ActionListener for the "Run" button 
        runButton.addActionListener(new ActionAdapter ()); 
        
        pauseButton = new Button("-Pause-");
        topPanel.add(pauseButton);
        pauseButton.addActionListener(new ActionAdapter ()); 
        
        resetButton = new Button("Reset");
        topPanel.add(resetButton);
        resetButton.addActionListener(new ActionAdapter ());
 	
 		add(topPanel, BorderLayout.NORTH);
 		
 		drawingPanel = new DrawingPanel();
		add(drawingPanel, BorderLayout.CENTER);	

        setSize(1000, 700);
		setVisible(true);
		
		randGen = new Random();
		
		survivorCount = 0;
		millitaryCount = 0;
		
		// You will probably want to create your initial objects here
		
		runButton.addKeyListener(new KeyAdapter()
            {
            public void keyPressed(KeyEvent ke)
                {
               	if(playerControlledZombie!=null){
	                if(ke.getKeyCode() == KeyEvent.VK_DOWN)
	                {
	                    playerControlledZombie.increaseYDirection();
	                    System.out.println("Down was pressed");
	                }
	                if(ke.getKeyCode() == KeyEvent.VK_UP)
	                {
	                    playerControlledZombie.decreaseYDirection();
	                    System.out.println("Up was pressed");
	                }
	                if(ke.getKeyCode() == KeyEvent.VK_LEFT)
	                {
	                    playerControlledZombie.decreaseXDirection();
	                    System.out.println("Left was pressed");
	                }
	                if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
	                {
	                   	playerControlledZombie.increaseXDirection();
	                   	System.out.println("Right was pressed");
	                }
                }
             }
    	});




	}
	
	private void checkCollisions()
	{
		double x1, x2, x3, y1, y2, y3, radius, r1, r2, nearest, distanceForSight;
		int i, j;
		double sightA, sightB;
		Color c1, c2;
		//radius is completely the wrong term. I think it is supposed to be diamiter
		
		for(i=0;i<humans.size();i++){ //for all survivors
			x1=((Human)humans.elementAt(i)).getXLocation();
			y1=((Human)humans.elementAt(i)).getYLocation();
			r1=((Human)humans.elementAt(i)).getSight();
			//sightA = ((Human)humans.elementAt(i)).getSight();
			
			for(j=0;j<zombies.size();j++) //for all zombies
			{
				x2=((Zombie)zombies.elementAt(j)).getXLocation();
				y2=((Zombie)zombies.elementAt(j)).getYLocation();
				//c2=((Zombie)zombies.elementAt(j)).getColor();
				r2=((Zombie)zombies.elementAt(j)).getSight();
				//sightB = ((Zombie)zombies.elementAt(i)).getEffectArea();
				x3 = x1-x2;
				y3 = y1-y2;
				if (r1>r2){
					radius = r1;
				}
				else{
					radius = r2;
				}
				
				/*System.out.println("Color1:"+c1);
				System.out.println("X1:"+x1+"|X2:"+x2+"|X3:"+x3+"|y1:"+y1+"|y2:"+y2+"|y3:"+y3);
				System.out.println("Sqrt:"+Math.sqrt(Math.pow(x3,2)+Math.pow(y3,2)));
				System.out.println("----------------------------------------------");*/
				
				radius *= radius; //square
				x3 *= x3;
				y3 *= y3;
				distanceForSight=x3+y3;
				
				//if(Math.sqrt(Math.pow(x3,2.0)+Math.pow(y3,2.0))<radius) //touching

				if(distanceForSight < radius) //one can see the other
				{
					//now to see if those two touch
					r1=((Human)humans.elementAt(i)).getEffectArea();
					r2=((Zombie)zombies.elementAt(j)).getEffectArea();
					if (r1>r2){
						radius = r1;
					}
					else{
						radius = r2;
					}
					radius *= radius; //square
					if(x3 + y3 < radius) //collide, else seek/flee
					{	
						//collide
						//System.out.println("Collision");
						//zombie vs human, zombie wins
						//c1 wins
						if(((Human)humans.elementAt(i)).hasWeapon()==1)
						{
							//mill, attack zomb
							((Human)humans.elementAt(i)).decreaseAmmunitions();
							zombies.remove(j);
						}
						else //no weapon, eaten by zombs
						{
							if(((Human)humans.elementAt(i)).getColor()==Color.BLUE)
							{
								millitaryCount--;
								((Human)humans.elementAt(i)).bitByZombie();
								((Zombie)zombies.elementAt(j)).increaseKillCount();
							}
							else if(((Human)humans.elementAt(i)).getColor()==Color.ORANGE)
							{
								survivorCount--;
								((Human)humans.elementAt(i)).bitByZombie();
								((Zombie)zombies.elementAt(j)).increaseKillCount();
							}
							
						}
					}
					else
					{
						//seek if mode is on
						if ((seekBox.getState()==true)&&(((Human)humans.elementAt(i)).getColor()!=Color.GRAY))//seek mode on
						{
							//seek
							seekOrFlee(i, j, distanceForSight);
						}
					}
						
						
				} //distance form
			}//end of for zombies
			r1 = ((Human)humans.elementAt(i)).getEffectArea();//to fix for the sight issue
			//System.out.println("SIZE OF HUM: "+humans.size()+" | i = "+i);
			if( ((Human)humans.elementAt(i)).getColor()==Color.ORANGE ) //millitary can't breed
			{
				for(j=i;j<humans.size();j++) //for human breeding
				{
					if(j!=i)
					{
						if( ((Human)humans.elementAt(j)).getColor()==Color.ORANGE ) //military and the dead can't breed
						{
							x2=((Human)humans.elementAt(j)).getXLocation();
							y2=((Human)humans.elementAt(j)).getYLocation();
							r2=((Human)humans.elementAt(j)).getEffectArea();
							x3 = x1-x2;
							y3 = y1-y2;
							if (r1>r2){
								radius = r1;
							}
							else{
								radius = r2;
							}
							/*System.out.println("Color1:"+c1);
							System.out.println("X1:"+x1+"|X2:"+x2+"|X3:"+x3+"|y1:"+y1+"|y2:"+y2+"|y3:"+y3);
							System.out.println("Sqrt:"+Math.sqrt(Math.pow(x3,2)+Math.pow(y3,2)));
							System.out.println("----------------------------------------------");*/
							
							radius *= radius; //square
							x3 *= x3;
							y3 *= y3;
							
							//if(Math.sqrt(Math.pow(x3,2.0)+Math.pow(y3,2.0))<radius) //touching
			
							if(x3 + y3 < radius)
							{
								((Human)humans.elementAt(j)).collisionBounce();
								((Human)humans.elementAt(i)).collisionBounce();
								((Human)humans.elementAt(j)).decreaseBreedCount();
								((Human)humans.elementAt(i)).decreaseBreedCount();
								try{
									humans.addElement(new Human(drawingPanel,Integer.parseInt(diamInput.getText()),lines));
									survivorCount++;
								}
				        		catch(NumberFormatException ex)
								{
									System.out.println(ex.getMessage());
								}
								
							} //end of distance form
						}//end of millitary j if
					}//end of if
				} //end of human breed
			}//end of milliitary i if
		}//end of all human/zombie/human conflict
		mechCollisions();
	}
	
	private void mechCollisions(){
		double x1, x2, x3, y1, y2, y3, radius, r1, r2;
		int i, j;
		int hasShot;
		Color c1, c2;
		for(i=0;i<mechs.size();i++){
			hasShot=0;
			//System.out.println("hasShot: "+hasShot);
			x1=((Mech)mechs.elementAt(i)).getXLocation();
			y1=((Mech)mechs.elementAt(i)).getYLocation();
			r1=((Mech)mechs.elementAt(i)).getSight();
			//sightA = ((Human)humans.elementAt(i)).getSight();
			
			for(j=0;j<zombies.size();j++) //for all zombies
			{
				x2=((Zombie)zombies.elementAt(j)).getXLocation();
				y2=((Zombie)zombies.elementAt(j)).getYLocation();
				r2=((Zombie)zombies.elementAt(j)).getSight();
				
				x3 = x1-x2;
				y3 = y1-y2;
				if (r1>r2){
					radius = r1;
				}
				else{
					radius = r2;
				}
				radius *= radius; //square
				x3 *= x3;
				y3 *= y3;
				
				if(x3 + y3 < radius) //one can see the other
				{
					//now to see if those two touch
					r1=((Mech)mechs.elementAt(i)).getEffectArea();
					r2=((Zombie)zombies.elementAt(j)).getEffectArea();
					r1*=r1;
					r2*=r2;
					if(x3+y3<r2)
					{
						//decrees mech health and bounce zombie
						((Mech)mechs.elementAt(i)).isAttacked();
						((Zombie)zombies.elementAt(j)).collisionBounce();
					}
					if(x3 + y3 < r1 && hasShot != 1) //collide, else seek/flee
					{	
						//collide
						//laser, pew pew?
						//System.out.println("Pewpew: "+j+" | Model #3879J"+i+"J890");
						hasShot=1;
						//System.out.println("xt: "+((Zombie)zombies.elementAt(j)).getX()+" | yt: "+((Zombie)zombies.elementAt(j)).getY());
						((Mech)mechs.elementAt(i)).fireLaser(((Zombie)zombies.elementAt(j)).getXLocation(),((Zombie)zombies.elementAt(j)).getYLocation());
						
						zombies.remove(j);
					}
					else //seek
					{
						if (seekBox.getState()==true)//seek mode on
						{
							//seek
							((Mech)mechs.elementAt(i)).improvedSeek((Zombie)zombies.elementAt(j),x3+y3);
						}
					}
					
				}//sigth if
			}//end of zombie for all
			//if(hasShot!=1){
			
			for(j=0;j<humans.size();j++) //for all humans
			{
				if(((Human)humans.elementAt(j)).getColor()!=Color.GRAY){
					x2=((Human)humans.elementAt(j)).getXLocation();
					y2=((Human)humans.elementAt(j)).getYLocation();
					r2=((Human)humans.elementAt(j)).getSight();
					
					x3 = x1-x2;
					y3 = y1-y2;
					if (r1>r2){
						radius = r1;
					}
					else{
						radius = r2;
					}
					radius *= radius; //square
					x3 *= x3;
					y3 *= y3;
					
					if(x3 + y3 < radius) //one can see the other
					{
						//now to see if those two touch
						r1=((Mech)mechs.elementAt(i)).getEffectArea();
						r2=((Human)humans.elementAt(j)).getEffectArea();
						r1*=r1;
						r2*=r2;
						if(x3+y3<r2)
						{
							//decrees mech health and bounce human
							((Mech)mechs.elementAt(i)).isAttacked();
							((Human)humans.elementAt(j)).collisionBounce();
						}
						if(x3 + y3 < r1&&hasShot!=1&&((Human)humans.elementAt(j)).getColor()!=Color.GRAY) //collide, else seek/flee
						{	
							//collide
							//laser, pew pew?
							hasShot=1;
							//System.out.println("xt: "+((Human)humans.elementAt(j)).getX()+" | yt: "+((Human)humans.elementAt(j)).getY());
							((Mech)mechs.elementAt(i)).fireLaser(((Human)humans.elementAt(j)).getXLocation(),((Human)humans.elementAt(j)).getYLocation());
							if(((Human)humans.elementAt(j)).getColor()==Color.ORANGE){
								survivorCount--;
							}
							else{
								millitaryCount--;
							}
							humans.remove(j);
							//humanCount--;
						}
						else //seek
						{
							if (seekBox.getState()==true)//seek mode on
							{
								//seek
								((Mech)mechs.elementAt(i)).improvedSeek((Human)humans.elementAt(j),x3+y3);
							}
						}							
					}//sigth if
				}//end of if not gray
			}//end of all humans
			for(j=i+1;j<mechs.size();j++) //for all mechz
			{
					x2=((Mech)mechs.elementAt(j)).getXLocation();
					y2=((Mech)mechs.elementAt(j)).getYLocation();
					r2=((Mech)mechs.elementAt(j)).getSight();
					
					x3 = x1-x2;
					y3 = y1-y2;
					if (r1>r2){
						radius = r1;
					}
					else{
						radius = r2;
					}
					radius *= radius; //square
					x3 *= x3;
					y3 *= y3;
					
					if(x3 + y3 < radius) //one can see the other
					{
						//now to see if those two touch
						r1=((Mech)mechs.elementAt(i)).getEffectArea();
						r2=((Mech)mechs.elementAt(j)).getEffectArea();
						r1*=r1;
						r2*=r2;
						if(x3+y3<r2)
						{
							//decrees mech health and bounce human
							((Mech)mechs.elementAt(j)).fireLaser(((Mech)mechs.elementAt(i)).getXLocation(),((Mech)mechs.elementAt(i)).getYLocation());
							((Mech)mechs.elementAt(i)).isAttacked();
							((Mech)mechs.elementAt(j)).collisionBounce();
						}
						if(x3 + y3 < r1&&hasShot!=1) //collide, else seek/flee
						{	
							//collide
							//laser, pew pew?
							hasShot=1;
							//System.out.println("xt: "+((Human)humans.elementAt(j)).getX()+" | yt: "+((Human)humans.elementAt(j)).getY());
							((Mech)mechs.elementAt(i)).fireLaser(((Mech)mechs.elementAt(j)).getXLocation(),((Mech)mechs.elementAt(j)).getYLocation());
							((Mech)mechs.elementAt(i)).collisionBounce();
							//humanCount--;
						}
										
					}//sigth if
			}//end of all mechs
			
		}//end of for all mechs
			
	}
	
	public void seekOrFlee(int h, int z, double distance) //h is human, z is zombie
	{
		Color c1=((Human)humans.elementAt(h)).getColor();
		double var1;
		var1=((Human)humans.elementAt(h)).getSight();
		var1*=var1;
		//c2=((Particle)particles.elementAt(p2)).getColor();
		//human zomb
		//mill zomb
		if(distance<=var1)
		{
			if (c1==Color.BLUE)
			{	//zombie vs blue, zombie seeks always, blue seeks if armed
				if(((Human)humans.elementAt(h)).lifeStatus()!=1)
				{
					((Human)humans.elementAt(h)).improvedSeek((Zombie)zombies.elementAt(z));	
				}
			}
			else if(c1==Color.ORANGE){
				((Human)humans.elementAt(h)).improvedFlee((Zombie)zombies.elementAt(z));
			}
		}//done checking if millitary and in sight
		var1=((Zombie)zombies.elementAt(z)).getSight();
		var1*=var1;
		if(distance<=var1)
		{
			((Zombie)zombies.elementAt(z)).improvedSeek((Human)humans.elementAt(h));
		}				
	}
	
	/*
	public void collideParticles(int p1, int p2)
	{
		Color c1, c2;
		c1=((Particle)particles.elementAt(p1)).getColor();
		c2=((Particle)particles.elementAt(p2)).getColor();
		//System.out.println("Collision");
		//collision();
		if(c1==Color.GREEN&&c2==Color.ORANGE)
		{
			//zombie vs human, zombie wins
			//c1 wins
			((Particle)particles.elementAt(p2)).bitByZombie();
			//((Particle)particles.elementAt(p2)).collisionBounce();
			((Particle)particles.elementAt(p1)).increaseKillCount();
			//((Particle)particles.elementAt(p1)).collisionBounce();
			survivorCount--;
			zombieCount++;
		}
		else if(c2==Color.GREEN&&c1==Color.ORANGE)
		{
			((Particle)particles.elementAt(p1)).bitByZombie();
			//((Particle)particles.elementAt(p1)).collisionBounce();
			((Particle)particles.elementAt(p2)).increaseKillCount();
			//((Particle)particles.elementAt(p2)).collisionBounce();
			survivorCount--;
			zombieCount++;
			//zombie vs human, zombie wins
			//c2 wins
		}
		else if(c1==Color.BLUE&&c2==Color.GREEN)
		{
			//zombie vs millitary
			//c1 wins
			if(((Particle)particles.elementAt(p1)).lifeStatus()==1)//about to die
			{
				((Particle)particles.elementAt(p1)).bitByZombie();
				((Particle)particles.elementAt(p2)).increaseKillCount();
				//particles.remove(i);
				millitaryCount--;
				zombieCount++;
			}
			else
			{
				((Particle)particles.elementAt(p1)).decreaseAmmunitions();
				particles.remove(p2);//kill the zombie
				zombieCount--;
			}

		}
		else if(c2==Color.BLUE&&c1==Color.GREEN)
		{
			//zombie vs millitary, millitary wins
			//c2 wins
			if(((Particle)particles.elementAt(p2)).lifeStatus()==1)//about to die
			{
				((Particle)particles.elementAt(p2)).bitByZombie();
				((Particle)particles.elementAt(p1)).increaseKillCount();
				//particles.remove(j);
				millitaryCount--;
				zombieCount++;
			}
			else
			{
				((Particle)particles.elementAt(p2)).decreaseAmmunitions();
				particles.remove(p1);//kill the zombie
				zombieCount--;
			}
		}
		else if(c1==Color.ORANGE&&c2==Color.ORANGE)
		{
			if(survivorCount+zombieCount<10000) //it gets crazy and the computer slows down after this
			{
				((Particle)particles.elementAt(p2)).collisionBounce();
				((Particle)particles.elementAt(p1)).collisionBounce();
				((Particle)particles.elementAt(p2)).decreaseBreedCount();
				((Particle)particles.elementAt(p1)).decreaseBreedCount();
				try{
					particles.addElement(new Particle(drawingPanel, Color.ORANGE,Integer.parseInt(diamInput.getText())));
				}
				catch(NumberFormatException ex)
				{
					System.out.println(ex.getMessage());
				}
				survivorCount++;								
			}
			
			//human on human, new human
			//new human
		}
	}*/
	
	
	
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == leftInput)
		{
			// put code to handle left input here
			System.out.println("Left Input Entered");
		}
		else if (ev.getSource() == rightInput)
		{
			// put code to handle right input here
			System.out.println("Right Input Entered");
		}
	}
	
	
	public void run()
	{
		try
		{
			int tickCount;
			while(true)
			{
				tickCount=0;
				while (running)
				{	
				
					checkCollisions();
					int status;
				    // Most of your logic for running the program will probably go in here	
				    int i;
					for(i=0;i<humans.size();i++){
						status=((Human)humans.elementAt(i)).lifeStatus();
						/*if(status==1){
							if(((Particle)particles.elementAt(i)).getColor()==Color.BLUE)
							//if((((Particle)particles.elementAt(i)).getColor()==Color.ORANGE)||
							//	(((Particle)particles.elementAt(i)).getColor()==Color.BLUE))
							{
								millitaryCount--;
							}
							else if (((Particle)particles.elementAt(i)).getColor()==Color.GREEN)
							{
								zombieCount--;
							}
							particles.remove(i);
						}*/
						if (status == 2)//upgraded from human to millitary
						{
							survivorCount--;
							millitaryCount++;
							((Human)humans.elementAt(i)).moveInSpace();
						}
						else if (status == 3) //gray ready to rise
						{
							try{
								zombies.addElement(new Zombie(drawingPanel,Integer.parseInt(diamInput.getText()), lines,
									((Human)humans.elementAt(i)).getXLocation(), ((Human)humans.elementAt(i)).getYLocation()));
								humans.remove(i);
							}
			        		catch(NumberFormatException ex)
							{
								System.out.println(ex.getMessage());
							}
						}
						else if (status == 4)
						{
							try{
								mechs.addElement(new Mech(drawingPanel,Integer.parseInt(diamInput.getText()), lines,
									((Human)humans.elementAt(i)).getXLocation(), ((Human)humans.elementAt(i)).getYLocation()));
								humans.remove(i);
								survivorCount--;
							}
			        		catch(NumberFormatException ex)
							{
								System.out.println(ex.getMessage());
							}
						}
						else
						{
							((Human)humans.elementAt(i)).moveInSpace();
						}	
					}
					if(lifeBox.getState()==true){
						for(i=0;i<zombies.size();i++){
							if(((Zombie)zombies.elementAt(i)).lifeStatus()==1)//die of hunger, cause zombies need brains
							{
								zombies.remove(i);
							}
							else
							{
								((Zombie)zombies.elementAt(i)).moveInSpace();
							}
						}
					}
					else{
						for(i=0;i<zombies.size();i++){
							((Zombie)zombies.elementAt(i)).moveInSpace();
							}
					}
					for(i=0;i<mechs.size();i++){
						if(((Mech)mechs.elementAt(i)).getStatus()==1){
							//explode
							mechs.remove(i);
						}
						else{
							((Mech)mechs.elementAt(i)).moveInSpace();
						}
					}

										
					System.out.println(
						"Tick: " + tickCount +
						" | Humans: "+survivorCount+
						" | Zombies: "+zombies.size()+
						" | Millitary: "+millitaryCount+
						" | Total: "+(survivorCount+zombies.size()+millitaryCount));
					tickCount++;
					drawingPanel.repaint();
					Thread.sleep(20);
				}
				
				//System.out.println("inside run endless loop. running is " + running);
			}
		}
		catch(InterruptedException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		GraphicsEngine engine = new GraphicsEngine( );
		engine.run();
	}
}