package com.yutadev31.tweakerextra.client.mixin;

import com.yutadev31.tweakerextra.client.LitematicaRange;
import com.yutadev31.tweakerextra.client.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void tweakerextra$restrictBreaking(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (Configs.RESTRICT_BREAKING.getBooleanValue() && !LitematicaRange.allows(pos)) {
            blocked("message.tweakerextra.breaking_blocked");
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void tweakerextra$restrictPlacing(LocalPlayer player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (!Configs.RESTRICT_PLACING.getBooleanValue() || player.level() == null
                || !(player.getItemInHand(hand).getItem() instanceof BlockItem)) {
            return;
        }

        BlockPos target = hit.getBlockPos();
        BlockState clickedState = player.level().getBlockState(target);
        if (!clickedState.canBeReplaced()) {
            target = target.relative(hit.getDirection());
        }

        if (!LitematicaRange.allows(target)) {
            blocked("message.tweakerextra.placing_blocked");
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }

    private static void blocked(String translationKey) {
        Minecraft minecraft = Minecraft.getInstance();
        if (Configs.SHOW_BLOCKED_MESSAGE.getBooleanValue() && minecraft.player != null) {
            minecraft.player.sendOverlayMessage(Component.translatable(translationKey));
        }
    }
}
