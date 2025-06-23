package net.beholderface.hexpsi.items;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vazkii.psi.common.item.ItemExosuitSensor;

public abstract class FocusExosuitSensorItem extends ItemExosuitSensor implements IotaHolderItem {

    public static final String TAG_IOTA_DATA = "hexpsi:iotastorage";
    public static final String EVENT_TYPE = "hexpsi.event.writehelmet";

    public FocusExosuitSensorItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean writeable(ItemStack itemStack) {
        return false;
    }

    @Override
    public String getEventType(ItemStack stack) {
        return EVENT_TYPE;
    }

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        return nbt.getCompound(TAG_IOTA_DATA);
    }

    @Override
    public void writeDatum(ItemStack itemStack, @Nullable Iota iota) {
        if (iota != null){
            CompoundTag nbt = itemStack.getOrCreateTag();
            NBTHelper.putCompound(nbt, TAG_IOTA_DATA, IotaType.serialize(iota));
        }
    }

    public abstract String getRequirementText();

    @OnlyIn(Dist.CLIENT)
    public int getColor(ItemStack stack) {
        return 12027607;
    }
}
