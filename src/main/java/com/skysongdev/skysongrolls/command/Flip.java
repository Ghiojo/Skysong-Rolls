package com.skysongdev.skysongrolls.command;

import com.skysongdev.skysongrolls.Main;
import com.skysongdev.skysongrolls.RollParser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Flip implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        int aux = RollParser.rollDice(2);
        String retStr = "has flipped a coin! It landed on ";
        if(aux == 1){
            retStr = retStr.concat(ChatColor.of("GOLD").toString() + "Heads");

        }
        else{
            retStr = retStr.concat(ChatColor.of("GOLD").toString() + "Tails");
        }
        Main.getOpenRP().getChat().sendMessage((Player)commandSender, retStr, "rolling");
        Bukkit.getLogger().info(commandSender.getName() + " " + ChatColor.stripColor(retStr));
        return true;
    }
}
