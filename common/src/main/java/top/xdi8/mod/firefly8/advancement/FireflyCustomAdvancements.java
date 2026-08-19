package top.xdi8.mod.firefly8.advancement;

import dev.architectury.registry.registries.RegistrySupplier;
import io.github.qwerty770.mcmod.xdi8.registries.InternalRegistryLogWrapper;
import net.minecraft.core.component.predicates.DataComponentPredicate;

import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.componentPredicateType;
import static io.github.qwerty770.mcmod.xdi8.registries.RegistryHelper.criterionTrigger;

@SuppressWarnings("unused")
public class FireflyCustomAdvancements {
    public static final InternalRegistryLogWrapper LOG_WRAPPER = InternalRegistryLogWrapper.firefly8("custom_advancements");

    public static final RegistrySupplier<SimpleDieInXdi8ahoTrigger> DIE_IN_XDI8AHO = criterionTrigger("die_in_xdi8aho", SimpleDieInXdi8ahoTrigger::new);
    public static final RegistrySupplier<DataComponentPredicate.Type<TotemAbilityPredicate>> TOTEM_ABILITY = componentPredicateType("totem_ability", TotemAbilityPredicate.CODEC);
}
