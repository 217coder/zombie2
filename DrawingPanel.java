import java.awt.*;
import java.util.*;

public class DrawingPanel extends Panel
{
	Vector humans, zombies, mechs, lines;
	Vector<Laser> graph;
	int prevH, prevZ;
    private int bufferWidth;
    private int bufferHeight;
    private Image bufferImage;
    private Graphics bufferGraphics;
    
	DrawingPanel()
	{
		setBackground(Color.BLACK);
		graph = new Vector<Laser>();
		prevH = 0;
		prevZ = 0;
	}


    private void resetBuffer(){
        // always keep track of the image size
        bufferWidth=getSize().width;
        bufferHeight=getSize().height;

        //    clean up the previous image
        if(bufferGraphics!=null){
            bufferGraphics.dispose();
            bufferGraphics=null;
        }
        if(bufferImage!=null){
            bufferImage.flush();
            bufferImage=null;
        }
        System.gc();

        //    create the new image with the size of the panel
        bufferImage=createImage(bufferWidth,bufferHeight);
        bufferGraphics=bufferImage.getGraphics();
    }
	
	
	void setHumans(Vector h)
	{
		humans = h;
	}
	
	void setZombies(Vector z)
	{
		zombies = z;
	}
	void setMechs(Vector m)
	{
		mechs = m;
	}
	void setLines(Vector l)
	{
		lines = l;
	}
	void graphStats()
	{
		if(graph!=null){
			graph.add(new Laser(getWidth()-1, getHeight()-prevH, getWidth(), getHeight()-humans.size(), Color.ORANGE));
			prevH=humans.size();
			graph.add(new Laser(getWidth()-1, getHeight()-prevZ, getWidth(), getHeight()-zombies.size(), Color.GREEN));
			prevZ=zombies.size();
		}
	}
	
	public void update(Graphics g){
        paint(g);
    }
	
	public void paint(Graphics g)
	{
		
    	if(bufferWidth!=getSize().width || 
    	    	   bufferHeight!=getSize().height || 
    	           bufferImage==null || bufferGraphics==null)
    	    		resetBuffer();
    	if(bufferGraphics!=null){
             //this clears the offscreen image, not the onscreen one
             bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);

             //calls the paintbuffer method with 
             //the offscreen graphics as a param
             paintBuffer(bufferGraphics);

             //we finaly paint the offscreen image onto the onscreen image
             g.drawImage(bufferImage,0,0,this);
         }
     }

     public void paintBuffer(Graphics g){
         //in classes extended from this one, add something to paint here!
         //always remember, g is the offscreen graphics
     
		//g.setColor(Color.RED);
		//g.drawLine(25,25, 200,170); //m = 1.296
		if (humans != null)
		{
			for(int i=0;i<humans.size();i++){
				((Human)humans.elementAt(i)).paint(g);
			}
		}
		if (zombies != null)
		{
			for(int i=0;i<zombies.size();i++){
				((Zombie)zombies.elementAt(i)).paint(g);
			}
		}
		if (mechs != null)
		{
			for (int i=0;i<mechs.size();i++){
				((Mech)mechs.elementAt(i)).paint(g);
			}
		}
		if (lines != null)
		{
			for (int i=0;i<lines.size();i++){
				((Line)lines.elementAt(i)).paint(g);
			}
		}
		/*if(graph!=null){
			graphStats();
			for(int i=0;i<graph.size();i++){
				graph.elementAt(i).paint(g);
			}
		}*/
		//g.drawString(humans.size()+","+zombies.size(), 10, 10);		
	}
}	