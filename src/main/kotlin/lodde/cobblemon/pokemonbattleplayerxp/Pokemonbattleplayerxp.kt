package lodde.cobblemon.pokemonbattleplayerxp

//import com.cobblemon.mod.common.api.battles.model.actor.ActorType.NPC
//import com.cobblemon.mod.common.api.battles.model.actor.ActorType.WILD
import kotlin.math.ceil
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import net.fabricmc.api.ModInitializer
import net.minecraft.text.Text
import net.minecraft.util.Formatting

import lodde.cobblemon.pokemonbattleplayerxp.config.PokemonbattleplayerxpConfig
import java.util.*
import com.cobblemon.mod.common.util.getPlayer

object Pokemonbattleplayerxp : ModInitializer {
    const val MOD_ID = "pokemon_battle_player_xp"
    private lateinit var pokemonbattleplayerxpConfig: PokemonbattleplayerxpConfig

    override fun onInitialize() {


        pokemonbattleplayerxpConfig = PokemonbattleplayerxpConfig.Builder.load()


        CobblemonEvents.BATTLE_VICTORY.subscribe { event ->
            gainxp(event)
        }
    }


    private fun gainxp(event: BattleVictoryEvent) {
        /*
        //val winners = event.winners
         //println(winners[0].uuid)
         //println(winners[0].type)
         //println(winners[0])
         //val server = net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.getServer()

         if(event.losers[0].type == NPC){
             println("AAA    "+event.losers[0].battle)
             println("AAA    "+event.losers[0].pokemonList[0].entity!!.pokemon.level)
             //event.wild_pokemon_winners

         } else if (event.losers[0].type == WILD){
             println("BBB    "+event.losers[0].battle)
             //var a = event.losers[0].pokemonList[0].entity!!.pokemon.level
             println("BBB    "+ event.losers[0].pokemonList[0].entity!!.pokemon.level)
         } else {
         println("CCC")
         }

         val pokemon_level = event.losers[0].pokemonList[0].entity!!.pokemon.level
         */
        //***********************************************
        //println("AAAAAAAAAAAAAAAAaa         "+event.losers.elementAt(0).pokemonList.elementAt(0).originalPokemon.level);
        var median = 0


        event.losers.elementAt(0).pokemonList.forEach { pk ->
            median += pk.originalPokemon.level;
        }

        val howmanypk = event.losers[0].pokemonList.size
        var mymedianlevel: Float = ((median/howmanypk).toFloat())
        mymedianlevel = ceil(mymedianlevel)
        if(mymedianlevel < 10) {
            mymedianlevel = 10f
        }



        val amount = calculateXp(mymedianlevel.toInt()) * pokemonbattleplayerxpConfig.multiplier.toInt()

        event.winners
            .flatMap { it.getPlayerUUIDs().mapNotNull(UUID::getPlayer) }
            .forEach { player ->

                player.addExperience(amount)
                player.sendMessage(Text.literal("You have been given $amount XP.").formatted(Formatting.GREEN), false)
            }
    }

}

fun calculateXp(level: Int): Int {
    // Ensure level is within the valid range
    if (level < 10 || level > 100) {
        throw IllegalArgumentException("Level must be between 10 and 100")
    }

    // Map levels 10 to 100 to the range 0 to 1
    val normalizedLevel = (level - 10).toDouble() / (100 - 10)

    // Use a quadratic curve: XP = a * (normalizedLevel^2) + b * normalizedLevel + c
    // Coefficients to create a curve between 1 XP (at level 10) and 70 XP (at level 100)
    val a = 70 - 1 // Maximum XP - Minimum XP
    val b = 0
    val c = 5      // Minimum XP

    // Calculate the XP using the quadratic equation
    val xp = a * (normalizedLevel * normalizedLevel) + b * normalizedLevel + c

    return xp.toInt() // Convert to Int for simplicity
}




