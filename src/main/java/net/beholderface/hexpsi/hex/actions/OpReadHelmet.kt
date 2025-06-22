package net.beholderface.hexpsi.hex.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import net.beholderface.hexpsi.hex.mishap.MishapBadHelmet
import net.beholderface.hexpsi.items.FocusExosuitSensorItem
import net.minecraft.server.level.ServerPlayer
import vazkii.psi.common.item.armor.ItemPsimetalExosuitHelmet

class OpReadHelmet : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        if (env.castingEntity is ServerPlayer){
            val player = (env.castingEntity as ServerPlayer)
            val inventory = player.inventory
            val helmetStack = inventory.armor[3]
            val stackItem = helmetStack.item
            if (stackItem is ItemPsimetalExosuitHelmet){
                val sensorStack = stackItem.getAttachedSensor(helmetStack)
                val sensorItem = sensorStack.item
                if (sensorItem is FocusExosuitSensorItem){
                    val output = sensorItem.readIota(helmetStack, env.world)
                    if (output != null){
                        return listOf(output)
                    }
                    return listOf(NullIota())
                }
            }
            throw MishapBadHelmet("readable")
        } else {
            throw MishapBadCaster()
        }
    }
}