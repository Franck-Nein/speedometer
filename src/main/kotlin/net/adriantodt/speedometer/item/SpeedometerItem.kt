package net.adriantodt.speedometer.item;

import net.adriantodt.speedometer.ExpandedLivingEntity;
import net.adriantodt.speedometer.Speedometer;
import net.adriantodt.speedometer.active;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

// Class representing a speedometer item in Minecraft
class SpeedometerItem(settings: Settings) : Item(settings) {
    // Called when the player right clicks with the item in their hand
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        // Get the stack representing the item
        val stack = user.getStackInHand(hand)

        // Create a copy of the stack
        val copy = stack.copy()
        // Get the NBT data of the copy
        val tag = copy.orCreateNbt
        // Toggle the active field in the NBT data
        tag.active = !tag.active
        // Return the updated stack with the toggled NBT data
        return TypedActionResult.success(copy)
    }

    // Called every game tick while the item is in the player's inventory
    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        // Check that the user is a player and has the ExpandedLivingEntity interface
        if (user is PlayerEntity && user is ExpandedLivingEntity && world.isClient && stack.nbt?.active == true) {
            // Get the unit of velocity to use
            val unit = Speedometer.configHolder.config.velocityUnit
            // Get the player's last position
            val lastPos = user.lastPos
            if (lastPos != null) {
                // Calculate the player's speed and send a message with the result
                val speed = lastPos.distanceTo(user.pos) * 20 * unit.multiplier
                user.sendMessage(Text.literal("| %.4f %s |".format(speed, unit.suffix)), true)
            }
        }
    }

    // Called when the player hovers over the item with the mouse
    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        // Add a description of the item to the tooltip
        tooltip += Text.translatable("$translationKey.description")
        // Add instructions on how to use the item to the tooltip
        tooltip += Text.translatable("tooltip.speedometer.configure_speedometer")
        tooltip += if (stack.nbt?.active == true) {
            // If the item is active, add a message to the tooltip indicating how to deactivate it
            Text.translatable("tooltip.speedometer.deactivate_speedometer")
        } else {
            // If the item is inactive, add a message to the tooltip indicating how to activate it
            Text.translatable("tooltip.speedometer.activate_speedometer")
        }
    }
}


