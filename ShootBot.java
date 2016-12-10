import java.io.*;
import java.util.*;

class GameHelper {

  private static final String alphabet = "abcdefg";
  private int gridLength = 7;
  private int gridSize = 49;
  private int [] grid = new int[gridSize];
  private int comCount = 0;


  public String getUserInput(String prompt) {
     String inputLine = null;
     System.out.print(prompt + "  ");
     try {
       BufferedReader is = new BufferedReader(
	 new InputStreamReader(System.in));
       inputLine = is.readLine();
       if (inputLine.length() == 0 )  return null; 
     } catch (IOException e) {
       System.out.println("IOException: " + e);
     }
     return inputLine.toLowerCase();
  }

  
  
 public ArrayList<String> placeDotCom(int comSize) {  
    ArrayList<String> alphaCells = new ArrayList<String>();
    String [] alphacoords = new String [comSize];      
    String temp = null;                                
    int [] coords = new int[comSize];                  
    int attempts = 0;                                  
    boolean success = false;                           
    int location = 0;                                  
    
    comCount++;                                        
    int incr = 1;                                      
    if ((comCount % 2) == 1) {                         
      incr = gridLength;                               
    }

    while ( !success & attempts++ < 200 ) {            
	location = (int) (Math.random() * gridSize);      
        //System.out.print(" try " + location);
	int x = 0;                                        
        success = true;                               
        while (success && x < comSize) {              
          if (grid[location] == 0) {                  
             coords[x++] = location;                  
             location += incr;                        
             if (location >= gridSize){               
               success = false;                       
             }
             if (x>0 & (location % gridLength == 0)) {
               success = false;                       
             }
          } else {                                    
              success = false;                        
          }
        }
    }                                                 

    int x = 0;                                        
    int row = 0;
    int column = 0;
    // System.out.println("\n");
    while (x < comSize) {
      grid[coords[x]] = 1;                            
      row = (int) (coords[x] / gridLength);           
      column = coords[x] % gridLength;                
      temp = String.valueOf(alphabet.charAt(column));      
      alphaCells.add(temp.concat(Integer.toString(row)));
      x++;  
    }
    return alphaCells;
   }
}
class ShootBot
{
	private GameHelper helper = new GameHelper();
	private ArrayList<DotCom> objectlist = new ArrayList<DotCom>();
	private int numofguess=0;
	private void setupgame()
	{
		DotCom a= new DotCom();
		a.setname("Enthiran");
		DotCom b=new DotCom();
		b.setname("RaOne");
		DotCom c=new DotCom();
		c.setname("Terminator");
		objectlist.add(a);
		objectlist.add(b);
		objectlist.add(c);
		System.out.println("Your goal is to kill 3 bots...");
		System.out.println("Try to sink them all in minimum guess");
		for(DotCom temp:objectlist)
		{
			ArrayList<String> newlocation = helper.placeDotCom(3);
			temp.setlocationcells(newlocation);
		}
	}
	private void startplay()
	{
		while(!objectlist.isEmpty())
		{
			String guess=helper.getUserInput("enter the number...");
			checkguess(guess);
		}
		finishgame();
	}
	
	private void checkguess(String userguess)
	{
		numofguess++;
		String result="Miss";
		for(DotCom temp:objectlist)
		{
			result=temp.checkyourself(userguess);
			if(result.equals("Hit"))
			{
			break;
			}
			if(result.equals("Kill"))
			{
				objectlist.remove(temp);
				break;
			}
		}
		System.out.println(result);
	}
	private void finishgame()
	{
		System.out.println("The game got over now all the bots are dead");
		System.out.println("you took " +numofguess+ " guesses in total");
		if(numofguess<=20)
		{
			System.out.println("Congrats!!You killed everything with less guesses");
		}
		else
		{
			System.out.println("Better luck next time!");
		}
	}
	public static void main(String[] args)
	{
	ShootBot game=new ShootBot();
	game.setupgame();
	game.startplay();
	}
}
class DotCom
{
	private ArrayList<String> locationcell;
	private String name;
	public void setlocationcells(ArrayList<String> loc)
	{
		locationcell=loc;
	}
	public void setname(String n)
	{
		name=n;
	}
	public String checkyourself(String input)
	{
		String result="Miss";
		int index=locationcell.indexOf(input);
		if(index>=0)
		{
			locationcell.remove(input);
		if(locationcell.isEmpty())
		{
			result="Kill";
			System.out.println("oops you killed " +name+ " :(");
		}
		else
		{
			result="Hit";
		}
		}
		return result;
	}
}	
		