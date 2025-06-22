package net.beholderface.hexpsi.pieces.selector;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.items.FocusExosuitVectorSensorItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceSelector;
import vazkii.psi.common.item.armor.ItemPsimetalExosuitHelmet;

public class PieceSelectorHelmetVector extends PieceSelector {
    public PieceSelectorHelmetVector(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        Player caster = context.caster;
        if (caster instanceof ServerPlayer serverPlayer){
            ItemStack helmetStack = caster.getInventory().armor.get(3);
            if (helmetStack.getItem() instanceof ItemPsimetalExosuitHelmet helmet){
                ItemStack sensorStack = helmet.getAttachedSensor(helmetStack);
                if (sensorStack.getItem() instanceof FocusExosuitVectorSensorItem sensor){
                    CompoundTag tag = sensor.readIotaTag(helmetStack);
                    if (tag == null){
                        //HexPsi.LOGGER.info("Null tag?");
                        return Vector3.zero;
                    }
                    Iota iota = IotaType.deserialize(tag, (ServerLevel) serverPlayer.level());
                    if (iota instanceof Vec3Iota vec3Iota) {
                        return Vector3.fromVec3d((vec3Iota).getVec3());
                    }
                }
            }
        } else {
            HexPsi.LOGGER.info("what the fuck why is this being called client-side");
        }
        return Vector3.zero;
    }
}
