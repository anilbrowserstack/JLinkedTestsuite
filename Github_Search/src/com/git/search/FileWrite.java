package com.git.search;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileWrite {
	static void fileWrite(String name, String where) {
		String filename = "";
		if(where.equals("name")) 
			filename = "github_users.txt";
		else if(where.equals("link"))
			filename = "github_userlinks.txt";
		
		try  
		{
		    FileWriter fstream = new FileWriter(filename, true);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(name+"\n");
		    out.close();
		}
		catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
		
	}
}
