package net.beholderface.hexpsi.mixin;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import net.beholderface.hexpsi.HexPsi;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.common.item.ItemCAD;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemCAD.class)
public abstract class ReadWriteCADMixin implements IotaHolderItem {

    @Shadow public abstract int getMemorySize(ItemStack stack);

    @Shadow public abstract Vector3 getStoredVector(ItemStack stack, int memorySlot) throws SpellRuntimeException;

    @Shadow public abstract void setStoredVector(ItemStack stack, int memorySlot, Vector3 vec) throws SpellRuntimeException;

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack itemStack) {
        int capacity = this.getMemorySize(itemStack);
        List<Iota> iotaList = new ArrayList<>();
        for (int i = 0; i < capacity; i++){
            try {
                iotaList.add(new Vec3Iota(this.getStoredVector(itemStack, i).toVec3D()));
            } catch (SpellRuntimeException exception){
                //this *should* be impossible, but...
                HexPsi.LOGGER.error("Attempted to read from an out-of-bounds memory index in a CAD.");
            }
        }
        ListIota listIota = new ListIota(iotaList);
        return IotaType.serialize(listIota);
    }

    @Override
    public boolean writeable(ItemStack itemStack) {
        //should this be allowed? not sure if it defeats the point of the "read helmet vector" piece too much
        return true;
    }

    @Override
    public boolean canWrite(ItemStack itemStack, @Nullable Iota iota) {
        boolean compatible = false;
        int size;
        if (iota != null){
            if (iota instanceof ListIota listIota){
                size = listIota.size() - 1;
            } else {
                size = iota.size();
            }
            if (size <= this.getMemorySize(itemStack)){
                if (iota instanceof ListIota list){
                    for (Iota contained : list.getList()){
                        if (!(contained instanceof Vec3Iota)){
                            return false;
                        }
                    }
                    compatible = true;
                }
                if (iota instanceof Vec3Iota){ //another thing I'm not sure should be allowed, since wrapping a single vector in a list is trivially easy
                    compatible = true;
                }
            }
        }
        return compatible;
    }

    @Override
    public void writeDatum(ItemStack itemStack, @Nullable Iota iota) {
        if (iota instanceof ListIota listIota){
            SpellList iotaList = listIota.getList();
            int capacity = this.getMemorySize(itemStack);
            int toWrite = Math.min(iotaList.size(), capacity);
            for (int i = 0; i < toWrite; i++){
                try {
                    this.setStoredVector(itemStack, i, new Vector3(((Vec3Iota)iotaList.getAt(i)).getVec3()));
                } catch (SpellRuntimeException exception){
                    //another thing that should be impossible
                    HexPsi.LOGGER.error("Attempted to write to an out-of-bounds memory index in a CAD.");
                }
            }
        } else if (iota instanceof Vec3Iota vec3Iota ){
            try {
                this.setStoredVector(itemStack, 0, new Vector3(vec3Iota.getVec3()));
            } catch (SpellRuntimeException exception){
                //what
                HexPsi.LOGGER.error("Attempted to write to an out-of-bounds memory index in a CAD, despite attempting to write to just the first slot.");
            }
        }
    }
}
