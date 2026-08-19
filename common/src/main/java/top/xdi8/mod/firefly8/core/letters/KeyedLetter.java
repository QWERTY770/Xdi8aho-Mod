package top.xdi8.mod.firefly8.core.letters;

import net.minecraft.resources.Identifier;

public interface KeyedLetter {
    Identifier id();
    boolean hasLowercase();
    int lowercase();

    boolean hasMiddleCase();
    int middleCase();

    boolean hasUppercase();
    int uppercase();

    default boolean isNull() { return false; }
    static KeyedLetter empty() { return EmptyLetter.INSTANCE; }

    @FunctionalInterface
    interface Provider {
        @javax.annotation.Nonnull
        KeyedLetter letter();
    }
}
