package com.shuguang.itemraritygui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod("itemraritygui")
public class ItemRarityGUI {

    private final Map<String, Integer> colorMap = new HashMap<>();
    private final Map<String, String> itemColorNameMap = new HashMap<>();
    private boolean messageShown = false;

    public ItemRarityGUI() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ItemRarityConfig.CONFIG);

        // 客户端环境加载后，延迟执行任务
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        // 监听配置重载事件
        MinecraftForge.EVENT_BUS.addListener(this::onConfigReload);
        MinecraftForge.EVENT_BUS.register(this);

        // 初始化颜色映射
        initColorMap();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        loadConfig();
        MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
    }

    private void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == ItemRarityConfig.CONFIG) {
            loadConfig();
        }
    }

    private void loadConfig() {
        itemColorNameMap.clear();
        for (String entry : ItemRarityConfig.ITEM_COLOR_LIST.get()) {
            String[] parts = entry.split(":");
            if (parts.length == 3) {
                String itemId = parts[0] + ":" + parts[1];
                String colorName = parts[2];
                if (colorMap.containsKey(colorName)) {
                    itemColorNameMap.put(itemId, colorName);
                }
            }
        }
    }

    private void initColorMap() {
        colorMap.clear();
        colorMap.put("白", 0x50FFFFFF);
        colorMap.put("绿", 0x5022FF22);
        colorMap.put("蓝", 0x503399FF);
        colorMap.put("紫", 0x50AA22FF);
        colorMap.put("橙", 0x50FFAA00);
        colorMap.put("红", 0x50FF5555);
    }

    @SubscribeEvent
    public void onRenderContainerBackground(ContainerScreenEvent.Render.Background event) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (Slot slot : event.getContainerScreen().getMenu().slots) {
            if (slot.hasItem()) {
                int x = slot.x + event.getContainerScreen().getGuiLeft();
                int y = slot.y + event.getContainerScreen().getGuiTop();
                renderBackgroundAt(event.getGuiGraphics(), slot.getItem(), x, y);
            }
        }

        RenderSystem.disableBlend();
    }

    @SubscribeEvent
    public void onRenderHotbar(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().items.get(i);
            if (!stack.isEmpty()) {
                int x = screenWidth / 2 - 90 + i * 20 + 2;
                int y = screenHeight - 16 - 3;
                renderBackgroundAt(event.getGuiGraphics(), stack, x, y);
            }
        }

        RenderSystem.disableBlend();
    }

    private void renderBackgroundAt(net.minecraft.client.gui.GuiGraphics graphics, ItemStack stack, int x, int y) {
        String itemId = stack.getItem().builtInRegistryHolder().key().location().toString();
        String colorName = itemColorNameMap.get(itemId);
        if (colorName != null && colorMap.containsKey(colorName)) {
            int color = colorMap.get(colorName);
            graphics.fill(x, y, x + 16, y + 16, color);
        }
    }

    // 内部类安全处理Tick事件，确保只执行一次
    private class ClientTickHandler {
        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END && !messageShown) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.sendSystemMessage(
                            net.minecraft.network.chat.Component.literal("§6[曙光征途] §a物品稀有度背景MOD由 §b曙光团队 §a荣誉出品！")
                    );
                    messageShown = true;
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }
        }
    }
}
