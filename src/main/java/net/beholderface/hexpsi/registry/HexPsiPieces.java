package net.beholderface.hexpsi.registry;

import com.mojang.datafixers.util.Pair;
import net.beholderface.hexpsi.HexPsi;
import net.beholderface.hexpsi.pieces.PieceFXSuppressor;
import net.beholderface.hexpsi.pieces.PieceMediaReportSuppressor;
import net.beholderface.hexpsi.pieces.selector.PieceSelectorHelmetVector;
import net.beholderface.hexpsi.pieces.selector.PieceSelectorSentinelPos;
import net.beholderface.hexpsi.pieces.selector.PieceSelectorSentinelTier;
import net.beholderface.hexpsi.pieces.selector.iotareading.*;
import net.beholderface.hexpsi.pieces.trick.PieceTrickSetSentinel;
import net.beholderface.hexpsi.pieces.trick.iotawriting.*;
import net.minecraft.resources.ResourceLocation;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellPiece;
import vazkii.psi.common.spell.base.ModSpellPieces;

import java.util.HashMap;
import java.util.Map;

public class HexPsiPieces {

    //
    public static final Map<Class<? extends SpellPiece>, Pair<ResourceLocation, ResourceLocation>> PIECE_RESLOC_MAP = new HashMap<>();

    public static ModSpellPieces.PieceContainer selectorSentinelPos;
    public static ModSpellPieces.PieceContainer selectorSentinelTier;

    public static ModSpellPieces.PieceContainer selectorReadVector;
    public static ModSpellPieces.PieceContainer selectorReadNumber;
    public static ModSpellPieces.PieceContainer selectorReadEntity;
    public static ModSpellPieces.PieceContainer selectorReadEntities;

    public static ModSpellPieces.PieceContainer selectorHelmetVector;

    public static ModSpellPieces.PieceContainer trickSetSentinel;
    public static ModSpellPieces.PieceContainer trickWriteVec;
    public static ModSpellPieces.PieceContainer trickWriteNum;
    public static ModSpellPieces.PieceContainer trickWriteEntity;
    public static ModSpellPieces.PieceContainer trickWriteEntities;

    public static ModSpellPieces.PieceContainer trickWriteHelmetVector;

    public static ModSpellPieces.PieceContainer modifierHideFX;
    public static ModSpellPieces.PieceContainer modifierHideReport;

    public static void init(){
        selectorSentinelPos = register(PieceSelectorSentinelPos.class, "selector_sentinel_pos", "sentinels");
        selectorSentinelTier = register(PieceSelectorSentinelTier.class, "selector_sentinel_tier", "sentinels");

        selectorReadVector = register(PieceSelectorReadVector.class, "selector_read_vec", "iota_io");
        selectorReadNumber = register(PieceSelectorReadNumber.class, "selector_read_num", "iota_io");
        selectorReadEntity = register(PieceSelectorReadEntity.class, "selector_read_entity", "iota_io");
        selectorReadEntities = register(PieceSelectorReadEntities.class, "selector_read_entities", "iota_io");

        selectorHelmetVector = register(PieceSelectorHelmetVector.class, "selector_helmet_vec", "iota_io");

        trickSetSentinel = register(PieceTrickSetSentinel.class, "trick_set_sentinel", "sentinels");
        trickWriteVec = register(PieceTrickWriteVector.class, "trick_write_iota_vec", "iota_io");
        trickWriteNum = register(PieceTrickWriteNumber.class, "trick_write_iota_num", "iota_io");
        trickWriteEntity = register(PieceTrickWriteEntity.class, "trick_write_iota_entity", "iota_io");
        trickWriteEntities = register(PieceTrickWriteEntities.class, "trick_write_iota_entities", "iota_io");

        trickWriteHelmetVector = register(PieceTrickWriteHelmetVector.class, "trick_write_helmet_vec", "iota_io");

        modifierHideFX = register(PieceFXSuppressor.class, "fx_suppressor", "hex_manipulators");
        modifierHideReport = register(PieceMediaReportSuppressor.class, "media_suppressor", "hex_manipulators");
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
