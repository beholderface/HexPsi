package net.beholderface.hexpsi.pieces;

import com.mojang.datafixers.util.Pair;
import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.pieces.selector.PieceSelectorSentinel;
import net.beholderface.hexpsi.pieces.trick.PieceTrickSetSentinel;
import net.minecraft.resources.ResourceLocation;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;
import vazkii.psi.common.spell.base.ModSpellPieces;

import java.util.HashMap;
import java.util.Map;

public class HexPsiPieces {

    //
    public static final Map<Class<? extends SpellPiece>, Pair<ResourceLocation, ResourceLocation>> PIECE_RESLOC_MAP = new HashMap<>();

    public static ModSpellPieces.PieceContainer selectorSentinel;
    public static ModSpellPieces.PieceContainer trickSetSentinel;

    public static void init(){
        selectorSentinel = register(PieceSelectorSentinel.class, "selector_sentinel", "memory_management");
        trickSetSentinel = register(PieceTrickSetSentinel.class, "trick_set_sentinel", "misc_tricks");
    }

    public static ModSpellPieces.PieceContainer register(Class<? extends SpellPiece> clazz, String name, String group) {
        return register(clazz, name, group, false);
    }

    public static ModSpellPieces.PieceContainer register(Class<? extends SpellPiece> clazz, String name, String group, boolean main) {
        ResourceLocation loc = new ResourceLocation(HexPsi.MODID, name);
        PIECE_RESLOC_MAP.put(clazz, new Pair<>(loc, new ResourceLocation(loc.getNamespace(), "spell/" + loc.getPath())));
        PsiAPI.registerSpellPieceAndTexture(loc, clazz);
        PsiAPI.addPieceToGroup(clazz, new ResourceLocation(HexPsi.MODID, group), main);
        return (s) -> SpellPiece.create(clazz, s);
    }
}
