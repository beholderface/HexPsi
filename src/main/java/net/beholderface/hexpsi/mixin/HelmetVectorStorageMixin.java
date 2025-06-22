package net.beholderface.hexpsi.mixin;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.items.FocusExosuitSensorItem;
import net.beholderface.hexpsi.registry.HexPsiItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.psi.common.item.armor.ItemPsimetalExosuitHelmet;

@Mixin(ItemPsimetalExosuitHelmet.class)
public abstract class HelmetVectorStorageMixin implements IotaHolderItem {
    @Shadow public abstract ItemStack getAttachedSensor(ItemStack stack);

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack itemStack) {
        if (this.getAttachedSensor(itemStack).getItem() instanceof FocusExosuitSensorItem focusSensor){
            CompoundTag tag = focusSensor.readIotaTag(itemStack);;
            return tag;
        }
        return null;
    }

    @Override
    public boolean writeable(ItemStack itemStack) {
        return this.getAttachedSensor(itemStack).getItem() instanceof FocusExosuitSensorItem;
    }

    @Override
    public boolean canWrite(ItemStack itemStack, @Nullable Iota iota) {
        return this.getAttachedSensor(itemStack).getItem() instanceof FocusExosuitSensorItem focusSensor && focusSensor.canWrite(itemStack, iota);
    }

    @Override
    public void writeDatum(ItemStack itemStack, @Nullable Iota iota) {
        if (this.getAttachedSensor(itemStack).getItem() instanceof FocusExosuitSensorItem focusSensor){
            focusSensor.writeDatum(itemStack, iota);
        }
    }
}
