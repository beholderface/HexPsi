package net.beholderface.hexpsi.mixin;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.relocated.jankson.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.psi.common.item.ItemVectorRuler;
import vazkii.psi.common.item.base.ModItems;

@Mixin(ItemVectorRuler.class)
public class ReadWriteRulerMixin implements IotaHolderItem {
    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack itemStack) {
        Vec3 vec = ((ItemVectorRuler) ModItems.vectorRuler).getVector(itemStack).toVec3D();
        return IotaType.serialize(new Vec3Iota(vec));
    }

    @Override
    public boolean writeable(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canWrite(ItemStack itemStack, @Nullable Iota iota) {
        if (iota instanceof Vec3Iota it){
            Vec3 vec = it.getVec3();
            double[] components = {vec.x, vec.y, vec.z};
            for (double d : components){
                if (Math.floor(d) != d){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void writeDatum(ItemStack itemStack, @Nullable Iota iota) {
        if (iota instanceof Vec3Iota veciota){
            Vec3 vec = veciota.getVec3();
            itemStack.removeTagKey("dstY");
            CompoundTag tag = itemStack.getOrCreateTag();
            tag.putInt("srcX", (int)vec.x);
            tag.putInt("srcY", (int)vec.y);
            tag.putInt("srcZ", (int)vec.z);
        }
    }
}
