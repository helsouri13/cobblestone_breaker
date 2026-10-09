package helsouri.cobblestone_breaker

import helsouri.cobblestone_breaker.block.CobblestoneBreakerBlock
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries // Import this
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey // Import this
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour // Ensure this is imported
import org.slf4j.LoggerFactory
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.world.item.CreativeModeTabs

object Cobblestone_breaker : ModInitializer {
	const val MOD_ID: String = "cobblestone_breaker"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	// 1. Create the ResourceKey for the block
	private val BLOCK_ID = Identifier.fromNamespaceAndPath(MOD_ID, "cobblestone_breaker")
	private val BLOCK_KEY = ResourceKey.create(Registries.BLOCK, BLOCK_ID)

	// 2. Pass the key to .setId() inside the properties
	val BREAKER_BLOCK: Block = CobblestoneBreakerBlock(
		BlockBehaviour.Properties.of()
			.setId(BLOCK_KEY) // <--- This fixes the crash
			.strength(3.5f)
			.requiresCorrectToolForDrops()
	)

	override fun onInitialize() {
		Registry.register(
			BuiltInRegistries.BLOCK,
			BLOCK_ID, // Reuse the Identifier you already created
			BREAKER_BLOCK
		)

		// 3. Do the same for the Item
		val ITEM_ID = Identifier.fromNamespaceAndPath(MOD_ID, "cobblestone_breaker")
		val ITEM_KEY = ResourceKey.create(Registries.ITEM, ITEM_ID)

		Registry.register(
			BuiltInRegistries.ITEM,
			ITEM_ID,
			BlockItem(BREAKER_BLOCK, Item.Properties().setId(ITEM_KEY)) // <--- This fixes it for the item
		)

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register { entries ->
			entries.accept(BREAKER_BLOCK)
		}

		LOGGER.info("Cobblestone Breaker loaded.")
	}

	fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}