package kval

import arc.*
import arc.util.*
import mindustry.*
import mindustry.content.*
import mindustry.game.EventType.*
import mindustry.gen.*
import mindustry.mod.*
import mindustry.net.Administration.*
import mindustry.world.blocks.storage.*

import kval.core.*

open class KtsEvalPlugin() : Plugin(){
    
    override fun registerServerCommands(handler: CommandHandler){
        handler.<Player>register("kts", "<code...>", "Execute KotlinScript code."){ args, player ->
            if(player.admin){
                val result = try{
                    KtsEngine.engine.eval(args[0], KtsEngine.context).let{
                        when(it){
                            is Deferred<*> -> it.await()
                            is Job -> it.join()
                            else -> it
                        }
                    }
                }catch(e: Throwable){
                    (e.cause ?: e).let{
                        it.toString()
                    }
                }
                
                player.sendMessage("> $result")
            }else{
                player.sendMessage("> [red]You are not an admin.[]")
            }
        }
    }
}
