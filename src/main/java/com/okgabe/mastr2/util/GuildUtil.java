/*
 * Copyright 2020 Gabriel Keller
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * A copy of this license can be found at
 * https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode.
 */

package com.okgabe.mastr2.util;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandEvent;
import com.okgabe.mastr2.event.ResponseListener;
import com.okgabe.mastr2.exceptions.MemberSearchException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.ChannelUnion;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.List;

public class GuildUtil {

    public static ActionableFutureImpl<Member> retrieveMemberByName(CommandEvent e, String search){
        return retrieveMemberByName(e.getMastr(), e.getMessage().getChannel(), e.getAuthor(), search, true);
    }

    public static ActionableFutureImpl<Member> retrieveMemberByName(CommandEvent e, String search, boolean allowClarification){
        return retrieveMemberByName(e.getMastr(), e.getMessage().getChannel(), e.getAuthor(), search, allowClarification);
    }

    public static ActionableFutureImpl<Member> retrieveMemberByName(Mastr mastr, MessageChannelUnion channelUnion, Member invoker, String search, boolean allowClarification){
        ActionableFutureImpl<Member> returnFuture = new ActionableFutureImpl<>();
        TextChannel channel = channelUnion.asTextChannel(); // TODO address potential exception

        channel.getGuild().retrieveMembersByPrefix(search, 100).onSuccess(members -> {
            if(members.size() == 0){ // No results
                returnFuture.getOnFailure().accept(new MemberSearchException("That search had no results."));
            }

            else if(members.size() > 1){ // More than 1 result
                if(allowClarification && members.size() < 6){
                    channel.sendMessage("That search matched multiple results. Please select from the list below:\n" + nameJoin(members, "\n")).queue(clarificationMessage -> {
                        mastr.getResponseHandler().register(new ResponseListener(channel.getType(), channel.getIdLong(), invoker.getIdLong(), 30L, responseMessage -> {
                            if(StringUtil.isNumeric(responseMessage.getMessage().getContentRaw())){ // Check if numeric to avoid costly exception throwing
                                try{
                                    int selector = Integer.parseInt(responseMessage.getMessage().getContentRaw()) - 1;
                                    if(selector >= members.size() || selector < 0){
                                        clarificationMessage.delete().queue();
                                        returnFuture.getOnFailure().accept(new MemberSearchException("Provided number does not match a specific user"));
                                    }
                                    else{
                                        returnFuture.getOnSuccess().accept(members.get(selector));
                                    }
                                }
                                catch(NumberFormatException ignored){
                                    clarificationMessage.delete().queue();
                                    returnFuture.getOnFailure().accept(new MemberSearchException("Provided number does not match a specific user"));
                                }
                                mastr.getResponseHandler().unregister(responseMessage);
                            }
                            else{
                                clarificationMessage.delete().queue();
                                returnFuture.getOnFailure().accept(new MemberSearchException("That search matched multiple results, and no valid follow-up was provided."));
                                mastr.getResponseHandler().unregister(responseMessage);
                            }
                        }, timeout -> {
                            clarificationMessage.delete().queue();
                            returnFuture.getOnFailure().accept(new MemberSearchException("That search matched multiple results, and no follow-up was provided."));
                            mastr.getResponseHandler().unregister(timeout);
                        })); // Cancelling: timeout reached
                    });
                }
                else{
                    returnFuture.getOnFailure().accept(new MemberSearchException("That search matched multiple results. Please specify the user's ID or 4-digit tag for more precision."));
                }
            }

            else{ // Only 1 result
                returnFuture.getOnSuccess().accept(members.get(0));
            }
        }).onError(throwable -> { // Unknown error
            returnFuture.getOnFailure().accept(new MemberSearchException("An unknown error occurred", throwable));
        });

        return returnFuture;
    }

    private static Member getMemberByTag(String tag, List<Member> members){
        String[] divided = tag.split("#");
        if(divided.length!=2) return null;

        for(Member member : members){
            if((member.getNickname() != null && member.getNickname().equalsIgnoreCase(divided[0])) || member.getUser().getName().equalsIgnoreCase(divided[0])){
                if(member.getUser().getDiscriminator().equalsIgnoreCase(divided[1])) return member;
            }
        }

        return null;
    }

    private static String nameJoin(List<Member> members, String combiner){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            sb.append("`")
                .append(i+1)
                .append(")` ")
                .append(StringUtil.nameAndTag(members.get(i)))
                .append(combiner);
        }

        return sb.substring(0, sb.length()-combiner.length());
    }
}
