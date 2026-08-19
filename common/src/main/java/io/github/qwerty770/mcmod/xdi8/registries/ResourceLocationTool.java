package io.github.qwerty770.mcmod.xdi8.registries;

import io.github.qwerty770.mcmod.xdi8.annotation.StableApi;
import net.minecraft.resources.Identifier;

@SuppressWarnings("unused")
@StableApi(since = "3.0.0-beta1")
public class ResourceLocationTool {
    // For compatibility between versions.
    public static Identifier create(String namespace, String path){
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    public static Identifier create(String location){
        return Identifier.parse(location);
    }

    public static Identifier withDefaultNamespace(String location){
        return Identifier.withDefaultNamespace(location);
    }
}
