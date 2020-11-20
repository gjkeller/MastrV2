/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.command.commands.moderation;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.command.CommandEvent;
import com.okgabe.mastr2.permission.BotRole;
import com.okgabe.mastr2.util.StringUtil;

import java.util.ArrayList;

public class PurgeCommand extends CommandBase {

    public PurgeCommand(Mastr mastr) {
        super(mastr);
        this.command = "purge";
        this.description = "Deletes up to a 100 messages in the chat.";
        this.category = CommandCategory.MODERATION;
        this.syntax = new String[] {"<amount> [users | bots | embeds | files | images | links | @user | \"quote\" | `regex` | to:id] - Specify filters for messages to purge."};
        this.aliases = new String[] {"prune", "clear"};
        this.minimumRole = BotRole.BOT_ADMINISTRATOR;
    }

    @Override
    public boolean called(CommandEvent e) {
        return e.getArgs().length > 0;
    }

    @Override
    public void execute(CommandEvent e) {
        PurgeSettings settings = new PurgeSettings(e.getArgs());

    }

    public static class PurgeSettings {
        private ArrayList<Long> userIds;
        private long upTo;
        private boolean users, bots, embeds, files, images, links, text;
        private int amount;
        private String quote;
        private String regex;

        public PurgeSettings(ArrayList<Long> userIds, long upTo, boolean users, boolean bots, boolean embeds, boolean files, boolean images, boolean links, boolean text, int amount, String quote, String regex) {
            this.userIds = userIds;
            this.upTo = upTo;
            this.users = users;
            this.bots = bots;
            this.embeds = embeds;
            this.files = files;
            this.images = images;
            this.links = links;
            this.text = text;
            this.amount = amount;
            this.quote = quote;
            this.regex = regex;
        }

        public PurgeSettings(){
            userIds = new ArrayList<>();
            users = false;
            bots = false;
            embeds = false;
            files = false;
            images = false;
            links = false;
            text = false;
            amount = 0;
        }

        public PurgeSettings(String[] arguments){
            // Regex isn't necessarily required to parse the arguments. I'm using this for speed and ease of use. I know Regex would be less verbose here, but this way is more flexible.
            for(int i = 0; i < arguments.length; i++) {
                String arg = arguments[i];
                if (amount == 0 && StringUtil.isNumeric(arg)) {
                    amount = Integer.parseInt(arg);
                } else if (arg.equalsIgnoreCase("users")) {
                    users = !users;
                } else if (arg.equalsIgnoreCase("bots")) {
                    bots = !bots;
                } else if (arg.equalsIgnoreCase("embeds")) {
                    embeds = !embeds;
                } else if (arg.equalsIgnoreCase("files")) {
                    files = !files;
                } else if (arg.equalsIgnoreCase("images")) {
                    images = !images;
                } else if (arg.equalsIgnoreCase("links")) {
                    links = !links;
                } else if(arg.equalsIgnoreCase("text")){
                    text = !text;
                } else if (arg.startsWith("\"") && arg.endsWith("\"")) {
                    quote = arg.substring(1, arg.length() - 1);
                } else if (arg.startsWith("`") && arg.endsWith("`")) {
                    regex = arg.substring(1, arg.length() - 1);
                } else if (arg.startsWith("<@!")) {
                    String inner = arg.substring(3, arg.length() - 1);
                    if (StringUtil.isNumeric(inner)) {
                        userIds.add(Long.parseLong(inner));
                    }
                } else if (arg.startsWith("<@")) {
                    String inner = arg.substring(2, arg.length() - 1);
                    if (StringUtil.isNumeric(inner)) {
                        userIds.add(Long.parseLong(inner));
                    }
                } else {
                    if(upTo != 0) continue;
                    String[] split = arg.split(":", 2);
                    if (split[0].equalsIgnoreCase("upto") || split[0].equalsIgnoreCase("to")){
                        if(arg.length() == 18 & StringUtil.isNumeric(arg)){
                            upTo = Long.parseLong(arg);
                        }
                        else if(arg.startsWith("https://canary.discord.com/channels") || arg.startsWith("https://discord.com/channels")){
                            String potentialId = arg.substring(arg.length() - 18);
                            if(StringUtil.isNumeric(potentialId))
                                upTo = Long.parseLong(potentialId);
                        }
                    }
                }
            }
        }

        public boolean clearEverything(){
            return !users && !bots && !embeds && !files && !images && !links && userIds.size()==0 && quote == null && regex == null;
        }

        public ArrayList<Long> getUserIds() {
            return userIds;
        }

        public void setUserIds(ArrayList<Long> userIds) {
            this.userIds = userIds;
        }

        public long getUpTo() {
            return upTo;
        }

        public void setUpTo(long upTo) {
            this.upTo = upTo;
        }

        public boolean isUsers() {
            return users;
        }

        public void setUsers(boolean users) {
            this.users = users;
        }

        public boolean isBots() {
            return bots;
        }

        public void setBots(boolean bots) {
            this.bots = bots;
        }

        public boolean isEmbeds() {
            return embeds;
        }

        public void setEmbeds(boolean embeds) {
            this.embeds = embeds;
        }

        public boolean isFiles() {
            return files;
        }

        public void setFiles(boolean files) {
            this.files = files;
        }

        public boolean isImages() {
            return images;
        }

        public void setImages(boolean images) {
            this.images = images;
        }

        public boolean isLinks() {
            return links;
        }

        public void setLinks(boolean links) {
            this.links = links;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }
    }
}
