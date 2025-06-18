package net.beholderface.hexpsi.mixin;

import at.petrak.hexcasting.common.lib.HexMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.psi.common.core.handler.PlayerDataHandler;

import java.lang.ref.WeakReference;

@Mixin(PlayerDataHandler.PlayerData.class)
public class PlayerRegenRateMixin {
    @Shadow public int regen;

    @Shadow @Final public WeakReference<Player> playerWR;

    @Inject(method = "getRegenPerTick()I", at = @At(value = "HEAD", remap = false), remap = false, cancellable = true)
    public void modifyBaseRegen(CallbackInfoReturnable<Integer> cir){
        Player player = this.playerWR.get();
        if (player != null && (player.hasEffect(HexMobEffects.ENLARGE_GRID) || player.hasEffect(HexMobEffects.SHRINK_GRID))){
            double multiplier = 1.0;
            MobEffectInstance clarityInstance = player.getEffect(HexMobEffects.ENLARGE_GRID);
            if (clarityInstance != null){
                multiplier += ((double)clarityInstance.getAmplifier() + 1.0) / 5.0;
            }
            MobEffectInstance cloudingInstance = player.getEffect(HexMobEffects.SHRINK_GRID);
            if (cloudingInstance != null){
                multiplier -= ((double)cloudingInstance.getAmplifier() + 1.0) / 5.0;
            }
            cir.setReturnValue((int)((double)this.regen * multiplier));
        }
    }
}
