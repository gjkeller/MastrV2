/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class MinesweeperCommand extends CommandBase {
    public MinesweeperCommand(Mastr mastr) {
        super(mastr);
    }

    @Override
    public boolean called(String[] args) {
        return true;
    }

    @Override
    public void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args) {
        String minesweeper = generateMinesweeper(9, 9, 10);
        logger.debug(minesweeper);
        channel.sendMessage(minesweeper).queue();
    }

    @Override
    public String getCommand() {
        return "minesweeper";
    }

    @Override
    public String getDescription() {
        return "Gives you a playable game of Minesweeper!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public String[] getSyntax() {
        return new String[] {};
    }

    public static String generateMinesweeper(int rows, int columns, int mines){
        String[][] field = new String[rows][columns];

        // Fill with zeroes
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < field[x].length; y++){
                field[x][y] = ":zero:";
            }
        }

        // Randomly add mines
        for(int i = 0; i < mines; i++){
            int x, y;
            do{
                x = (int)(Math.random()*field.length);
                y = (int)(Math.random()*field[x].length);
            }
            while(field[x][y].equals(":bomb:"));

            field[x][y] = ":bomb:";
        }

        // Fix number values
        for(int x = 0; x < field.length;x++){
            for(int y = 0; y < field[x].length; y++){
                if(field[x][y].equals(":bomb:")) continue;
                int totalBombs = 0;
                boolean hasLeft = y > 0;
                boolean hasRight = y < (columns-1);
                boolean hasTop = x > 0;
                boolean hasBottom = x < (rows-1);

                //left
                totalBombs += (hasLeft && field[x][y-1].equals(":bomb:") ? 1 : 0);

                //top left
                totalBombs += (hasLeft && hasTop && field[x-1][y-1].equals(":bomb:") ? 1 : 0);

                //top
                totalBombs += (hasTop && field[x-1][y].equals(":bomb:") ? 1 : 0);

                //top right
                totalBombs += (hasTop && hasRight && field[x-1][y+1].equals(":bomb:") ? 1 : 0);

                //right
                totalBombs += (hasRight && field[x][y+1].equals(":bomb:") ? 1 : 0);

                // bottom right
                totalBombs += (hasRight && hasBottom && field[x+1][y+1].equals(":bomb:") ? 1 : 0);

                // bottom
                totalBombs += (hasBottom && field[x+1][y].equals(":bomb:") ? 1 : 0);

                //bottom left
                totalBombs += (hasBottom && hasLeft && field[x+1][y-1].equals(":bomb:") ? 1 : 0);

                String replacement;
                switch(totalBombs){
                    case 1:
                        replacement = ":one:";
                        break;
                    case 2:
                        replacement = ":two:";
                        break;
                    case 3:
                        replacement = ":three:";
                        break;
                    case 4:
                        replacement = ":four:";
                        break;
                    case 5:
                        replacement = ":five:";
                        break;
                    case 6:
                        replacement = ":six:";
                        break;
                    case 7:
                        replacement = ":seven:";
                        break;
                    case 8:
                        replacement = ":eight:";
                        break;
                    default:
                        replacement = ":zero:";
                        break;
                }

                field[x][y] = replacement;
            }
        }

        // Convert to disc message
        StringBuilder message = new StringBuilder();
        for(int x = 0; x < field.length; x++){
            for(int y = 0; y < field[x].length; y++){
                message.append("|| ").append(field[x][y]).append(" || ");
            }
            message.append("\n");
        }

        return message.substring(0, message.length()-2);
    }
}
