package net.beholderface.hexpsi.pieces.trick;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.api.player.Sentinel;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
        this.setStatLabel(EnumSpellStat.POTENCY, (new StatLabel("psi.spellparam.hexpsi:sentineltier", true)).max((double)10.0F).mul((double)25.0F).floor());
        this.setStatLabel(EnumSpellStat.COST, (new StatLabel("psi.spellparam.hexpsi:sentineltier", true)).mul((double)250.0F).max((double)25.0F).floor());
    }

    public void initParams() {
        this.addParam(this.position = new ParamVector("psi.spellparam.position", SpellParam.BLUE, false, false));
        this.addParam(this.tier = new ParamNumber("psi.spellparam.hexpsi:sentineltier", SpellParam.RED, false, true));
    }

    public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
        super.addToMetadata(meta);
        Double powerVal = (Double)this.getParamEvaluation(this.tier);
        if (powerVal != null) {
            if (powerVal == 0 || powerVal == 1 || powerVal == 2){
                meta.addStat(EnumSpellStat.POTENCY, (int)Math.max(powerVal * (double)25.0F, 10.0f));
                meta.addStat(EnumSpellStat.COST, (int)(Math.max(powerVal * (double)250.0F, 25.0F)));
            } else {
                throw new SpellCompilationException("hexpsi.spellerror.badtier", this.x, this.y);
            }
        }
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        Vector3 positionVal = (Vector3)this.getParamValue(context, this.position);
        double tier = this.getParamValue(context, this.tier).doubleValue();
        if (positionVal == null) {
            throw new SpellRuntimeException("psi.spellerror.nullvector");
        } else if (!context.isInRadius(positionVal)) {
            throw new SpellRuntimeException("psi.spellerror.outsideradius");
        } else {
            if (tier == 0.0){
                IXplatAbstractions.INSTANCE.setSentinel(context.caster, null);
            } else if (tier == 1.0 || tier == 2.0) {
                boolean greater = tier == 2;
                try {
                    if (greater && context.caster instanceof ServerPlayer player && !(player.getAdvancements().getOrStartProgress(
                            player.server.getAdvancements().getAdvancement(new ResourceLocation("hexcasting:enlightenment"))).isDone())){
                        throw new SpellRuntimeException("hexpsi.spellerror.enlightenment");
                    }
                } catch (NullPointerException exception){
                    throw new SpellRuntimeException("hexpsi.spellerror.enlightenment");
                }
                IXplatAbstractions.INSTANCE.setSentinel(context.caster, new Sentinel(greater, positionVal.toVec3D(), context.caster.level().dimension()));
            } else {
                throw new SpellRuntimeException("hexpsi.spellerror.badtier");
            }
            return null;
        }
    }
}
