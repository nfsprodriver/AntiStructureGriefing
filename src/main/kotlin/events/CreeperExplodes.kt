package events

import com.destroystokyo.paper.event.entity.CreeperIgniteEvent
import org.bukkit.Location
import org.bukkit.StructureType
import org.bukkit.entity.Creeper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.Nullable

class CreeperExplodes(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun creeperExplodes(event: CreeperIgniteEvent) {
        val creeper: Creeper = event.entity
        plugin.config.getConfigurationSection("structures")?.getValues(false)?.forEach { (structureName, radius) ->
            val structure: StructureType? = StructureType.getStructureTypes()[structureName]
            if (structure != null) {
                val structureLoc: @Nullable Location? = creeper.world.locateNearestStructure(creeper.location, structure, 3, false)
                if (structureLoc != null) {
                    structureLoc.y = creeper.location.y
                    if (creeper.location.distance(structureLoc) < radius as Double) {
                        plugin.logger.info("Protect $structureName from creeper explosion.")
                        creeper.explosionRadius = 0
                    }
                }
            }
        }
    }
}