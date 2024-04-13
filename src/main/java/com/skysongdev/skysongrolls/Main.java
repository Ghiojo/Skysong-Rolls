package com.skysongdev.skysongrolls;

import com.skysongdev.skysongrolls.command.Flip;
import com.skysongdev.skysongrolls.command.PrivateRoll;
import com.skysongdev.skysongrolls.command.Roll;
import openrp.OpenRP;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;
    private static OpenRP openrp;

    public static OpenRP getOpenRP() {
        return openrp;
    }
    public static Main getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        super.onEnable();
        // Verify plugin loading
        if (getServer().getPluginManager().isPluginEnabled("OpenRP")) {
            openrp = (OpenRP) getServer().getPluginManager().getPlugin("OpenRP");
        } else {
            getLogger().info("OpenRP 2.3.5 is required for the Addon to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("skyroll").setExecutor(new Roll());
        getCommand("flip").setExecutor(new Flip());
        getCommand("proll").setExecutor(new PrivateRoll());
    }

}
