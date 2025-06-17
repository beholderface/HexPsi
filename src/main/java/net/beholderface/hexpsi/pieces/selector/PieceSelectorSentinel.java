package net.beholderface.hexpsi.pieces.selector;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceSelector;

public class PieceSelectorSentinel extends PieceSelector {
    public PieceSelectorSentinel(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Vector3.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        var sentinel = IXplatAbstractions.INSTANCE.getSentinel(context.caster);
        if (sentinel != null){
            return Vector3.fromVec3d(sentinel.position());
        } else {
            return Vector3.zero;
        }
    }
}
