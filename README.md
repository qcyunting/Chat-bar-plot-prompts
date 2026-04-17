# ChatBarPlotPrompts - 聊天栏剧本插件（目前千阙云庭仍然使用由星汉编写的插件）

一个为 Paper 1.21.4 服务器设计的 Minecraft 插件，可以在聊天栏向玩家播放剧本对话，支持 YAML 格式配置，可自定义每句话的时长、说话人和文字颜色。

## 📋 功能特性

- ✅ 向特定玩家播放剧本对话
- ✅ YAML 格式配置，易于编写和修改
- ✅ 自定义每句话的显示时长
- ✅ 自定义说话人名称
- ✅ 支持 16 种 Minecraft 颜色
- ✅ 多剧本管理
- ✅ 权限系统支持

## 📦 安装方法

1. 下载插件 jar 文件
2. 将 jar 文件放入服务器的 `plugins` 文件夹
3. 重启服务器
4. 插件会自动生成配置文件目录

## 🎮 命令列表

### 玩家命令

| 命令 | 说明 | 权限 |
|------|------|------|
| `/script list` | 查看所有可用剧本 | `chatbarplotprompts.use` |
| `/script play <剧本名>` | 为自己播放剧本 | `chatbarplotprompts.use` |
| `/script stop` | 停止当前播放 | `chatbarplotprompts.use` |

### 管理员命令

| 命令 | 说明 | 权限 |
|------|------|------|
| `/script play <剧本名> <玩家名>` | 为指定玩家播放剧本 | `chatbarplotprompts.admin` |
| `/scriptadmin reload` | 重新加载所有剧本 | `chatbarplotprompts.admin` |

## 📝 剧本配置

### 剧本格式

创建一个 `.yml` 文件，例如 `my_script.yml`：

```yaml
description: "剧本的描述信息"
lines:
  - speaker: "旁白"
    content: "这是一个示例剧本"
    duration: 3000
    color: "GRAY"
    
  - speaker: "英雄"
    content: "我要开始冒险了！"
    duration: 2000
    color: "GOLD"
    
  - speaker: "村民"
    content: "小心前方的危险！"
    duration: 2500
    color: "GREEN"
```
## 配置参数说明

| 参数 | 类型 | 说明 | 示例 |
|------|------|------|------|
| description | String | 剧本描述，会在 `/script list` 中显示 | "主线剧情第一章" |
| speaker | String | 说话人名称 | "国王", "NPC", "系统" |
| content | String | 说话内容 | "欢迎来到我的王国！" |
| duration | Integer | 该句话显示后到下一句话的等待时间（毫秒） | 2000 (2秒) |
| color | String | 文字颜色 | "GOLD", "RED", "AQUA" |

## 支持的颜色

| 颜色代码 | 颜色名称 | 效果 |
|---------|----------|------|
| BLACK | 黑色 | 黑色文字 |
| DARK_BLUE | 深蓝色 | 深蓝色文字 |
| DARK_GREEN | 深绿色 | 深绿色文字 |
| DARK_AQUA | 深青色 | 深青色文字 |
| DARK_RED | 深红色 | 深红色文字 |
| DARK_PURPLE | 深紫色 | 深紫色文字 |
| GOLD | 金色 | 金色文字 |
| GRAY | 灰色 | 灰色文字 |
| DARK_GRAY | 深灰色 | 深灰色文字 |
| BLUE | 蓝色 | 蓝色文字 |
| GREEN | 绿色 | 绿色文字 |
| AQUA | 青色 | 青色文字 |
| RED | 红色 | 红色文字 |
| LIGHT_PURPLE | 浅紫色 | 浅紫色文字 |
| YELLOW | 黄色 | 黄色文字 |
| WHITE | 白色 | 白色文字 |

也可以使用颜色代码，如 `&a`（绿色）、`&c`（红色）等。

### ❗ 注意事项

1. **剧本文件编码**：请使用 UTF-8 编码保存 YAML 文件
2. **duration 单位**：毫秒（ms），1000 = 1秒
3. **剧本名称**：使用文件名（不含 .yml 扩展名）作为剧本名
4. **颜色代码**：建议使用大写字母的颜色名称
5. **特殊字符**：避免在内容中使用会导致 YAML 解析错误的特殊字符

### 🐛 常见问题

**Q: 修改剧本后没有生效？**

A: 需要使用 `/scriptadmin reload` 重新加载剧本文件。

**Q: 剧本播放时颜色不显示？**

A: 检查颜色名称是否使用大写，或使用标准的 Minecraft 颜色代码。

**Q: 可以为多个玩家同时播放不同的剧本吗？**

A: 可以，每个玩家独立播放，互不干扰。

**Q: 剧本播放中可以停止吗？**

A: 可以使用 `/script stop` 命令随时停止当前剧本。

**Q: 如何删除剧本？**

A: 直接删除 `scripts` 文件夹中的对应 `.yml` 文件，然后执行 `/scriptadmin reload`。
