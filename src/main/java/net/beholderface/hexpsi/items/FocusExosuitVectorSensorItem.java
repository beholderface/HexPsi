package net.beholderface.hexpsi.items;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FocusExosuitVectorSensorItem extends FocusExosuitSensorItem {


    public static final String EVENT_TYPE_SUFFIX = "_vector";

    public FocusExosuitVectorSensorItem(Properties properties) {
        super(properties);
    }

    @Override
    public String getRequirementText() {
        return "vector";
    }

    @Override
    public boolean canWrite(ItemStack itemStack, @Nullable Iota iota) {
        return iota instanceof Vec3Iota;
    }

    @Override
    public String getEventType(ItemStack stack) {
        return super.getEventType(stack) + EVENT_TYPE_SUFFIX;
    }
}
