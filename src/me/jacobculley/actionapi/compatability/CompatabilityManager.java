// 
// Decompiled by Procyon v0.5.30
// 

package me.jacobculley.actionapi.compatability;

import java.util.Collection;
import org.bukkit.entity.Player;

public interface CompatabilityManager
{
    String getVersion();
    
    void sendTitle(final Player p0, final String p1);
    
    void sendSubtitle(final Player p0, final String p1);
    
    void sendAction(final Player p0, final String p1);
    
    void sendJSONMessage(final Player p0, final String p1);
    
    void sendJSONBroadcast(final Collection<? extends Player> p0, final String p1);
}
