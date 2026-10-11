package helsouri.cobblestone_breaker.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.redstone.Orientation

class CobblestoneBreakerBlock(settings: Properties) : Block(settings) {

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        block: Block,
        orientation: Orientation?,
        movedByPiston: Boolean
    ) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston)

        if (!level.isClientSide) {
            if (level.hasNeighborSignal(pos)) {
                for (direction in Direction.entries) {
                    val target = pos.relative(direction)
                    if (level.getBlockState(target).`is`(Blocks.COBBLESTONE)) {
                        level.destroyBlock(target, true)
                    }
                }
            }
        }
    }
}