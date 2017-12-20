package danielComponent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import guiPlayer.Book;

public class CatalogMaker 
{
	private ArrayList<Movie> movies;
	
	public static Scanner in;
	public static boolean inConvo;
	
	public static int count;
	
	public CatalogMaker()
	{
		movies = new ArrayList<Movie>();
		
		movies.add(new Movie("The Terminator", "James Cameron", 1984));
		movies.add(new Movie("The Titanic", "James Cameron", 1997));
		
	}
	
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		CatalogMaker maker = new CatalogMaker();
		System.out.println("Do you want to load first?");
		String reply =in.nextLine();
		if (reply.equals("yes"))
		{
			maker.load();
		}
		else
		maker.add();		
		
		System.out.println("Do you want to save?");
		reply = in.nextLine();
		if (reply.equals("yes"))
		{
			maker.save();
		}
		
		
	}
	
	public void add()
	{
		Scanner in = new Scanner(System.in);
		CatalogMaker maker = new CatalogMaker();
		while (!inConvo)
		{			
			System.out.println("Enter a movie title.");
			String title = in.nextLine();
			if (!(title.equals("")))
			{
				System.out.println("Enter a director name.");
				String director = in.nextLine();
				if (!(director.equals("")))
				{
					System.out.println("Enter a year of release.");
					String date = in.nextLine();
					
					while (!isNumeric(date))
					{
						System.out.println("Enter a valid year.");	
						date = in.nextLine();
					}
					int year = Integer.parseInt(date);
					maker.movies.add(new Movie(title, director, year));
					System.out.println("Your movie has been created.");
					inConvo = true;
				}				
			}						
		}
		System.out.println(maker.getCsvContent());	
	}

	public String getCsvContent()
	{
		String data = "";
		
		for (Movie a : movies)
		{
			data += a + "\n";
		} 
		return data;
	}
	
	private void save() {
		try{    
			FileWriter fw=new FileWriter("MovieCatalog.csv");
			for(Movie b: movies){
				fw.write(b+"\n");    	
			}

			fw.close();    
			System.out.println("Success! File \"MovieCatalog.csv\" saved!");
		}catch(IOException e){
			System.out.println("An IOException was thrown. \nCheck to see that the directory where you tried to save the file actually exists.");
		}
	}
	
	private void load() {
		Scanner in = new Scanner(System.in);
		String fileName = "";
		//empty the catalog to prepare for a new load
		movies = new ArrayList<Movie>();
		//use this boolean to control the while loop. The user should have multiple chances to enter a correct filename
		boolean opened = false;
		while(!opened){
			try {
				System.out.println("Enter a file to open");
				fileName = in.nextLine();
				FileReader fileReader = new FileReader(new File(fileName));
				String line = "";
				//a BufferedReader enables us to read teh file one line at a time
				BufferedReader br = new BufferedReader(fileReader);
				while ((line = br.readLine()) != null) {

					String[] param = line.split(",");
					//add a new Book for each line in the save file
					movies.add(new Movie(param[0],param[1],Integer.parseInt(param[2])));



				}
				br.close();
				opened = true;
			}catch (IOException e) {
				System.out.println("The file name you specified does not exist.");
			}
		}
	}
	

	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c))
	        {
	        	return false;
	        }
	    }
	    return true;
	}
}
