package net.jadar.devcapesdemo;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.jadarstudios.developercapes.DevCapes;

@Mod(modid = "devcapesdemo", name = "Developer Capes Demo", version = "1.0")
public class DevCapesDemo {

    @Mod.Instance
    public static DevCapesDemo instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                DevCapes.getInstance().registerConfig("https://dl.dropboxusercontent.com/u/22865035/ModHosting/capes/capes.json");
        }
    }
}