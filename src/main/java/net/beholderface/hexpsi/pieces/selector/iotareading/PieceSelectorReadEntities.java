package net.beholderface.hexpsi.pieces.selector.iotareading;

import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import net.minecraft.world.entity.Entity;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

import java.util.ArrayList;
import java.util.List;

public class PieceSelectorReadEntities extends PieceSelectorReadIota{
    public PieceSelectorReadEntities(Spell spell) {
        super(spell);
    }

    @Override
    public boolean isCompatibleIota(Iota iota) {
        if (iota instanceof ListIota list){
            for (Iota i : list.getList()){
                if (!(i instanceof EntityIota)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Class<?> getEvaluationType() {
        return EntityListWrapper.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        super.execute(context);
        ListIota listIota = (ListIota) this.iota;
        List<Entity> entityList = new ArrayList<>();
        for (Iota i : listIota.getList()){
            if (i instanceof EntityIota entityIota){
                entityList.add(entityIota.getEntity());
            }
        }
        return EntityListWrapper.make(entityList);
    }
}
