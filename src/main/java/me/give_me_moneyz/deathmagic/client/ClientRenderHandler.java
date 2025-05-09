package me.give_me_moneyz.deathmagic.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.give_me_moneyz.deathmagic.DeathMagic;
import me.give_me_moneyz.deathmagic.client.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRenderHandler {
    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        DeathMagic.LOGGER.debug("we rendering up in this jawn");
        PoseStack stack = ClientUtils.setupPoseStack(event);
        double rho = 1;
        assert Minecraft.getInstance().player != null && Minecraft.getInstance().cameraEntity != null;
        float theta = (float) ((Minecraft.getInstance().player.getViewYRot(event.getPartialTick()) + 90) * Math.PI / 180);
        float phi = Mth.clamp((float) ((Minecraft.getInstance().player.getViewXRot(event.getPartialTick()) + 90) * Math.PI / 180), 0.0001F, 3.14F);
        Vec3 playervec = Minecraft.getInstance().cameraEntity
                .getEyePosition(event.getPartialTick())
                .add(rho * Mth.sin(phi) * Mth.cos(theta), rho * Mth.cos(phi) - 0.35F, rho * Mth.sin(phi) * Mth.sin(theta));
        ClientUtils.drawLine(stack, playervec, new Vec3(9, 9, 9), 4f, 0F, 0.6F, 1F);
    }
}