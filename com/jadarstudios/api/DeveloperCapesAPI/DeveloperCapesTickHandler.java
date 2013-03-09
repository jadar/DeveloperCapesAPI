/**
 * Copyright (c) Jadar, 2013
 * Developer Capes API by Jadar
 * 
 * version 1.1
 */
package com.jadarstudios.api.DeveloperCapesAPI;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DeveloperCapesTickHandler implements ITickHandler {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final DeveloperCapesAPI instance = DeveloperCapesAPI.getInstance();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// will not run if there is no world, and if there are player entities in the playerEntities list.
		if ((mc.theWorld != null) && (mc.theWorld.playerEntities.size() > 0)) {

			// grabs a list of all the players, and the world.
			@SuppressWarnings("unchecked")
			List<EntityPlayer> players = mc.theWorld.playerEntities;

			// the loops that goes through each player
			for (int counter = 0; counter < players.size(); counter++) {

				// helps keep from getting an ArrayOutOfBoundException
				if (players.get(counter) != null) {

					// get the player from the players list.
					EntityPlayer player = players.get(counter);
					String oldCloak = player.playerCloakUrl;
					
					if(player.playerCloakUrl.startsWith("http://skins.minecraft.net/MinecraftCloaks/") || player.playerCloakUrl.startsWith("https://dl.dropbox.com/")) {
					
						String lowerUsername = player.username.toLowerCase();
						
						for(String s : instance.testerUsers) {
							if(s.equals(lowerUsername)) {
								// set the cloak url of the player
								player.cloakUrl = (player.playerCloakUrl = instance.testerCape);
							}
						}

						for(String s : instance.devUsers) {
							if(s.equals(lowerUsername)) {

								// set the cloak url of the player
								player.cloakUrl = (player.playerCloakUrl = instance.devCape);
							}
						}

						// if the set cloak does not equal the old cloak then download the cloak.
						if ( player.playerCloakUrl != oldCloak & player.cloakUrl != oldCloak) {
							// download the cloak. the second arguement is an image buffer that makes sure the cape is the right dimensions.
							System.out.println(player.playerCloakUrl);
							mc.renderEngine.obtainImageData(player.playerCloakUrl, new ImageBufferDownload());
						}
					}
				}
			}
		}
	}

	/*
	 * Not used, stub method.
	 */
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "DeveloperCapesTickHandler";
	}

}
