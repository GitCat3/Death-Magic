package me.give_me_moneyz.deathmagic.common.network;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;

import static me.give_me_moneyz.deathmagic.DeathMagic.MODID;

public class MyNetwork {
    public static final String PROTOCOL_VERSION = "1";
    public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(MODID, "main");
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(CHANNEL_NAME)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    private static int packetId = 0;

    public static void register() {
        CHANNEL.messageBuilder(EntityDeathDataPacket.class, packetId++)
                .encoder(EntityDeathDataPacket::encode)
                .decoder(EntityDeathDataPacket::decode)
                .consumerMainThread(EntityDeathDataPacket::handle)
                .add();
    }
}
