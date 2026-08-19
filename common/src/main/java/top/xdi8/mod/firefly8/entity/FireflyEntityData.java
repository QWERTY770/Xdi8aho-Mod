package top.xdi8.mod.firefly8.entity;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.UUID;

public final class FireflyEntityData {
    public static final long CHARGE_TIME = 24000L;  // 20min
    public static final long OWNER_TIME = 72000L;   // 1h

    public static void saveToTag(CompoundTag tag, FireflyEntity firefly) {
        // Added "Silent", "NoGravity" and "Glowing" tags in Minecraft 1.21+
        tag.putBoolean("NoAi", firefly.isNoAi());
        tag.putBoolean("Silent", firefly.isSilent());
        tag.putBoolean("NoGravity", firefly.isNoGravity());
        tag.putBoolean("Glowing", firefly.hasGlowingTag());
        tag.putBoolean("Invulnerable", firefly.isInvulnerable());
        tag.put("OwnerData", serializeOwners(firefly.getOwnerMap()));
    }

    public static void loadFromTag(FireflyEntity firefly, CompoundTag tag) {
        if (tag.contains("NoAi")) firefly.setNoAi(tag.getBooleanOr("NoAi", false));
        if (tag.contains("Silent")) firefly.setSilent(tag.getBooleanOr("Silent", false));
        if (tag.contains("NoGravity")) firefly.setNoGravity(tag.getBooleanOr("NoGravity", false));
        if (tag.contains("Glowing")) firefly.setGlowingTag(tag.getBooleanOr("Glowing", false));
        if (tag.contains("Invulnerable")) firefly.setInvulnerable(tag.getBooleanOr("Invulnerable", false));
        if (tag.contains("OwnerData"))
            deserializeOwners(firefly.getOwnerMap(), tag.getList("OwnerData").orElse(new ListTag()));
    }

    static ListTag serializeOwners(Object2LongMap<UUID> ownerMap) {
        ListTag root = new ListTag();
        ownerMap.forEach((uuid, outOfBottleTime) -> {
            CompoundTag tag = new CompoundTag();
            root.add(tag);
            tag.putString("OwnerID", uuid.toString());
            tag.putLong("ReleaseTime", outOfBottleTime);
        });
        return root;
    }

    static void serializeOwners(ValueOutput.ValueOutputList root, Object2LongMap<UUID> ownerMap) {
        ownerMap.forEach((uuid, outOfBottleTime) -> {
            ValueOutput entry = root.addChild();
            entry.putString("OwnerID", uuid.toString());
            entry.putLong("ReleaseTime", outOfBottleTime);
        });
    }

    static void deserializeOwners(Object2LongMap<UUID> ownerMap, ListTag root) {
        if (!root.isEmpty() && root.getFirst().getId() == Tag.TAG_COMPOUND) {
            for (Tag t : root) {
                CompoundTag tag = (CompoundTag) t;
                UUID uuid = UUID.fromString(tag.getString("OwnerID").orElse(""));
                long releaseTime = tag.getLongOr("ReleaseTime", 0L);
                ownerMap.put(uuid, releaseTime);
            }
        }
    }

    static void deserializeOwners(Object2LongMap<UUID> ownerMap, ValueInput.ValueInputList root) {
        for (ValueInput entry : root) {
            entry.getString("OwnerID").map(UUID::fromString)
                    .ifPresent(uuid -> ownerMap.put(uuid, entry.getLongOr("ReleaseTime", 0L)));
        }
    }

    static void deleteOutdatedOwners(Object2LongMap<UUID> ownerMap, long currentTime) {
        ownerMap.forEach((uuid, outOfBottleTime) -> {
            if (currentTime - outOfBottleTime > OWNER_TIME) {
                ownerMap.removeLong(uuid);
            }
        });
    }
}
