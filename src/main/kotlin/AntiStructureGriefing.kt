import events.CreeperExplodes
import events.EntityGrief
import events.OnFire
import events.OnTnt
import org.bukkit.plugin.java.JavaPlugin

class AntiStructureGriefing : JavaPlugin(){

    override fun onEnable() {
        saveDefaultConfig()
        val entityNames: List<String> = config.getStringList("entities")
        if (entityNames.contains("CREEPER")) {
            server.pluginManager.registerEvents(CreeperExplodes(this), this)
        }
        if (entityNames.contains("FIRE")) {
            server.pluginManager.registerEvents(OnFire(this), this)
        }
        if (entityNames.contains("TNT")) {
            server.pluginManager.registerEvents(OnTnt(this), this)
        }
        server.pluginManager.registerEvents(EntityGrief(this), this)
    }
}