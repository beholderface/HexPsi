package net.beholderface.hexpsi.registry;

import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.items.FocusExosuitSensorItem;
import net.beholderface.hexpsi.items.FocusExosuitVectorSensorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.psi.common.item.base.ModItems;

public class HexPsiItems {

    public static void init(IEventBus bus){
        ITEMS.register(bus);

    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HexPsi.MODID);

    public static final RegistryObject<Item> HELMET_SENSOR_FOCUS = ITEMS.register("exosuit_vector_focus_sensor",
            ()->new FocusExosuitVectorSensorItem(new Item.Properties().stacksTo(1)));

}
