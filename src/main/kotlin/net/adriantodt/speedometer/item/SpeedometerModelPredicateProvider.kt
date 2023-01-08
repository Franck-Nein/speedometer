package net.adriantodt.speedometer.item;

import net.adriantodt.speedometer.ExpandedLivingEntity;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

// Model predicate provider for the speedometer item that determines the animation state of the item
object SpeedometerModelPredicateProvider : UnclampedModelPredicateProvider {
    // Determines the animation state of the item based on the entity's velocity
    override fun unclampedCall(stack: ItemStack?, world: ClientWorld?, entity: LivingEntity?, seed: Int): Float {
        if (entity == null) {
            // If the entity is null, return 0
            return 0f;
        }
        // Get the last position of the entity from the ExpandedLivingEntity mixin
        val lastPos = (entity as? ExpandedLivingEntity)?.lastPos
        if (lastPos != null) {
            // Calculate the velocity based on the distance between the last position and the current position
            // Return the velocity as a float, scaled up a bit
            return lastPos.distanceTo(entity.pos).toFloat() * 4.16f
        }
        // If the last position is not available, fall back to using the entity's velocity
        // Return the velocity as a float, scaled up a bit
        return entity.velocity.length().toFloat() * 4.16f;
    }
}

