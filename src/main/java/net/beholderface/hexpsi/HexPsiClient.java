package net.beholderface.hexpsi;

import net.beholderface.hexpsi.registry.HexPsiPieces;
import net.beholderface.hexpsi.pieces.selector.PieceSelectorSentinelPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.psi.api.ClientPsiAPI;
import vazkii.psi.api.spell.SpellPiece;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HexPsiClient {

    @SubscribeEvent
    public static void init(RegisterEvent event){
        registerPieceTexture(PieceSelectorSentinelPos.class);
    }

    private static void registerPieceTexture(Class<? extends SpellPiece> piece){
        var map = HexPsiPieces.PIECE_RESLOC_MAP;
        ClientPsiAPI.registerPieceTexture(map.get(piece).getFirst(), map.get(piece).getSecond());
    }
}
