import events.EntityGrief
import events.OnFire
import events.OnExplosion
import org.bukkit.plugin.java.JavaPlugin

class AntiStructureGriefing : JavaPlugin(){

    override fun onEnable() {
        saveDefaultConfig()
        val entityNames: List<String> = config.getStringList("entities")
        if (entityNames.contains("FIRE")) {
            server.pluginManager.registerEvents(OnFire(this), this)
        }
        server.pluginManager.registerEvents(OnExplosion(this), this)
        server.pluginManager.registerEvents(EntityGrief(this), this)
    }
}