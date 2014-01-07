package com.jadarstudios.developercapes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.util.ResourceLocation;

public class DevCapes
{

	/**
	 * Adds a cape for just a single player
	 * @param username: The username of the player. 
	 * @param url: The url of the cape image.
	 */
	public static void addPersonalCape(String username, String url)
	{
		
		ThreadDownloadImageData object = new ThreadDownloadImageData(url, null, null);
		Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (TextureObject) object);
	}
	
	/**
	 * Adds cape for a group of players from an array.
	 * @param group: Array of users to give the cape to.
	 * @param url: The url of the cape image.
	 */
	public static void addGroupedCape(String[] group, String url)
	{
		
		for (String username : group) 
		{
			
			ThreadDownloadImageData object = new ThreadDownloadImageData(url, null, null);
			Minecraft.getMinecraft().renderEngine.loadTexture(new ResourceLocation("cloaks/" + username), (TextureObject) object);
		}
	}
	
	/**
	 * Grabs a string array from an online source. 
	 * Each line of file is a new username.
	 * @param url: Link to the text file.
	 * @return: An array from a text file. If link is invalid returns a blank array.
	 */
	public static String[] getArrayFromUrl(String url)
	{
		
		ArrayList<String> list = new ArrayList<String>();
		
		try
		{
			
			BufferedReader urlReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			
			String line;
			
			while((line = urlReader.readLine()) != null)
			{
				
				list.add(line);
			}
			
			return (String[]) list.toArray(new String[list.size()]);
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			list.add("");
			return (String[]) list.toArray(new String[list.size()]);
		}
	}
}