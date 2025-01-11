package lodde.cobblemon.pokemonbattleplayerxp.config


import com.google.gson.GsonBuilder
import lodde.cobblemon.pokemonbattleplayerxp.Pokemonbattleplayerxp
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

class PokemonbattleplayerxpConfig {
	val multiplier = 1.0

	class Builder {
		companion object {
			fun load() : PokemonbattleplayerxpConfig {
				val gson = GsonBuilder()
						.disableHtmlEscaping()
						.setPrettyPrinting()
						.create()

				var config = PokemonbattleplayerxpConfig()
				val configFile = File("config/${Pokemonbattleplayerxp.MOD_ID}.json")
				configFile.parentFile.mkdirs()
//CaptureXP
				if (configFile.exists()) {
					try {
						val fileReader = FileReader(configFile)
						config = gson.fromJson(fileReader, PokemonbattleplayerxpConfig::class.java)
						fileReader.close()
					} catch (e: Exception) {
						println("Error reading config file")
					}
				}

				val pw = PrintWriter(configFile)
				gson.toJson(config, pw)
				pw.close()

				return config
			}
		}
	}
}