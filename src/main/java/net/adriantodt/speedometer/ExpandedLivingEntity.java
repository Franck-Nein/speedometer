package net.adriantodt.speedometer;

import net.minecraft.util.math.Vec3d;

// Interface for providing access to the last position of a living entity
public interface ExpandedLivingEntity {
    // Returns the last known position of the living entity
    Vec3d getLastPos();
}

