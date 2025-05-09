package me.give_me_moneyz.deathmagic;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DeathMagic.MODID)
public class DeathMagic
{
    public static final String MODID = "deathmagic";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DeathMagic(IEventBus modEventBus)
    {
        NeoForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.debug("server starting here!");
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientRenderHandler {
        @SubscribeEvent
        public static void onRender(RenderLevelStageEvent event) {
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
            LOGGER.debug("we rendering up in this jawn");
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (player == null || mc.level == null) return;

            Vec3 cameraPos = event.getCamera().getPosition();
            Vec3 eyePos = player.getEyePosition(mc.getFrameTime());
            Vec3 targetCenter = new Vec3(9.5, 9.5, 9.5); // Center of block (9,9,9)

            // Offset both positions relative to camera
            double startX = eyePos.x - cameraPos.x;
            double startY = eyePos.y - cameraPos.y;
            double startZ = eyePos.z - cameraPos.z;

            double endX = targetCenter.x - cameraPos.x;
            double endY = targetCenter.y - cameraPos.y;
            double endZ = targetCenter.z - cameraPos.z;

            // Set up rendering
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.lineWidth(2.0F);

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();

            buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
            buffer.vertex(startX, startY, startZ).color(255, 0, 0, 255).endVertex(); // Red start
            buffer.vertex(endX, endY, endZ).color(255, 0, 0, 255).endVertex();       // Red end

            tesselator.end();

            RenderSystem.lineWidth(1.0F);
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
        }
    }
}