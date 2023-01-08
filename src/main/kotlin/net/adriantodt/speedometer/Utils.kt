package net.adriantodt.speedometer;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// Creates an Identifier object with the given path and the namespace "speedometer"
fun identifier(path: String) = Identifier("speedometer", path)

// Registers the given Item object in the Item registry using the Identifier object as the key
fun Identifier.item(item: Item) = apply {
    Registry.register(Registry.ITEM, this, item)
}

// Extension property for the NbtCompound class, gets and sets the "Active" field as a boolean
var NbtCompound.active
    get() = getBoolean("Active")
    set(value) = putBoolean("Active", value)

// Returns a default Item.Settings object with the ItemGroup set to TRANSPORTATION
fun itemSettings(): Item.Settings = Item.Settings().group(ItemGroup.TRANSPORTATION)

