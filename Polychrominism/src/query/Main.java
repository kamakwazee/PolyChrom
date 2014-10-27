package query;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class Main {

	private static String[] words;
	private static ArrayList<String> list;
	private static File dictionary;
	private static Scanner querier;
	private static String[] longestWords;
	private static ArrayList<String> allWords;
	private static int amount = 0;
	
	
	public static void main(String[] args) {
		
		dictionary = new File("/home/kamakwazee/Desktop/dictionary.txt");
		try {
			querier = new Scanner(dictionary);
			list = new ArrayList<String>();
			while(querier.hasNextLine())
			{
				list.add(querier.nextLine());
			}
			words = list.toArray(new String[list.size()]);
			querier.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		allWords = new ArrayList<String>();
		longestWords = findLongestWord();
		System.out.println(Integer.toString(amount));
		
		try {
			Formatter write = new Formatter(new File("/home/kamakwazee/Desktop/polychroms.txt"));
			Formatter writeall = new Formatter(new File("/home/kamakwazee/Desktop/allpolychroms.txt"));
			writeall.format(toOutput(allWords.toArray(new String[allWords.size()])));
			write.format(toOutput(longestWords));
			write.close();
			writeall.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static String toOutput(String[] lw)
	{
		
		String out = "";
		for(String w : lw)
			out += w + "%n";
		
		return out;
		
	}
	
	private static String[] findLongestWord()
	{
		ArrayList<String> lw = new ArrayList<String>();
		boolean noWords = true;
		for(String w : words)
		{
			boolean canSplit = queryDictionary(w);
			
			if(canSplit)
			{
				System.out.println(w);
				allWords.add(w);
				amount++;
				if(!noWords)
				{
					int size = lw.toArray(new String[lw.size()])[0].length();
					if(w.length() > size)
					{
						lw.clear();
						lw.add(w);
					}
					else if(w.length() == size)
					{
						lw.add(w);
					}
				}
				else
				{
					lw.add(w);
					noWords = false;
				}
			}
		}
		
		return lw.toArray(new String[lw.size()]);
	}
	
	private static boolean queryDictionary(String w)
	{
		char[] ch = w.toCharArray();
		String[] split = splitString(ch);
		
		if(inDictionary(split[0]) && inDictionary(split[1]))
		{
			return true;
		}
		
		
		return false;
	}
	
	private static boolean inDictionary(String s)
	{
		
		for(String dict : words)
		{
			
			if(dict.equals(s))
				return true;
			
		}
		
		return false;
		
	}
	
	private static String[] splitString(char[] ch)
	{
		
		int sect = 0;
		String s1 = "";
		String s2 = "";
		
		for(char c : ch)
		{
			
			if(sect == 0)
			{
				s1 += c;
				sect = 1;
			}
			else if(sect == 1)
			{
				s2 += c;
				sect = 0;
			}
			
		}
		return new String[]{s1,s2};
		
	}

}
