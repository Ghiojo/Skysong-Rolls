package com.skysongdev.skysongrolls;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class RollParser {

    public static int rollDice(int input){
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(input)+1;
    }

    public static ArrayList<Integer> diceResult(String input){
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        if(input.matches("^[0-9]+d[0-9]+$")){
            String[] aux = input.split("d");
            int numRolls = Integer.valueOf(aux[0]);
            int rollSize = Integer.valueOf(aux[1]) - 1;
            int returnValue = 0;
            int rollAux;


            if(numRolls <= 0 || numRolls <= 0){
                returnList.add(-2);
                return returnList;
            }
            Random randomizer = new Random();
            for(int i = 0; i < numRolls; i++){
                rollAux = rollDice(rollSize);
                returnValue += rollAux;
                returnList.add(rollAux);
            }
            returnList.add(0, returnValue);
            return returnList;
        }
        else {
            returnList.add(-1);
            return returnList;
        }
    }

    public static boolean checkValidNumerical(String input){
        return !input.matches("^[0-9]+d[0-9]+$") && !input.matches("^[0-9]+$") && !input.matches("(dex)|(str)|(con)|(foc)");
    }

    public static boolean checkValidOperator(String input){
        return !input.matches("[+-]");
    }
}

