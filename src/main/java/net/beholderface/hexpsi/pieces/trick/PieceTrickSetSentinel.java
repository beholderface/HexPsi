package net.beholderface.hexpsi.pieces.trick;

import at.petrak.hexcasting.api.player.Sentinel;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.*;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickSetSentinel extends PieceTrick {
    SpellParam<Vector3> position;
    SpellParam<Number> tier;
    public PieceTrickSetSentinel(Spell spell) {
        super(spell);
        this.setStatLabel(EnumSpellStat.POTENCY, (new StatLabel("psi.spellparam.power", true)).max((double)0.5F).mul((double)70.0F).floor());
        this.setStatLabel(EnumSpellStat.COST, (new StatLabel("psi.spellparam.power", true)).max((double)0.5F).mul((double)210.0F).floor());
    }

    public void initParams() {
        this.addParam(this.position = new ParamVector("psi.spellparam.position", SpellParam.BLUE, false, false));
        this.addParam(this.tier = new ParamNumber("psi.spellparam.hexpsi:sentineltier", SpellParam.RED, false, true));
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        Double powerVal = (Double)this.getParamEvaluation(this.tier);
        if (powerVal != null && !(powerVal <= (double)0.0F)) {
            powerVal = Math.max((double)0F, powerVal);
            meta.addStat(EnumSpellStat.POTENCY, (int)(powerVal * (double)25.0F));
            meta.addStat(EnumSpellStat.COST, (int)(Math.max(powerVal * (double)250.0F, 25.0F)));
        } else {
            throw new SpellCompilationException("psi.spellerror.nonpositivevalue", this.x, this.y);
        }
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 positionVal = (Vector3)this.getParamValue(context, this.position);
        int tier = this.getParamValue(context, this.tier).intValue();
        if (positionVal == null) {
            throw new SpellRuntimeException("psi.spellerror.nullvector");
        } else if (!context.isInRadius(positionVal)) {
            throw new SpellRuntimeException("psi.spellerror.outsideradius");
        } else {
            if (tier == 0){
                IXplatAbstractions.INSTANCE.setSentinel(context.caster, null);
            } else {
                IXplatAbstractions.INSTANCE.setSentinel(context.caster, new Sentinel(tier == 2, positionVal.toVec3D(), context.caster.level().dimension()));
            }
            return null;
        }
    }
}
