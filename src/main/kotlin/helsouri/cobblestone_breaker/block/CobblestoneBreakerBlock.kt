package helsouri.cobblestone_breaker.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class CobblestoneBreakerBlock(settings: Properties) : Block(settings) {

    override fun useWithoutItem(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hit: BlockHitResult
    ): InteractionResult {
        if (!world.isClientSide) {
            for (direction in Direction.entries) {
                val target = pos.relative(direction)
                if (world.getBlockState(target).`is`(Blocks.COBBLESTONE)) {
                    world.destroyBlock(target, false)
                }
            }
        }
        return InteractionResult.SUCCESS
    }
}