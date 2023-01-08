package net.adriantodt.speedometer.mixin;

import net.adriantodt.speedometer.ExpandedLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Mixin class that adds new methods and fields to the LivingEntity class
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ExpandedLivingEntity {
    // Field to store the last position of the entity
    protected Vec3d speedometer$lastPos;

    // Constructor for the mixin class
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Method added by the mixin, returns the last position of the entity
    @Override
    public Vec3d getLastPos() {
        return speedometer$lastPos;
    }

    // Method that modifies the behavior of the tickMovement method in LivingEntity
    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void speedometer$beforeTickMovement(CallbackInfo info) {
        // Set the value of the speedometer$lastPos field to the current position of the entity
        speedometer$lastPos = this.getPos();
    }
}

