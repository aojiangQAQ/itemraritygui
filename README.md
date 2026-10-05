# ItemRarityGUI

Minecraft 1.20.1 的 Forge 客户端模组，根据配置的物品 ID，为背包、容器和快捷栏中的物品绘制半透明背景。

作者：**aojiangQAQ（鳌江）**，曙光团队。

## 环境

- Minecraft：`1.20.1`。
- 构建依赖：Forge `1.20.1-47.2.0`，ForgeGradle `6.0.25`。
- 构建使用 JDK 17 与仓库内 Gradle Wrapper `8.2`。
- 只在客户端安装，不适用于独立服务端。

依赖版本由 `build.gradle` 直接指定；`gradle.properties` 中的版本元数据不参与当前构建依赖选择。

## 构建与安装

```powershell
git clone https://github.com/aojiangQAQ/itemraritygui.git
cd itemraritygui
.\gradlew.bat build
```

Linux/macOS 使用 `./gradlew build`。产物为 `build/libs/itemraritygui-1.0.jar`，复制到 Minecraft 客户端实例的 `mods/` 目录后启动 Forge。

开发环境可通过 `.\gradlew.bat runClient` 启动，运行目录为 `run/`。

## 配置

Forge 会生成客户端配置文件 `config/itemraritygui-client.toml`。`general.itemColors` 中每个条目的格式为 `命名空间:物品名称:颜色名`：

```toml
[general]
itemColors = [
    "minecraft:netherite_sword:绿",
    "minecraft:diamond_sword:蓝",
    "minecraft:iron_sword:白",
    "minecraft:golden_apple:橙"
]
```

默认配置包含下界合金剑、钻石剑和铁剑三个条目。模组物品也可以使用自身的命名空间和物品 ID。

支持的颜色名为 `白`、`绿`、`蓝`、`紫`、`橙`、`红`；透明度固定为 `0x50`，不单独提供颜色值或透明度配置。未匹配的物品不添加背景，格式错误或颜色名不支持的条目会被跳过。

## 显示逻辑

- 容器界面：在槽位背景渲染阶段绘制配置的背景颜色。
- 快捷栏：在原版快捷栏绘制之后覆盖半透明颜色。
- 首次进入世界后显示一次作者信息。

修改配置后可重新启动客户端。源码还监听配置重载事件，但不提供游戏内重载命令。

## 源码

- `src/main/java/com/shuguang/itemraritygui/ItemRarityGUI.java`：配置读取、颜色映射和客户端渲染事件。
- `src/main/java/com/shuguang/itemraritygui/ItemRarityConfig.java`：Forge 客户端配置。
- `src/main/resources/META-INF/mods.toml`：模组信息与依赖声明。
- `src/main/resources/team.png`：模组列表图标。

## 反馈与许可

问题和改进建议请提交到 [Issues](https://github.com/aojiangQAQ/itemraritygui/issues)。

项目代码使用 [MIT License](LICENSE)。Forge 的许可和鸣谢分别保留在 [FORGE_LICENSE.txt](FORGE_LICENSE.txt) 与 [CREDITS.txt](CREDITS.txt) 中；`changelog.txt` 为 Forge 随附变更记录，不是本模组的版本记录。
