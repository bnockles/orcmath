package danielComponent;

import java.util.ArrayList;
import java.util.Scanner;

public class CatalogMaker 
{
	private ArrayList<Movie> movies;
	
	public static Scanner in;
	public static boolean inConvo;
	
	public static int count;
	
	public CatalogMaker()
	{
		movies = new ArrayList<Movie>();
		
		count = 0;
		movies.add(count, new Movie("The Terminator", "James Cameron", 1984));
		count++;
		movies.add(count, new Movie("The Titanic", "James Cameron", 1997));
		
	}
	
	public static void main(String[] args) 
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
					count++;
					maker.movies.add(count, new Movie(title, director, year));
					System.out.println("Your movie has been created.");
					inConvo = true;
				}				
			}
			
			System.out.println("Do you want to save your changes?");
			
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
