package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.piece.PieceTrick;

public abstract class PieceTrickWriteHelmetIota extends PieceTrick {

    ADIotaHolder holder = null;

    public PieceTrickWriteHelmetIota(Spell spell) {
        super(spell);
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        meta.addStat(EnumSpellStat.POTENCY, 10);
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        this.holder = null;
        assert context.caster != null;
        var holder = IXplatAbstractions.INSTANCE.findDataHolder(context.caster.getInventory().getArmor(3));
        if (holder != null && holder.writeable()){
            this.holder = holder;
        } else {
            throw new SpellRuntimeException("hexpsi.spellerror.writeable");
        }
        return null;
    }
}
