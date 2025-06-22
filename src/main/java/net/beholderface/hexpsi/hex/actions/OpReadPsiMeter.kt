package net.beholderface.hexpsi.hex.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import vazkii.psi.common.core.handler.PlayerDataHandler

class OpReadPsiMeter : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        if (env.caster == null){
            throw MishapBadCaster()
        }
        val data = PlayerDataHandler.get(env.caster)
        return listOf(DoubleIota(data.availablePsi.toDouble()))
    }
}