package dev.kikugie.elytratrims.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import dev.kikugie.elytratrims.ElytraTrimsMod;
import dev.kikugie.elytratrims.config.ConfigState.RenderMode;
import dev.kikugie.elytratrims.config.ConfigState.RenderType;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.Collection;

public class YaclConfig {
    public static Screen createGui(Screen parent) {
        ConfigState config = ElytraTrimsMod.getConfigState();
        return YetAnotherConfigLib.createBuilder()
                .title(ConfigState.title)
                .category(ConfigCategory.createBuilder()
                        .name(ConfigState.category)
                        .group(OptionGroup.createBuilder()
                                .name(ConfigState.renderGroup)
                                .options(allOptions())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(ConfigState.miscGroup)
                                .option(Option.<Boolean>createBuilder()
                                        .name(config.misc.lockDefaultPack.getName())
                                        .description(OptionDescription.of(config.misc.lockDefaultPack.getTooltip()))
                                        .binding(true,
                                                () -> config.misc.lockDefaultPack.value,
                                                (value) -> config.misc.lockDefaultPack.value = value)
                                        .controller(TickBoxControllerBuilder::create)
                                        .flag(OptionFlag.GAME_RESTART)
                                        .build())
                                .build())
                        .build())
                .save(ElytraTrimsMod.getConfigState()::save)
                .build()
                .generateScreen(parent);
    }

    private static Collection<Option<RenderMode>> allOptions() {
        ArrayList<Option<RenderMode>> options = new ArrayList<>(RenderType.values().length);
        for (RenderType type : RenderType.values()) {
            options.add(optionFor(type));
        }
        return options;
    }

    private static Option<RenderMode> optionFor(RenderType type) {
        return Option.<RenderMode>createBuilder()
                .name(type.getName())
                .description(OptionDescription.of(type.getTooltip()))
                .binding(RenderMode.ALL,
                        () -> ElytraTrimsMod.getConfigState().getFor(type),
                        mode -> ElytraTrimsMod.getConfigState().setFor(type, mode))
                .customController(opt -> new EnumController<>(opt, ConfigState.RenderMode::getName, ConfigState.RenderMode.values()))
                .build();
    }
}
