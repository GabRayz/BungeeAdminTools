package fr.Alphart.BAT.Utils;

import lombok.RequiredArgsConstructor;
import net.alpenblock.bungeeperms.BPPermission;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Provide a clean way to support both version of BungeePerms
 */
public class BPInterfaceFactory{
    
    public static PermissionProvider getBPInterface(final Plugin bpPlugin){
        return new BungeePermsV4((net.alpenblock.bungeeperms.platform.bungee.BungeePlugin) bpPlugin);
    }
    
    public interface PermissionProvider{
        public Collection<String> getPermissions(final CommandSender sender);
    }

    @RequiredArgsConstructor
    static class BungeePermsV4 implements PermissionProvider{
        private final net.alpenblock.bungeeperms.platform.bungee.BungeePlugin bpPlugin;

        @Override
        public Collection<String> getPermissions(CommandSender sender)
        {
            return bpPlugin.getBungeeperms().getPermissionsManager().getUser(sender.getName())
                           .getEffectivePerms(null, null)
                           .stream()
                           .map(BPPermission::getPermission)
                           .collect(Collectors.toList());
        }
    }
}