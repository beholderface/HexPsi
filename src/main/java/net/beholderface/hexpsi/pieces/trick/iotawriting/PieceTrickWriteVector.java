package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamVector;

public class PieceTrickWriteVector extends PieceTrickWriteIota{
    SpellParam<Vector3> vec;
    public PieceTrickWriteVector(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.vec = new ParamVector("psi.spellparam.hexpsi:vec", Vec3Iota.TYPE.color(), false, false));
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        var vec3 = (Vector3)this.getParamValue(context, this.vec);
        this.holder.writeIota(new Vec3Iota(vec3.toVec3D()), false);
        return null;
    }
}
