package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapOthersName;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamVector;

public class PieceTrickWriteEntity extends PieceTrickWriteIota{
    SpellParam<Entity> entity;

    public PieceTrickWriteEntity(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.entity = new ParamEntity("psi.spellparam.hexpsi:entity", EntityIota.TYPE.color(), false, false));
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        var entity = this.getParamValue(context, this.entity);
        Iota iota = new EntityIota(entity);
        Player truename = MishapOthersName.getTrueNameFromDatum(iota, context.caster);
        if (truename != null && truename != context.caster){
            return null;
        }
        this.holder.writeIota(iota, false);
        return null;
    }
}
