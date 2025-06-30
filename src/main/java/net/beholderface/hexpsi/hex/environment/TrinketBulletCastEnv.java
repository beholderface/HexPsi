package net.beholderface.hexpsi.hex.environment;

import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.addldata.ADMediaHolder;
import at.petrak.hexcasting.api.casting.eval.env.PackagedItemCastEnv;
import at.petrak.hexcasting.api.pigment.FrozenPigment;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class TrinketBulletCastEnv extends PackagedItemCastEnv {

    public final ItemStack bulletStack;

    public TrinketBulletCastEnv(ServerPlayer caster, InteractionHand castingHand, ItemStack bullet) {
        super(caster, castingHand);
        this.bulletStack = bullet;
    }

    @Override
    public FrozenPigment getPigment() {
        ADHexHolder casterHexHolder = IXplatAbstractions.INSTANCE.findHexHolder(bulletStack);
        if (casterHexHolder == null) {
            return IXplatAbstractions.INSTANCE.getPigment(this.caster);
        } else {
            FrozenPigment hexHolderPigment = casterHexHolder.getPigment();
            return hexHolderPigment != null ? hexHolderPigment : IXplatAbstractions.INSTANCE.getPigment(this.caster);
        }
    }

    @Override
    public long extractMediaEnvironment(long costLeft, boolean simulate) {
        if (this.caster.isCreative()) {
            return 0L;
        } else {
            ADHexHolder casterHexHolder = IXplatAbstractions.INSTANCE.findHexHolder(bulletStack);
            if (casterHexHolder == null) {
                return costLeft;
            } else {
                boolean canCastFromInv = casterHexHolder.canDrawMediaFromInventory();
                ADMediaHolder casterMediaHolder = IXplatAbstractions.INSTANCE.findMediaHolder(bulletStack);
                if (casterMediaHolder != null) {
                    long extracted = casterMediaHolder.withdrawMedia((long)((int)costLeft), simulate);
                    costLeft -= extracted;
                }

                if (canCastFromInv && costLeft > 0L) {
                    costLeft = this.extractMediaFromInventory(costLeft, this.canOvercast(), simulate);
                }

                return costLeft;
            }
        }
    }

}
