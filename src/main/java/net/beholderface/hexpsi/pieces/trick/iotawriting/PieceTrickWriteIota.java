package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.*;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.beholderface.hexpsi.HexPsi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.*;
import vazkii.psi.api.spell.piece.PieceTrick;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

public abstract class PieceTrickWriteIota extends PieceTrick {

    ADIotaHolder holder = null;

    public PieceTrickWriteIota(Spell spell) {
        super(spell);
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 10);
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        assert context.caster != null;
        for (ItemStack checkedStack : context.caster.getHandSlots()){
            var holder = IXplatAbstractions.INSTANCE.findDataHolder(checkedStack);
            if (holder != null && holder.writeable()){
                this.holder = holder;
                break;
            }
        }
        if (this.holder == null){
            throw new SpellRuntimeException("hexpsi.spellerror.writeable");
        }
        return null;
    }
}
