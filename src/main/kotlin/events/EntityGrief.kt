package events

import org.bukkit.Location
import org.bukkit.StructureType
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.Nullable

class EntityGrief(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun entityGrief(event: EntityChangeBlockEvent) {
        val entity: Entity = event.entity
        val entityNames: List<String> = plugin.config.getStringList("entities")
        if (!entityNames.contains(entity.type.name)) {
            return
        }
        plugin.config.getConfigurationSection("structures")?.getValues(false)?.forEach { (structureName, radius) ->
            val structure: StructureType? = StructureType.getStructureTypes()[structureName]
            if (structure != null) {
                val structureLoc: @Nullable Location? = entity.world.locateNearestStructure(entity.location, structure, 3, false)
                if (structureLoc != null) {
                    structureLoc.y = entity.location.y
                    if (entity.location.distance(structureLoc) < radius as Double) {
                        plugin.logger.info("Protect $structureName from griefing.")
                        event.isCancelled = true
                    }
                }
            }
        }
    }
}