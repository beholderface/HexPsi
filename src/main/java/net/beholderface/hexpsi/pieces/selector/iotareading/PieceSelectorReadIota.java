package net.beholderface.hexpsi.pieces.selector.iotareading;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceSelector;

public abstract class PieceSelectorReadIota extends PieceSelector {

    ADIotaHolder holder = null;
    Iota iota = null;

    public PieceSelectorReadIota(Spell spell) {
        super(spell);
    }

    public abstract boolean isCompatibleIota(Iota iota);

    public Object execute(SpellContext context) throws SpellRuntimeException {
        assert context.caster != null;
        for (ItemStack checkedStack : context.caster.getHandSlots()){
            var holder = IXplatAbstractions.INSTANCE.findDataHolder(checkedStack);
            this.holder = holder;
            if (holder != null){
                this.iota = holder.readIota((ServerLevel) context.caster.level());
                if (this.iota == null || !this.isCompatibleIota(this.iota)){
                    throw new SpellRuntimeException("hexpsi.spellerror.unreadablecontents");
                }
            }
        }
        if (this.holder == null){
            throw new SpellRuntimeException("hexpsi.spellerror.readable");
        }
        return null;
    }
}
