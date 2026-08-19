# 突破锻造上限 BreakForgeLimit

> 掀掉铁砧"过于昂贵"的天花板，价格递增，经验足够就能一直锻造。MIT 开源，零依赖，Paper 1.20.6~1.21.x。

原版铁砧当费用超过 **39 级**，就红字"**过于昂贵！**"拒绝锻造——本插件掀掉这个上限，价格继续递增（40、63、127、255…），**只要经验够就能一直锻**。价格永远递增、永不为负，天然筛大佬。

- 🔓 **突破 39 级上限**：费用继续涨，永不被"过于昂贵"卡住
- 📈 **价格严格递增**：越锻越贵，萌新经验不够自然锻不起
- 💰 **费用模式可切**（config 一行）：
  - `vanilla`：完全原版价格（只破上限）
  - `discount`：折扣曲线（后期打折，比原版平缓，**推荐**）
- 🧠 **防呆设计**：经验不足提示"需要 X 级"；创造模式不受影响
- 🎨 **品牌标识**：启动打 TinyAII 像素字横幅
- 📖 **MIT 开源**：源码已公开（见仓库 `src/`），欢迎学习/二次开发，保留版权署名即可

---

## 安装

1. 下载 `forge-break-limit-1.0.0.jar`
2. 放入服务器 `plugins/` 目录
3. 重启服务器（或 `/reload`）
4. 打开铁砧，开始锻造

## 配置（`plugins/BreakForgeLimit/config.yml`）

```yaml
enabled: true
cost-mode: discount        # vanilla = 原版价格 | discount = 折扣曲线（推荐）
discount-rate: 0.45        # 最大折扣（0.45 = 狠打 55 折；0.3 更温和）
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

## 实现原理（开源可读）

- `PrepareAnvilEvent`：`setMaximumRepairCost(MAX_VALUE)` 掀掉上限；真实费用用 `PersistentDataContainer` 藏进结果物品，界面只显示 39 绕开"过于昂贵"红字
- `InventoryClickEvent`：取真实费用、校验经验、`giveExpLevels` 手动扣级，创造模式不受影响
- `cost-mode`：`vanilla` 原价只破上限；`discount` 折扣曲线越锻越缓

## 兼容

- 服务端：Paper 1.20.6 – 1.21.x（依赖 `AnvilView` API）
- Java：17 / 21
- 零依赖（无前置插件）

## 开源许可

**MIT License** — Copyright (c) 2026 TinyAII。源码见 `src/main/java/com/mcadmin/forgebreak/`，可自由使用/修改/分发，请保留版权与许可声明。

---

# BreakForgeLimit (English)

Remove the anvil "Too Expensive!" cap. Costs keep rising; forge as long as you have XP. MIT open source, zero deps, Paper 1.20.6~1.21.x.

## Features
- Remove the 39-level cap: costs rise (40, 63, 127, 255...) — never blocked
- Strictly increasing prices: a natural end-game gate
- Switchable cost mode: `vanilla` (original prices) or `discount` (smoother, **recommended**)
- Clear "need X levels" message when XP insufficient; creative unaffected
- TinyAII ASCII banner on startup; **MIT open source**

## Install
jar → `plugins/` → restart

## Config (`plugins/BreakForgeLimit/config.yml`)
```yaml
enabled: true
cost-mode: discount      # vanilla | discount
discount-rate: 0.45
discount-ref: 255
```

## Compatibility
- Paper 1.20.6 – 1.21.x (uses `AnvilView` API)
- Java 17 / 21
- Zero dependencies

## License
**MIT** — Copyright (c) 2026 TinyAII. Source in `src/`. Free to use, modify, distribute; keep the copyright notice.

## Author
TinyAII · MIT 开源 · 零依赖
