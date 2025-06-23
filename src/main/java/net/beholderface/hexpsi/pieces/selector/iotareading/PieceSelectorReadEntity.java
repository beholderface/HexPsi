package net.beholderface.hexpsi.pieces.selector.iotareading;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;

public class PieceSelectorReadEntity extends PieceSelectorReadIota{
    public PieceSelectorReadEntity(Spell spell) {
        super(spell);
    }

    @Override
    public boolean isCompatibleIota(Iota iota) {
        return iota.getType() == EntityIota.TYPE;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        return ((EntityIota)this.iota).getEntity();
    }
}
