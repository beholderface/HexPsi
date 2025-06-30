package net.beholderface.hexpsi.pieces;

import net.beholderface.hexpsi.items.TrinketSpellBulletItem;
import vazkii.psi.api.spell.*;

public class PieceFXSuppressor extends SpellPiece {

    public PieceFXSuppressor(Spell spell) {
        super(spell);
    }

    public void addToMetadata(SpellMetadata meta) {
        meta.setFlag(TrinketSpellBulletItem.TAG_SUPPRESS_FX, true);
    }

    public EnumPieceType getPieceType() {
        return EnumPieceType.MODIFIER;
    }

    public Class<?> getEvaluationType() {
        return Void.class;
    }

    public Object evaluate() {
        return null;
    }

    public Object execute(SpellContext context) {
        return null;
    }
}
