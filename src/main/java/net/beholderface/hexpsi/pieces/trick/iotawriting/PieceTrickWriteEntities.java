package net.beholderface.hexpsi.pieces.trick.iotawriting;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapOthersName;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamEntity;
import vazkii.psi.api.spell.param.ParamEntityListWrapper;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

import java.util.ArrayList;
import java.util.List;

public class PieceTrickWriteEntities extends PieceTrickWriteIota{
    SpellParam<EntityListWrapper> entities;

    public PieceTrickWriteEntities(Spell spell) {
        super(spell);
    }

    public void initParams() {
        this.addParam(this.entities = new ParamEntityListWrapper("psi.spellparam.hexpsi:entities", ListIota.TYPE.color(), false, false));
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        var entities = this.getParamValue(context, this.entities);
        List<Iota> list = new ArrayList<>();
        for (Entity e : entities){
            list.add(new EntityIota(e));
        }
        Iota iota = new ListIota(list);
        Player truename = MishapOthersName.getTrueNameFromDatum(iota, context.caster);
        if (truename != null && truename != context.caster){
            return null;
        }
        this.holder.writeIota(iota, false);
        return null;
    }
}
