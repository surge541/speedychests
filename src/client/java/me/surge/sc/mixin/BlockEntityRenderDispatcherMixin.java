package me.surge.sc.mixin;

import me.surge.sc.mixin.accessor.WorldRendererAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin<T> {

    @Inject(
            method="render(Lnet/minecraft/client/render/block/entity/BlockEntityRenderer;Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/math/Vec3d;)V",
            at=@At("HEAD"),
            cancellable = true
    )
    private static <T extends BlockEntity> void injectRender(BlockEntityRenderer<T> renderer, T blockEntity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Vec3d cameraPos, CallbackInfo ci) {
        if (MinecraftClient.getInstance().worldRenderer != null) {
            Frustum frustum = ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum();

            if (frustum != null && !frustum.isVisible(Box.from(Vec3d.of(blockEntity.getPos())))) {
                ci.cancel();
            }
        }
    }

}
