package server.controller;
import server.*;
import server.model.RegistrationApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {

	private ServerSocket s;
	private Socket theSocket;
	private BufferedReader socketIn;
	private RegistrationApp theLogic;
	
	
	public ServerCommunication(int port)
	{
		try
		{
			s = new ServerSocket(port);
			theSocket = s.accept();
			socketIn = new BufferedReader (new InputStreamReader(theSocket.getInputStream()));
			theLogic = new RegistrationApp(theSocket);
		}catch(IOException e)
		{
			System.err.println("Error");
		}
	}
	
	private void communication() {
		
		String line = null;
		
		while(true)
		{
			try
			{
				line = socketIn.readLine();
				
				String[] word = line.split(",");
				
				switch(Integer.parseInt(word[word.length-1]))
				{
					case 1:
						int courseNum = Integer.parseInt(word[1]);
						theLogic.searchCatalogueCourses(word[0], courseNum);
						break;
						
					case 2:
						int num = Integer.parseInt(word[2]);
						int section = Integer.parseInt(word[3]);
						theLogic.addStudentCourses(word[0],word[1], num, section);
						break;
						
					case 3:
						int choice = Integer.parseInt(word[1]);
						theLogic.removeStudentCourses(word[0], choice);
						break;
						
					case 4:
						
						theLogic.viewAllCourses();
						break;
						
					case 5:
						
						theLogic.viewAllStudentCourses(word[0]);
						break;
					
				}
			} catch (IOException e)
			{
				System.err.println("ERROR!");
			}
		}
		
	}
	
	public static void main(String[] arg) throws IOException
	{
		ServerCommunication theServer = new ServerCommunication(8099);
		theServer.communication();
		
		theServer.socketIn.close();
	}


} 
