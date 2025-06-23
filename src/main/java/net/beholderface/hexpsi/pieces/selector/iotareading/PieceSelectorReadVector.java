package net.beholderface.hexpsi.pieces.selector.iotareading;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;

public class PieceSelectorReadVector extends PieceSelectorReadIota{
    public PieceSelectorReadVector(Spell spell) {
        super(spell);
    }

    @Override
    public boolean isCompatibleIota(Iota iota) {
        return iota.getType() == Vec3Iota.TYPE;
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        return new Vector3(((Vec3Iota)this.iota).getVec3());
    }
}
