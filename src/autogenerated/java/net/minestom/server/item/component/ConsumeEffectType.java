package net.minestom.server.item.component;

import net.kyori.adventure.key.Key;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.registry.StaticProtocolObject;
import net.minestom.server.utils.nbt.BinaryTagSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * AUTOGENERATED by GenericEnumGenerator
 */
enum ConsumeEffectType implements StaticProtocolObject {
    APPLY_EFFECTS(Key.key("minecraft:apply_effects")),

    REMOVE_EFFECTS(Key.key("minecraft:remove_effects")),

    CLEAR_ALL_EFFECTS(Key.key("minecraft:clear_all_effects")),

    TELEPORT_RANDOMLY(Key.key("minecraft:teleport_randomly")),

    PLAY_SOUND(Key.key("minecraft:play_sound"));

    public static final NetworkBuffer.Type<ConsumeEffectType> NETWORK_TYPE = NetworkBuffer.Enum(ConsumeEffectType.class);

    public static final BinaryTagSerializer<ConsumeEffectType> NBT_TYPE = BinaryTagSerializer.fromEnumKeyed(ConsumeEffectType.class);

    private final Key key;

    ConsumeEffectType(@NotNull Key key) {
        this.key = key;
    }

    @NotNull
    @Override
    public Key key() {
        return this.key;
    }

    @Override
    public int id() {
        return this.ordinal();
    }
}
