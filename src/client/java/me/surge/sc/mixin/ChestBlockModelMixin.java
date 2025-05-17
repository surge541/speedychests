package me.surge.sc.mixin;

import net.minecraft.client.model.*;
import net.minecraft.client.render.block.entity.model.ChestBlockModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlockModel.class)
public class ChestBlockModelMixin {

    @Redirect(
            method="<init>",
            at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelPart;getChild(Ljava/lang/String;)Lnet/minecraft/client/model/ModelPart;")
    )
    public ModelPart redirectInit(ModelPart instance, String name) {
        return null;
    }

    @Inject(
            method="getSingleTexturedModelData",
            at=@At("HEAD"),
            cancellable=true
    )
    private static void injectGetSingleTexturedModelData(CallbackInfoReturnable<TexturedModelData> cir) {
        ModelData modelData = new ModelData();

        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.BOTTOM, ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F)
                        .uv(0, 0)
                        .cuboid(1.0F, 9.0F, 1.0F, 14.0F, 5.0F, 14.0F)
                        .cuboid(7.0F, 7.0F, 15.0F, 2.0F, 4.0F, 1.0F)
                ,
                ModelTransform.NONE
        );

        cir.setReturnValue(TexturedModelData.of(modelData, 64, 64));
    }

    @Inject(
            method="getDoubleChestRightTexturedBlockData",
            at=@At("HEAD"),
            cancellable=true
    )
    private static void injectGetDoubleChestRightTexturedBlockData(CallbackInfoReturnable<TexturedModelData> cir) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.BOTTOM, ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F)
                        .uv(0, 0)
                        .cuboid(1.0F, 9.0F, 1.0F, 15.0F, 5.0F, 14.0F)
                        .cuboid(15.0F, 7.0F, 15.0F, 1.0F, 4.0F, 1.0F),
                ModelTransform.NONE
        );

        cir.setReturnValue(TexturedModelData.of(modelData, 64, 64));
    }

    @Inject(
            method="getDoubleChestLeftTexturedBlockData",
            at=@At("HEAD"),
            cancellable=true
    )
    private static void injectGetDoubleChestLeftTexturedBlockData(CallbackInfoReturnable<TexturedModelData> cir) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.BOTTOM, ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F)
                        .uv(0, 0)
                        .cuboid(0.0F, 9.0F, 1.0F, 15.0F, 5.0F, 14.0F)
                        .cuboid(0.0F, 7.0F, 15.0F, 1.0F, 4.0F, 1.0F),
                ModelTransform.NONE
        );

        cir.setReturnValue(TexturedModelData.of(modelData, 64, 64));
    }

    @Inject(
            method="setLockAndLidPitch",
            at=@At("HEAD"),
            cancellable=true
    )
    public void injectSetLockAndLidPitch(CallbackInfo ci) {
        ci.cancel();
    }

}
