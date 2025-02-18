plugins {
	id("java")
	id("fabric-loom") version("1.4-SNAPSHOT")
	kotlin("jvm") version ("1.8.20")
	id("com.google.devtools.ksp") version "1.8.20-1.0.10"
}

group = property("maven_group")!!
version = property("mod_version")!!

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	maven("https://maven.impactdev.net/repository/development/")
	maven("https://api.modrinth.com/maven")
	maven("https://maven.wispforest.io/releases")
	maven("https://maven.terraformersmc.com/")
	maven("https://maven.wispforest.io")
	maven("https://maven.kosmx.dev/")
}

dependencies {
	minecraft("com.mojang:minecraft:${property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${property("yarn_mappings")}")
	modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

	// Fabric API
	modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

	// Fabric Kotlin
	modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin_version")}")

	// Cobblemon
	modImplementation("com.cobblemon:fabric:${property("cobblemon_version")}")


	modImplementation("io.wispforest:owo-lib:${property("owo_version")}")
	include("io.wispforest:owo-sentinel:${property("owo_version")}")

	//modImplementation("com.terraformersmc:modmenu:${property("modmenu_version")}")

	modImplementation("maven.modrinth:modmenu:${property("modmenu_modrinth_id")}")
	//modRuntimeOnly("maven.modrinth:modmenu:${project.modmenu_modrinth_id}")

	ksp("dev.kosmx.kowoconfig:ksp-owo-config:0.1.0") // Keep it updated




}

tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand(mutableMapOf("version" to project.version))
		}
	}

	jar {
		from("LICENSE")
	}

	compileKotlin {
		kotlinOptions.jvmTarget = "17"
	}
}