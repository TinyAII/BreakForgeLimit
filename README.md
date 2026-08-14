# 突破锻造上限 / BreakForgeLimit

![version](https://img.shields.io/badge/version-1.0.0-brightgreen)
![paper](https://img.shields.io/badge/Paper-1.20.6--1.21.x-blue)

> ⚒️ 突破铁砧"过于昂贵"限制，只要经验足够，就能一直锻造！/ Break the "Too Expensive" anvil limit!

---

<details open>
<summary><b>🇨🇳 中文版（点击收起）</b></summary>

## 功能介绍

**突破锻造上限** 是一款面向 **Paper 生存服** 的铁砧增强插件。原版铁砧费用超过 **39 级**就显示红字"**过于昂贵！**"直接禁止锻造——本插件**掀掉这个天花板**，价格继续递增，**只要经验足够就能一直锻造**！

- 🔓 **突破 39 级上限**：费用 40、63、127、255... 一路涨，永不被"过于昂贵"卡住
- 📈 **价格严格递增**：越锻越贵，天然筛选大佬——小萌新经验不够自然锻不起
- 💰 **费用模式可切换**（config 一行搞定）：
  - `vanilla`：完全原版价格（只突破上限，不调价）
  - `discount`：折扣曲线（后期打折，比原版平缓，**推荐**）
- 🧠 **防呆设计**：经验不足时明确提示"需要 X 级经验"；创造模式不受影响
- 🎨 **品牌标识**：启动时控制台显示 **TinyAII** 像素字

### 🚀 安装

1. 下载 `forge-break-limit-1.0.0.jar`
2. 放入服务器 `plugins/` 目录
3. 重启服务器（或执行 `reload`）
4. 打开铁砧，开始锻造！

### ⚙️ 配置（`plugins/BreakForgeLimit/config.yml`）

```yaml
enabled: true
cost-mode: discount        # vanilla = 原版价格 | discount = 折扣曲线（推荐）
discount-rate: 0.45        # 最大折扣（0.45 = 最狠打 55 折；0.3 更温和）
discount-ref: 255          # 参考值（达到后折扣封顶，价格继续递增）
```

**费用曲线效果**（discount 模式）：

| 锻造次数 | 原版费用 | 新费用 |
|---|---|---|
| 第 5 次 | 31 | 29 |
| 第 6 次 | 63 | 56 |
| 第 7 次 | 127 | 99 |
| 第 8 次 | 255 | 140 |
| 第 9 次 | 511 | 281 |

价格永远递增、永不为负。

### ✅ 兼容性

- 服务端：**Paper 1.20.6 – 1.21.x**（需要 AnvilView API）
- Java：17 / 21
- 无外部依赖

### 👤 作者

**TinyAII** — 我的世界服务器管理员

</details>

---

<details>
<summary><b>🇬🇧 English Version (click to expand)</b></summary>

## Introduction

**BreakForgeLimit** is an anvil enhancement plugin for **Paper survival servers**. Vanilla anvils refuse to work when the cost exceeds **39 levels** ("Too Expensive!") — this plugin removes that cap, so you can keep forging as long as you have enough XP!

- 🔓 **Remove the 39-level cap**: costs keep rising (40, 63, 127, 255...) — never blocked by "Too Expensive"
- 📈 **Strictly increasing prices**: the more you forge, the more it costs — a natural end-game gate
- 💰 **Switchable cost mode** (one line in config):
  - `vanilla`: original vanilla prices (only removes the cap)
  - `discount`: discount curve (cheaper at high tiers, **recommended**)
- 🧠 **Anti-bug design**: clear "need X levels" message when XP is insufficient; creative mode unaffected
- 🎨 **Brand banner**: shows a **TinyAII** ASCII banner on startup

### 🚀 Installation

1. Download `forge-break-limit-1.0.0.jar`
2. Put it into `plugins/`
3. Restart server (or run `reload`)
4. Open an anvil and start forging!

### ⚙️ Config (`plugins/BreakForgeLimit/config.yml`)

```yaml
enabled: true
cost-mode: discount        # vanilla = original prices | discount = discount curve (recommended)
discount-rate: 0.45        # max discount (0.45 = up to 55% off; 0.3 = gentler)
discount-ref: 255          # reference value (discount caps here, prices keep rising)
```

**Discount curve example**:

| Forge # | Vanilla | New |
|---|---|---|
| 5 | 31 | 29 |
| 6 | 63 | 56 |
| 7 | 127 | 99 |
| 8 | 255 | 140 |
| 9 | 511 | 281 |

Prices always rise, never negative.

### ✅ Compatibility

- Server: **Paper 1.20.6 – 1.21.x** (requires AnvilView API)
- Java: 17 / 21
- No external dependencies

### 👤 Author

**TinyAII** — Minecraft Server Administrator

</details>

---

by TinyAII ❤️
