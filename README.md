# ItemRarityGUI

一个为 Minecraft 1.20.1 Forge 核心开发的 MOD，用于根据物品稀有度为物品栏和背包中的物品添加对应颜色的背景。

---

## 🔧 功能特性

- ✅ 根据物品ID配置不同的背景颜色
- ✅ 支持热键栏、存储容器、背包、创造模式显示
- ✅ 支持透明背景
- ✅ 支持通过中文颜色名配置（如 红、蓝、紫 等）
- ✅ 使用 `forge config` 实现动态配置加载
- ✅ 启动后自动显示制作信息（曙光征途 · 曙光团队）

---

## 📁 配置格式示例

```toml
[general]
itemColors = [
    "minecraft:netherite_sword:紫",
    "minecraft:diamond_sword:蓝",
    "minecraft:iron_sword:白",
    "minecraft:emerald:绿",
    "minecraft:golden_apple:橙",
    "minecraft:apple:红"
]
```
## 🚀 作者与贡献
由 曙光团队 开发                
隶属于 Minecraft 服务器项目：曙光征途
联系我们：https://shuguang.space

## 🪪 授权信息

本 MOD 项目代码基于 [MIT License](./LICENSE) 许可。

本项目依赖 Minecraft Forge 项目运行环境，Forge 使用 LGPL 2.1 授权（见 `FORGE_LICENSE.txt`），本 MOD 并不修改或包含 Forge 本体源代码。
