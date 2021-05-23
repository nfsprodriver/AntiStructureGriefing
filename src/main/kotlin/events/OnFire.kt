package events

import com.destroystokyo.paper.event.entity.CreeperIgniteEvent
import org.bukkit.Location
import org.bukkit.StructureType
import org.bukkit.block.Block
import org.bukkit.entity.Enderman
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.Nullable

class OnFire(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun entityGrief(event: BlockIgniteEvent) {
        val block: Block = event.block
        plugin.config.getConfigurationSection("structures")?.getValues(false)?.forEach { (structureName, radius) ->
            val structure: StructureType? = StructureType.getStructureTypes()[structureName]
            if (structure != null) {
                val structureLoc: @Nullable Location? = block.world.locateNearestStructure(block.location, structure, 3, false)
                if (structureLoc != null) {
                    structureLoc.y = block.location.y
                    if (block.location.distance(structureLoc) < radius as Double) {
                        plugin.logger.info("Protect $structureName from burning.")
                        event.isCancelled = true
                    }
                }
            }
        }
    }
}