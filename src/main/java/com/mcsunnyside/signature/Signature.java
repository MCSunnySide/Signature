package com.mcsunnyside.signature;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Signature extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("signature").setExecutor(this);
        new PlaceHolderRegister(this).register();
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("只有玩家可以使用此命令");
            return true;
        }
        Player player = (Player)sender;
        StringBuilder builder = new StringBuilder();
        boolean start = true;
        for (String part:args) {
            if(start){
                start = false;
            }else {
                builder.append(" ");
            }
            builder.append(part);
        }
        String signs = builder.toString();
        if(signs.isEmpty()){
            getConfig().set("signatures."+player.getUniqueId(),null);
            sender.sendMessage("您的签名已成功删除");
            saveConfig();
            reloadConfig();
            return true;
        }
        signs = ChatColor.translateAlternateColorCodes('&',signs);
        getConfig().set("signatures."+player.getUniqueId(),signs.replace(".","\\."));
        sender.sendMessage("您的签名已修改为："+signs);
        saveConfig();
        reloadConfig();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return super.onTabComplete(sender, command, alias, args);
    }
}
