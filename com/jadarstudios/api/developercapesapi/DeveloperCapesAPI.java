/**
 * Copyright (c) Jadar, 2013
 * Developer Capes API by Jadar
 * License: Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * version 1.3.1
 */
package com.jadarstudios.api.developercapesapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class DeveloperCapesAPI {

    private static DeveloperCapesAPI instance;

    private HashMap<String, DeveloperCapesUser> users;
    private HashMap<String, String> groupUrls;

    private boolean tickSetUp = false;

    /**
     * Object constructor.
     */
    private DeveloperCapesAPI() {
        users = new HashMap<String, DeveloperCapesUser>();
        groupUrls = new HashMap<String, String>();
    }

    /**
     * Gets the current {@link DeveloperCapesAPI}'s instance.
     * 
     * @return Either a new instance or the instance being used.
     */
    public static DeveloperCapesAPI getInstance() {
        if (instance == null){
            instance = new DeveloperCapesAPI();
        }
        return instance;
    }

    /**
     * Sets up capes. All cape URLs are in the TXT file passed in.
     * https://github.com/jadar/DeveloperCapesAPI/blob/master/SampleCape.txt
     * 
     * @param parTxtUrl
     *            The URL that contains the .txt file with the User groups and
     *            their capes URL.
     */
    public void init(String parTxtUrl) {
        try{
            URL url = new URL(parTxtUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            String username = "";
            String group = "";
            String capeUrl = "";

            while ((line = reader.readLine()) != null){

                // Excludes commented lines
                if (!line.startsWith("#")){
                    // Loops through characters.
                    for (int i = 0; i < line.length(); i++){
                        // When char '=' is found do stuff.
                        if (line.charAt(i) == '='){
                            group = line.substring(0, i);
                            String subLine = line.substring(i + 1);

                            if (subLine.startsWith("http")){
                                capeUrl = subLine;
                                getInstance().addGroupUrl(group, capeUrl);
                                continue;
                            }else{
                                username = subLine.toLowerCase();
                                getInstance().addUser(username, group);
                            }
                        }
                    }
                }
            }
        }catch(IOException x){
            x.printStackTrace();
        }

        // Make sure to set up only one tick handler.
        if (!tickSetUp){
            // Sets up tick handler for capes.
            TickRegistry.registerTickHandler(new DeveloperCapesTickHandler(), Side.CLIENT);
            tickSetUp = true;
        }
    }

    /**
     * Used to add user to users HashMap.
     * 
     * @param parUsername
     *            The User name to add to the group.
     * @param parGroup
     *            the group in witch the User name should be on.
     */
    public void addUser(String parUsername, String parGroup) {
        if (getUser(parUsername) == null){
            users.put(parUsername, (new DeveloperCapesUser(parUsername, parGroup)));

        }
    }

    /**
     * Used to get user from users HashMap.
     * 
     * @param parUsername
     *            The User to find in the HashMap.
     * @return The User name found in the HashMap.
     */
    public DeveloperCapesUser getUser(String parUsername) {
        return users.get(parUsername.toLowerCase());
    }

    /**
     * Used to add group and URL to groupUrls HashMap.
     * 
     * @param parGroup
     *            The group to add to the HashMap.
     * @param parCapeUrl
     *            The cape URL to add to the HashMap.
     */
    public void addGroupUrl(String parGroup, String parCapeUrl) {
        if (getGroupUrl(parGroup) == null){
            groupUrls.put(parGroup, parCapeUrl);
        }
    }

    /**
     * Used to get URL from groupUrl by the group name.
     * 
     * @param group
     *            The group name to get the cape URL from.
     * @return The cape URL.
     */
    public String getGroupUrl(String group) {
        return groupUrls.get(group);
    }
}
