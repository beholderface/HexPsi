package net.beholderface.hexpsi.mixin;

import at.petrak.hexcasting.api.player.Sentinel;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.psi.api.internal.MathHelper;
import vazkii.psi.api.spell.SpellContext;

@Mixin(SpellContext.class)
public class SentinelPsiAmbitMixin {
    @Shadow public Player caster;

    @Inject(method = "isInRadius(DDD)Z", at = @At(value = "HEAD", remap = false), remap = false, cancellable = true)
    private void extendedBySentinel(double x, double y, double z, CallbackInfoReturnable<Boolean> cir){
        if (this.caster != null){
            Sentinel sentinel = IXplatAbstractions.INSTANCE.getSentinel(this.caster);
            if (sentinel != null){
                boolean extended = false;
                if (sentinel.dimension() == this.caster.level().dimension() && sentinel.extendsRange()){
                    Vec3 sentinelPos = sentinel.position();
                    extended = MathHelper.pointDistanceSpace(x, y, z, sentinelPos.x, sentinelPos.y, sentinelPos.z) <= 16.0;
                }
                if (extended){
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
