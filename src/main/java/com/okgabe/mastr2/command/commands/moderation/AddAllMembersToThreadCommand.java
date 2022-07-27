package com.okgabe.mastr2.command.commands.moderation;

import com.okgabe.mastr2.Mastr;
import com.okgabe.mastr2.command.CommandBase;
import com.okgabe.mastr2.command.CommandCategory;
import com.okgabe.mastr2.command.CommandEvent;
import com.okgabe.mastr2.permission.BotRole;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.ThreadChannel;

public class AddAllMembersToThreadCommand extends CommandBase {
    public AddAllMembersToThreadCommand(Mastr mastr) {
        super(mastr);
        this.command = "addallmemberstothread";
        this.description = "Adds all members of server to a thread.";
        this.category = CommandCategory.MODERATION;
        this.syntax = new String[] {"[roleid] - ID of role to add members from"};
        this.aliases = new String[] {"aamtt"};
        this.minimumRole = BotRole.DEFAULT;
    }

    @Override
    public boolean called(CommandEvent e) {
        return true;
    }

    @Override
    public void execute(CommandEvent e) {
        if(!e.getAuthor().isOwner()){
            e.replyError("You must be the owner of the guild to run this command!").queue();
            return;
        }

        if(!e.getChannel().getType().isThread()){
            e.replyError("You must run this command in a thread!").queue();
            return;
        }

        ThreadChannel thread = (ThreadChannel)e.getChannel();

        Role role = null;

        if(e.getArgs().length > 0){
            try{
                long roleId = Long.parseLong(e.getArgs()[0]);
                role = e.getGuild().getRoleById(roleId);
            }
            catch(NumberFormatException ex){
                e.replyError("Input a valid role id!").queue();
                return;
            }

            if(role == null){
                e.replyError("That ID was not a role!").queue();
                return;
            }
        }

        if(role == null)
            role = e.getGuild().getPublicRole();

        for(Member m : e.getGuild().getMembersWithRoles(role)){
            thread.addThreadMember(m).queue();
        }

        e.reply("Done!").queue();
    }
}
