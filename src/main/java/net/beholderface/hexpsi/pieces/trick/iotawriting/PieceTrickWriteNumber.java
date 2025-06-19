package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;

public class PieceTrickWriteNumber extends PieceTrickWriteIota{
    SpellParam<Number> num;

    public PieceTrickWriteNumber(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.num = new ParamNumber("psi.spellparam.hexpsi:num", DoubleIota.TYPE.color(), false, false));
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        var num = this.getParamValue(context, this.num).doubleValue();
        this.holder.writeIota(new DoubleIota(num), false);
        return null;
    }
}
