package kval.core

import javax.script.*

object KtsEngine{
    val context = SimpleScriptContext()
    
    val engine by lazy{
        ScriptEngineManager(Thread.currentThread().contextClassLoader).getEngineByExtension("kts")!!
    }
}
