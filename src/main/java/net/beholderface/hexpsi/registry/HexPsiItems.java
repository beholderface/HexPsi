package net.beholderface.hexpsi.registry;

import at.petrak.hexcasting.common.items.ItemStaff;
import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.items.FocusExosuitVectorSensorItem;
import net.beholderface.hexpsi.items.TrinketSpellBulletItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HexPsiItems {

    public static void init(IEventBus bus){
        ITEMS.register(bus);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HexPsi.MODID);

    public static final Item.Properties UNSTACKABLE = new Item.Properties().stacksTo(1);

    public static final RegistryObject<FocusExosuitVectorSensorItem> HELMET_SENSOR_FOCUS = ITEMS.register("exosuit_vector_focus_sensor", ()->new FocusExosuitVectorSensorItem(UNSTACKABLE));
    public static final RegistryObject<ItemStaff> PSI_CORE_STAFF = ITEMS.register("psi_core_staff", ()->new ItemStaff(UNSTACKABLE));
    public static final RegistryObject<TrinketSpellBulletItem> TRINKET_SPELL_BULLET = ITEMS.register("spell_bullet_trinket", ()->new TrinketSpellBulletItem(UNSTACKABLE));
}
