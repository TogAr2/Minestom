package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.network.packet.server.ComponentHoldingServerPacket;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.UnaryOperator;

public class MapDataPacket implements ComponentHoldingServerPacket {

    public int mapId;
    public byte scale;
    public boolean trackingPosition;
    public boolean locked;

    public Icon[] icons;

    public short columns;
    public short rows;
    public byte x;
    public byte z;
    public byte[] data;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(mapId);
        writer.writeByte(scale);
        writer.writeBoolean(trackingPosition);
        writer.writeBoolean(locked);

        if (icons != null && icons.length > 0) {
            writer.writeVarInt(icons.length);
            for (Icon icon : icons) {
                icon.write(writer);
            }
        } else {
            writer.writeVarInt(0);
        }

        writer.writeByte((byte) columns);
        if (columns <= 0) {
            return;
        }

        writer.writeByte((byte) rows);
        writer.writeByte(x);
        writer.writeByte(z);
        if (data != null && data.length > 0) {
            writer.writeVarInt(data.length);
            writer.writeBytes(data);
        } else {
            writer.writeVarInt(0);
        }

    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.MAP_DATA;
    }

    @Override
    public @NotNull Collection<Component> components() {
        if (icons == null || icons.length == 0) {
            return Collections.emptyList();
        } else {
            List<Component> components = new ArrayList<>();
            for (Icon icon : icons) {
                components.add(icon.displayName);
            }
            return components;
        }
    }

    @Override
    public @NotNull ServerPacket copyWithOperator(@NotNull UnaryOperator<Component> operator) {
        if (this.icons == null || this.icons.length == 0) {
            return this;
        } else {
            MapDataPacket packet = new MapDataPacket();
            packet.mapId = this.mapId;
            packet.scale = this.scale;
            packet.trackingPosition = this.trackingPosition;
            packet.locked = this.locked;
            packet.columns = this.columns;
            packet.rows = this.rows;
            packet.x = this.x;
            packet.z = this.z;
            packet.data = this.data;

            packet.icons = Arrays.copyOf(this.icons, this.icons.length);
            for (Icon icon : packet.icons) {
                icon.displayName = operator.apply(icon.displayName);
            }

            return packet;
        }
    }

    public static class Icon {
        public int type;
        public byte x, z;
        public byte direction;
        public Component displayName;

        private void write(BinaryWriter writer) {
            writer.writeVarInt(type);
            writer.writeByte(x);
            writer.writeByte(z);
            writer.writeByte(direction);

            final boolean hasDisplayName = displayName != null;
            writer.writeBoolean(hasDisplayName);
            if (hasDisplayName) {
                writer.writeComponent(displayName);
            }
        }

    }

}