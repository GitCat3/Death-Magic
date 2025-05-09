package me.give_me_moneyz.deathmagic.client.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

public class ClientUtils {
    public static PoseStack setupPoseStack(final RenderLevelStageEvent event) {
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.disablePolygonOffset();
        RenderSystem.defaultBlendFunc();

        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        Vec3 view = Minecraft.getInstance().cameraEntity.getEyePosition(event.getPartialTick());
        stack.translate(-view.x, -view.y, -view.z);
        RenderSystem.applyModelViewMatrix();
        return stack;
    }

    public static void drawLine(PoseStack stack, Vec3 player, Vec3 dest, float width, float r, float g, float b) {

        //        RenderSystem.lineWidth(width);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();

        builder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
        Matrix4f matrix4f = stack.last().pose();
        builder.vertex(matrix4f, (float) player.x, (float) player.y, (float) player.z).color(r, g, b, 0.6f).endVertex();
        builder.vertex(matrix4f, (float) dest.x, (float) dest.y, (float) dest.z).color(r, g, b, 0.6f).endVertex();
        RenderSystem.lineWidth(width);

        tessellator.end();
    }
}
