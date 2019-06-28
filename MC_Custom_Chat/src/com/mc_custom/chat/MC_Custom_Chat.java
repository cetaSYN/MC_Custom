package com.mc_custom.chat;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.mc_custom.chat.channels.ChannelHandler;
import com.mc_custom.chat.channels.ChatChannel;
import com.mc_custom.chat.commands.ChatCommandHandler;
import com.mc_custom.chat.commands.MeCommand;
import com.mc_custom.chat.exceptions.ChannelAlreadyExistsException;
import com.mc_custom.chat.listeners.ChannelChangeListener;
import com.mc_custom.chat.listeners.ChatListener;
import com.mc_custom.chat.listeners.JoinQuitListener;
import com.mc_custom.chat.member.ChatMember;
import com.mc_custom.chat.member.MemberHandler;
import com.mc_custom.chat.subcommands.AcceptCommand;
import com.mc_custom.chat.subcommands.CreateCommand;
import com.mc_custom.chat.subcommands.HighlightCommand;
import com.mc_custom.chat.subcommands.InviteCommand;
import com.mc_custom.chat.subcommands.JoinCommand;
import com.mc_custom.chat.subcommands.KickCommand;
import com.mc_custom.chat.subcommands.ListCommand;
import com.mc_custom.chat.subcommands.LockCommand;
import com.mc_custom.chat.subcommands.MonitorCommand;
import com.mc_custom.chat.subcommands.MoveCommand;
import com.mc_custom.chat.subcommands.OpenCommand;
import com.mc_custom.chat.subcommands.PasswordCommand;
import com.mc_custom.chat.subcommands.RemoveCommand;
import com.mc_custom.chat.subcommands.RenameCommand;

public class MC_Custom_Chat extends JavaPlugin {

   public static ChatChannel global_chat;
   public static ChatChannel staff_chat;
   private HashMap<String, Map<String, ChatColor>> highlight_list;
   private MC_Custom_Core core;
   private ChannelHandler channel_handler;
   private MemberHandler member_handler;
   private LinkedList<ChatMember> monitors;

   @Override
   public void onEnable( ) {
      /* Get Handle to Core */
      try {
         core = ( MC_Custom_Core ) getServer().getPluginManager().getPlugin("MC_Custom_Core");
      }
      catch (Exception ex) {
         Bukkit.getLogger().severe(ex.getMessage());
         ex.printStackTrace();
         Bukkit.getLogger().severe("Error linking to MC_Custom_Core! Shutting Down!");
         this.getServer().shutdown();
      }

      /* Initialize Resources */
      member_handler = new MemberHandler(this.getCore());
      channel_handler = new ChannelHandler(this);
      global_chat = new ChatChannel("GlobalChat", "Console");
      staff_chat = new ChatChannel("StaffChat", "Console");
      staff_chat.setLocked(true);
      monitors = new LinkedList<ChatMember>();
      highlight_list = new HashMap<String, Map<String, ChatColor>>();

      try {
         channel_handler.addChannel(global_chat);
         channel_handler.addChannel(staff_chat);
      }
      catch (ChannelAlreadyExistsException e) {
         e.printStackTrace();
      }

      /* Register Listeners */
      this.getServer().getPluginManager().registerEvents(new ChannelChangeListener(this), this);
      this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);
      this.getServer().getPluginManager().registerEvents(new JoinQuitListener(this), this);

      /* Register Command Handlers */
      CommandHandler<BaseCommand> command_handler = core.getCommandHandler();
      ChatCommandHandler chat_command_handler = new ChatCommandHandler(this);
      /* Register Commands */
      chat_command_handler.addCommand(new AcceptCommand(this));
      chat_command_handler.addCommand(new CreateCommand(this));
      chat_command_handler.addCommand(new HighlightCommand(this));
      chat_command_handler.addCommand(new InviteCommand(this));
      chat_command_handler.addCommand(new JoinCommand(this));
      chat_command_handler.addCommand(new KickCommand(this));
      chat_command_handler.addCommand(new ListCommand(this));
      chat_command_handler.addCommand(new LockCommand(this));
      chat_command_handler.addCommand(new MonitorCommand(this));
      chat_command_handler.addCommand(new MoveCommand(this));
      chat_command_handler.addCommand(new OpenCommand(this));
      chat_command_handler.addCommand(new PasswordCommand(this));
      chat_command_handler.addCommand(new RemoveCommand(this));
      chat_command_handler.addCommand(new RenameCommand(this));
      command_handler.addCommand(chat_command_handler);
      command_handler.addCommand(new MeCommand(this));
      // Never mind, didn't see this in Core
      //command_handler.addCommand(new NickCommand(this));
   }


   public MC_Custom_Core getCore( ) {
      return core;
   }

   public ChannelHandler getChannelHandler( ) {
      return channel_handler;
   }

   public MemberHandler getMemberHandler( ) {
      return member_handler;
   }

   public CommandHandler<BaseCommand> getCommandHandler( ) {
      return core.getCommandHandler();
   }

   public LinkedList<ChatMember> getMonitoringStaff( ) {
      return monitors;
   }
   public HashMap<String, Map<String, ChatColor>> getHighlightList(){
       return highlight_list;
   }
   public boolean toggleMonitor( ChatMember member ) {
      if (monitors.contains(member)) {
         monitors.remove(member);
         return false;
      }
      else {
         monitors.add(member);
         return true;
      }
   }
   
   public void addToMonitor( ChatMember member ) {
      if (!monitors.contains(member)) {
         monitors.add(member);
      }
   }
}