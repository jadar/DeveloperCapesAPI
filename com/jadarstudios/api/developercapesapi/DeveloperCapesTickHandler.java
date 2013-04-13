/**
 * Copyright (c) Jadar, 2013
 * Developer Capes API by Jadar
 * License: Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * version 1.3.1
 */
package com.jadarstudios.api.developercapesapi;

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
        /*
         * Will not run if there is no world, and if there are player entities
         * in the playerEntities list.
         */
        if ((mc.theWorld != null) && (mc.theWorld.playerEntities.size() > 0)){

            // Grabs a list of all the players, and the world.
            @SuppressWarnings("unchecked")
            List<EntityPlayer> players = mc.theWorld.playerEntities;

            // The loops that goes through each player
            for (int counter = 0; counter < players.size(); counter++){

                // Helps keep from getting an ArrayOutOfBoundException
                if (players.get(counter) != null){

                    // Get the player from the players list.
                    EntityPlayer player = players.get(counter);

                    if (player.cloakUrl.startsWith("http://skins.minecraft.net/MinecraftCloaks/")){
                        // Lowercase username, so no problems with case.
                        String lowerUsername = player.username.toLowerCase();

                        if (instance.getUser(lowerUsername) != null){
                            String oldCloak = player.cloakUrl;

                            /*
                             * Get the user from the hash map and get the cape
                             * URL.
                             */
                            DeveloperCapesUser hashUser = instance.getUser(lowerUsername);
                            String groupUrl = instance.getGroupUrl(hashUser.getGroup());

                            // Sets the cape URL.
                            player.cloakUrl = groupUrl;

                            /*
                             * If the set cloak does not equal the old cloak
                             * then download the cloak.
                             */
                            if (player.cloakUrl != oldCloak){
                                /*
                                 * Downloads the cloak. The second argument is
                                 * an image buffer that makes sure the cape is
                                 * the right dimensions.
                                 */
                                mc.renderEngine.obtainImageData(player.cloakUrl,
                                        new ImageBufferDownload());
                            }
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
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return "DeveloperCapesTickHandler";
    }

}
