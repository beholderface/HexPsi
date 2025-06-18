package net.beholderface.hexpsi.pieces.selector;

import at.petrak.hexcasting.xplat.IXplatAbstractions;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceSelector;

public class PieceSelectorSentinelTier extends PieceSelector {
    public PieceSelectorSentinelTier(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }

    public Object execute(SpellContext context) throws SpellRuntimeException {
        var sentinel = IXplatAbstractions.INSTANCE.getSentinel(context.caster);
        if (sentinel != null && sentinel.dimension() == context.caster.level().dimension()){
            return sentinel.extendsRange() ? 2.0 : 1.0;
        } else {
            return 0.0;
        }
    }
}
