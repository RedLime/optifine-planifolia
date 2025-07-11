package dev.tildejustin.planifolia.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.option.GameOption;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.List;

@Mixin(VideoOptionsScreen.class)
public abstract class VideoOptionsScreenMixin extends Screen {
    private static GameOption[] field_1298 = new GameOption[]{
            GameOption.GRAPHICS,
            GameOption.RENDER_DISTANCE,
            GameOption.AMBIENT_OCCLUSION,
            GameOption.FRAMERATE_LIMIT,
            GameOption.ANAGLYPH,
            GameOption.VIEW_BOBBING,
            GameOption.GUI_SCALE,
            GameOption.ADVANCED_OPENGL,
            GameOption.GAMMA,
            GameOption.RENDER_CLOUDS,
            GameOption.PARTICLES,
            GameOption.USE_FULLSCREEN,
            GameOption.ENABLE_VSYNC,
            GameOption.MIPMAP_LEVELS,
            GameOption.ANISOTROPIC_FILTERING
    };

    @Dynamic
    @WrapWithCondition(
            method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=of.options.quality"), to = @At(value = "CONSTANT", args = "stringValue=gui.done"))
    )
    private boolean stopOptiFineButtons(List<?> instance, Object e) {
        return false;
    }

    @Dynamic
    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionButtonWidget;<init>(IIILjava/lang/String;)V", ordinal = 0), index = 0)
    private int changeId(int original) {
        return 212;
    }

    @Dynamic
    @ModifyConstant(method = "init", constant = @Constant(stringValue = "of.options.shaders"))
    private String changeId(String original) {
        return "of.options.performance";
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/VideoOptionsScreen;renderBackground()V"))
    public void redirectRenderBackground(VideoOptionsScreen instance) {
        this.renderDirtBackground(0);
    }
}
