package com.skysongdev.skysongrolls.command;

import com.skysongdev.skysongrolls.Main;
import com.skysongdev.skysongrolls.RollParser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Roll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Step 1: Check if the command has enough arguments (1)
        if(strings.length > 1){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Too many arguments! You don't need to put spaces");
            return true;
        }
        if(strings.length == 0){
            commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Too little Arguments! Correct usage: /roll <attributes>");
            return true;
        }

        //Step 2: We split the string by the operators *without destroying the operator signs*
        String args = strings[0];
        args = args.replace("+", "ç+ç");
        args = args.replace("-", "ç-ç");

        String [] splitArgs = args.split("ç");
        ArrayList<String> numArgs = new ArrayList<String>();
        ArrayList<String> opArgs = new ArrayList<String>();

        //Step 3: The Function separates the input String into 2 different ArrayLists, one for Numbers/dice,
        //and one for the operators. The order is always Number - Operator - Number, so it's a simple odd/even
        //separation.
        for(int i = 0; i < splitArgs.length; i++){
            if(i % 2 == 0){
                numArgs.add(splitArgs[i]);
            }
            else{
                opArgs.add(splitArgs[i]);
            }
        }

        //Step 4: We make sure that all of the numbers and dice are valid. This also incldes
        //making sure the dice are not 0. Such as 0d2 or 2d0
        for(int i = 0; i < numArgs.size(); i++){
            if(RollParser.checkValidNumerical(numArgs.get(i))){
                commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Argument " + i + " (" + numArgs.get(i) + ") is invalid! (Correct form: <number>d<number>)");
                return true;
            }
            if(numArgs.get(i).matches("^[0-9]+d[0-9]+$"))
                if(Objects.equals(numArgs.get(i).split("d")[0], "0") || Objects.equals(numArgs.get(i).split("d")[1], "0")){
                    commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Argument " + i + " (" + numArgs.get(i) + ") is invalid! (Correct form: <number>d<number>)");
                    return true;
                }
        }

        //Step 5: We make sure all operators are either a + or a -. These are the only two valid ones at the moment.
        for(int i = 0; i < opArgs.size(); i++){
            if(RollParser.checkValidOperator(opArgs.get(i))){
                commandSender.sendMessage(ChatColor.of("RED").toString() + "[ERROR] Operator " + i + "(" + opArgs.get(i) + ") is invalid! (Correct form: <number>d<number>)");
                return true;
            }
        }

        //Step 6: We begin constructing the string that will be sent to the OpenRP chat.
        String returnString = "has rolled:";
        ArrayList<Integer> aux = new ArrayList<Integer>();
        int finalResult = 0;
        //We first find the first result of the first dice roll, if it's a static number
        //for whatever ungodly reason, it shouldn't break or send an error code.
        if(numArgs.get(0).matches("^[0-9]+d[0-9]+$")){
            aux = RollParser.diceResult(numArgs.get(0));
        }
        else{
            aux.add(Integer.parseInt(numArgs.get(0)));
        }
        finalResult += aux.get(0);
        if(aux.size() > 10){
            int intaux = aux.get(0);
            aux.clear();
            aux.add(intaux);
        } else if(aux.size() > 1)
            aux.remove(0);
        returnString = returnString.concat(" "+ ChatColor.of("WHITE").toString() + numArgs.get(0) + ChatColor.of("GOLD").toString() + aux + ChatColor.of("DARK_AQUA").toString() + " ");
        //We enter a loop to do every roll, we always match i (which starts at 1) with i-1 on the
        //opArgs side, with the idea of knowing wether the result of a roll (or static number) is added or
        //substracted to the final result.
        for (int i = 1; i < numArgs.size(); i++){
            if(numArgs.get(i).matches("^[0-9]+d[0-9]+$")){
                aux = RollParser.diceResult(numArgs.get(i));
            }
            else{
                aux.clear();
                aux.add(Integer.parseInt(numArgs.get(i)));
            }
            if(Objects.equals(opArgs.get(i - 1), "+")){
                returnString = returnString.concat(ChatColor.of("WHITE").toString() + "+");
                finalResult += aux.get(0);
            }
            if(Objects.equals(opArgs.get(i - 1), "-")){
                returnString = returnString.concat(ChatColor.of("WHITE").toString() + "-");
                finalResult -= aux.get(0);
            }
            if(aux.size() > 10){
                int intaux = aux.get(0);
                aux.clear();
                aux.add(intaux);
            }
            else if(aux.size() > 1)
                aux.remove(0);

            returnString = returnString.concat(" "+ ChatColor.of("WHITE").toString() + numArgs.get(i) + ChatColor.of("GOLD").toString() + aux + ChatColor.of("DARK_AQUA").toString() + " ");
        }

        //After constructing all the operations on the return string, we add the result of it all
        //at the end of the return string
        returnString = returnString.concat(" = " + ChatColor.of("GOLD").toString() + finalResult);


        //Step 7: We send the string to the OpenRP chat *and make sure it's logged*, this is so
        //moderation can easily tell what someone has rolled.
        Main.getOpenRP().getChat().sendMessage((Player)commandSender, returnString, "rolling");
        Bukkit.getLogger().info(commandSender.getName() + " " + ChatColor.stripColor(returnString));
        return true;
    }
}
