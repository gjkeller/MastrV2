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
import com.okgabe.mastr2.command.ResponseListenerIdentity;
import com.okgabe.mastr2.entity.BotGuild;
import com.okgabe.mastr2.entity.BotUser;
import com.okgabe.mastr2.util.StringUtil;
import net.dv8tion.jda.api.entities.*;

import java.util.function.Consumer;

public class MinesweeperCommand extends CommandBase {
    private Emote bombEmote;
    private Emote flagEmote;
    private Emote oneEmote;
    private Emote twoEmote;
    private Emote threeEmote;
    private Emote fourEmote;
    private Emote fiveEmote;
    private Emote sixEmote;
    private Emote sevenEmote;
    private Emote eightEmote;
    private Emote nineEmote;

    private Emote aEmote;
    private Emote bEmote;
    private Emote cEmote;
    private Emote dEmote;
    private Emote eEmote;
    private Emote fEmote;
    private Emote gEmote;
    private Emote hEmote;
    private Emote iEmote;

    private Emote blankTile;

    private String flag;
    private String bomb;

    public MinesweeperCommand(Mastr mastr) {
        super(mastr);
        bombEmote = mastr.getShardManager().getEmoteById(746599421764173944L);
        flagEmote = mastr.getShardManager().getEmoteById(746601049854574694L);
        oneEmote = mastr.getShardManager().getEmoteById(746642432728236092L);
        twoEmote = mastr.getShardManager().getEmoteById(746642432690487307L);
        threeEmote = mastr.getShardManager().getEmoteById(746642432770179173L);
        fourEmote = mastr.getShardManager().getEmoteById(746642432732561508L);
        fiveEmote = mastr.getShardManager().getEmoteById(746642432522715178L);
        sixEmote = mastr.getShardManager().getEmoteById(746642432472514581L);
        sevenEmote = mastr.getShardManager().getEmoteById(746642432342491210L);
        eightEmote = mastr.getShardManager().getEmoteById(746642432736755752L);
        nineEmote = mastr.getShardManager().getEmoteById(746642432745013308L);

        aEmote = mastr.getShardManager().getEmoteById(746645135760293908L);
        bEmote = mastr.getShardManager().getEmoteById(746645135671951401L);
        cEmote = mastr.getShardManager().getEmoteById(746645135412166748L);
        dEmote = mastr.getShardManager().getEmoteById(746645135688728606L);
        eEmote = mastr.getShardManager().getEmoteById(746645135630008360L);
        fEmote = mastr.getShardManager().getEmoteById(746645135592521769L);
        gEmote = mastr.getShardManager().getEmoteById(746645135609036841L);
        hEmote = mastr.getShardManager().getEmoteById(746645135328018463L);
        iEmote = mastr.getShardManager().getEmoteById(746645135663562782L);

        blankTile = mastr.getShardManager().getEmoteById(746647537913561128L);

        flag = flagEmote.getAsMention();
        bomb = bombEmote.getAsMention();
    }

    @Override
    public boolean called(String[] args) {
        return true;
    }

    @Override
    public void execute(Member author, BotGuild guild, BotUser user, MessageChannel channel, Message message, String[] args) {
        String[][] minesweeper = generateMinesweeper(9, 9, 10, bombEmote.getAsMention());
        String[][] copy = copy(minesweeper);
        String minesweeperString = minesweeperToString(minesweeper);

        channel.sendMessage(minesweeperString).queue(m1 -> {
            channel.sendMessage("You have started a game of Minesweeper! Here are some commands you can use:\n" +
                    "flag <tile>, reveal, end").queue(m2 -> {

                MinesweeperResponseListener responseListener = new MinesweeperResponseListener(channel.getType(), channel.getIdLong(), user.getUserId(), 5*60, ident -> {
                    MinesweeperResponseListener identity = (MinesweeperResponseListener)ident;
                    String content = ident.getMessage().getContentRaw().toLowerCase();
                    if(content.startsWith("f")){
                        if(!content.contains(" ")) return;

                        ident.getMessage().delete().queue();
                        identity.setFailedAttempt(false);
                        String tileCode = content.split(" ")[1];
                        char firstChar = tileCode.charAt(0);
                        char secondChar = tileCode.charAt(1);
                        int row;
                        int col;

                        try{
                            if(StringUtil.isNumeric(String.valueOf(firstChar))){
                                row = Integer.parseInt(String.valueOf(firstChar))-1;
                                col = StringUtil.positionInAlphabet(secondChar);
                                if(col == -1) throw new Exception();
                            }
                            else{
                                row = Integer.parseInt(String.valueOf(secondChar))-1;
                                col = StringUtil.positionInAlphabet(firstChar);
                                if(col == -1) throw new Exception();
                            }
                        }
                        catch(Exception ignored){
                            m2.editMessage("You provided an invalid tile!").queue();
                            return;
                        }

                        String tile = copy[row][col];
                        if(tile.equals(flagEmote.getAsMention())){
                            tile = minesweeper[row][col];
                        }
                        else{
                            tile = flagEmote.getAsMention();
                        }
                        copy[row][col] = tile;

                        m1.editMessage(minesweeperToString(copy)).queue();
                        m2.editMessage("Set tile " + firstChar + secondChar + " to a flag!").queue();
                    }
                    else if(content.startsWith("r")){
                        ident.getMessage().delete().queue();
                        m1.editMessage(minesweeperString.replaceAll("\\|\\|", "")).queue();
                        m2.editMessage("Revealed! Your game is over.").queue();
                        mastr.getResponseHandler().unregister(identity);
                    }
                    else if(content.startsWith("e")){
                        ident.getMessage().delete().queue();
                        m2.editMessage("Ending your game of Minesweeper.").queue();
                        mastr.getResponseHandler().unregister(ident);
                    }
                    else{
                        if(identity.isFailedAttempt()){
                            m2.editMessage("Ending your game of Minesweeper.").queue();
                            mastr.getResponseHandler().unregister(identity);
                        }
                        else{
                            m2.editMessage("I couldn't understand that. You can say flag, reveal, or end.").queue();
                            identity.setFailedAttempt(true);
                        }
                    }
                }, expiration -> {
                    m2.editMessage("Your game of Minesweeper has expired.").queue();
                });

                mastr.getResponseHandler().register(responseListener);
            });
        });
    }

    public class MinesweeperResponseListener extends ResponseListenerIdentity {

        private boolean failedAttempt = false;

        public MinesweeperResponseListener(ChannelType channelType, long channelId, long userId, long timeout, Consumer<ResponseListenerIdentity> handler, Consumer<ResponseListenerIdentity> timeoutHandler) {
            super(channelType, channelId, userId, timeout, handler, timeoutHandler);
        }

        public boolean isFailedAttempt() {
            return failedAttempt;
        }

        public void setFailedAttempt(boolean failedAttempt) {
            this.failedAttempt = failedAttempt;
        }
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

    @Override
    public String[] getAliases() {
        return new String[] {"msw"};
    }

    public String minesweeperToString(String[][] field){
        // Convert to disc message
        StringBuilder message = new StringBuilder();
        for(int x = 0; x < field.length; x++){
            String s = "";
            switch(x+1){
                case 1:
                    s = oneEmote.getAsMention();
                    break;
                case 2:
                    s = twoEmote.getAsMention();
                    break;
                case 3:
                    s = threeEmote.getAsMention();
                    break;
                case 4:
                    s = fourEmote.getAsMention();
                    break;
                case 5:
                    s = fiveEmote.getAsMention();
                    break;
                case 6:
                    s = sixEmote.getAsMention();
                    break;
                case 7:
                    s = sevenEmote.getAsMention();
                    break;
                case 8:
                    s = eightEmote.getAsMention();
                    break;
                case 9:
                    s = nineEmote.getAsMention();
            }
            message.append(s).append(" ");
            for(int y = 0; y < field[x].length; y++){
                if(field[x][y].equals(flag))
                    message.append(field[x][y]).append(" ");
                else
                    message.append("||").append(field[x][y]).append("|| ");
            }
            message.append("\n");
        }

        String msw = message.substring(0, message.length()-2);
        StringBuilder topRow = new StringBuilder();
        topRow.append(blankTile.getAsMention()).append(" ");
        for(int i = 0; i < field[0].length; i++){
            String s = String.valueOf(StringUtil.numberToAlphabet(i));
            switch(s){
                case "a":
                    s = aEmote.getAsMention();
                    break;
                case "b":
                    s = bEmote.getAsMention();
                    break;
                case "c":
                    s = cEmote.getAsMention();
                    break;
                case "d":
                    s = dEmote.getAsMention();
                    break;
                case "e":
                    s = eEmote.getAsMention();
                    break;
                case "f":
                    s = fEmote.getAsMention();
                    break;
                case "g":
                    s = gEmote.getAsMention();
                    break;
                case "h":
                    s = hEmote.getAsMention();
                    break;
                case "i":
                    s = iEmote.getAsMention();
                    break;
            }
            topRow.append(s).append(" ");
        }
        msw = topRow.toString() + "\n" + msw;

        return msw;
    }

    public String[][] generateMinesweeper(int rows, int columns, int mines, String bomb){
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
            while(field[x][y].equals(bomb));

            field[x][y] = bomb;
        }

        // Fix number values
        for(int x = 0; x < field.length;x++){
            for(int y = 0; y < field[x].length; y++){
                if(field[x][y].equals(bomb)) continue;
                int totalBombs = 0;
                boolean hasLeft = y > 0;
                boolean hasRight = y < (columns-1);
                boolean hasTop = x > 0;
                boolean hasBottom = x < (rows-1);

                //left
                totalBombs += (hasLeft && field[x][y-1].equals(bomb) ? 1 : 0);

                //top left
                totalBombs += (hasLeft && hasTop && field[x-1][y-1].equals(bomb) ? 1 : 0);

                //top
                totalBombs += (hasTop && field[x-1][y].equals(bomb) ? 1 : 0);

                //top right
                totalBombs += (hasTop && hasRight && field[x-1][y+1].equals(bomb) ? 1 : 0);

                //right
                totalBombs += (hasRight && field[x][y+1].equals(bomb) ? 1 : 0);

                //bottom right
                totalBombs += (hasRight && hasBottom && field[x+1][y+1].equals(bomb) ? 1 : 0);

                //bottom
                totalBombs += (hasBottom && field[x+1][y].equals(bomb) ? 1 : 0);

                //bottom left
                totalBombs += (hasBottom && hasLeft && field[x+1][y-1].equals(bomb) ? 1 : 0);

                String replacement = ":" + StringUtil.numberToString( totalBombs) + ":";

                field[x][y] = replacement;
            }
        }

        return field;
    }

    public static String[][] copy(String[][] array){
        String[][] copy = new String[array.length][array[0].length];

        for(int i = 0; i < array.length; i++){
            for(int x = 0; x < array[i].length; x++){
                copy[i][x] = array[i][x];
            }
        }

        return copy;
    }
}
