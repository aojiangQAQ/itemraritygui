package com.yourserver.itemraritygui;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ItemRarityConfig {
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_COLOR_LIST;
    public static ForgeConfigSpec CONFIG;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("general");

        List<String> defaultColors = new ArrayList<>();
        defaultColors.add("minecraft:netherite_sword:绿");
        defaultColors.add("minecraft:diamond_sword:蓝");
        defaultColors.add("minecraft:iron_sword:白");

        ITEM_COLOR_LIST = builder
                .comment("物品ID与颜色对应关系（格式：itemid:颜色名）")
                .defineList("itemColors", defaultColors, entry -> entry instanceof String);

        builder.pop();
        CONFIG = builder.build();
    }
}
