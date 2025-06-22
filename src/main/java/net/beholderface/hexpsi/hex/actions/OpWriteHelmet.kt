package net.beholderface.hexpsi.hex.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import net.beholderface.hexpsi.hex.mishap.MishapBadHelmet
import net.beholderface.hexpsi.items.FocusExosuitSensorItem
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import vazkii.psi.api.exosuit.PsiArmorEvent
import vazkii.psi.common.item.armor.ItemPsimetalExosuitHelmet

class OpWriteHelmet : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val toWrite = args[0]
        if (env.castingEntity is ServerPlayer){
            val player = (env.castingEntity as ServerPlayer)
            val inventory = player.inventory
            val helmetStack = inventory.armor[3]
            val stackItem = helmetStack.item
            if (stackItem is ItemPsimetalExosuitHelmet){
                val sensorStack = stackItem.getAttachedSensor(helmetStack)
                val sensorItem = sensorStack.item
                if (sensorItem is FocusExosuitSensorItem){
                    if (sensorItem.canWrite(sensorStack, toWrite)){
                        sensorItem.writeDatum(helmetStack, toWrite)
                        PsiArmorEvent.post(PsiArmorEvent(player, FocusExosuitSensorItem.EVENT_TYPE))
                        return listOf()
                    } else {
                        throw MishapInvalidIota(toWrite, 0, Component.translatable("hexpsi.helmetrequires.${sensorItem.requirementText}"))
                    }
                }
            }
            throw MishapBadHelmet("writeable")
        }
        return listOf()
    }
}