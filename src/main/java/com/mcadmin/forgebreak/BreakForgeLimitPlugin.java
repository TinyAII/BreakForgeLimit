/*
 * BreakForgeLimit - 突破锻造上限
 * Copyright (c) 2026 TinyAII
 *
 * 本插件基于 MIT 协议开源，详见仓库根目录 LICENSE 文件。
 *
 * 功能：突破铁砧"过于昂贵"上限 —— 原版铁砧费用超过 39 级就红字拒绝锻造，
 *       本插件掀掉这个天花板，价格继续递增，经验足够即可无限锻造。
 *       支持 vanilla（原价只破上限）与 discount（折扣曲线，推荐）两种费用模式。
 *
 * 实现要点：
 *   - PrepareAnvilEvent: setMaximumRepairCost(MAX_VALUE) 掀掉上限；真实费用用 PersistentDataContainer
 *     藏进结果物品，界面只显示 39 绕开"过于昂贵"红字提示。
 *   - InventoryClickEvent: 取真实费用、校验经验、手动扣级 giveExpLevels，创造模式不受影响。
 *   - cost-mode 配置：vanilla = 原版价格；discount = 折扣曲线（越锻越缓、价格仍永递增、永不为负）。
 *
 * 反编译恢复：源码随开发服清理丢失，本仓库源码由已发布 jar 经 CFR 0.152 反编译恢复后做开源清理
 *             （还原中文/补类头/LICENSE），逻辑与原始版一致。
 */
package com.mcadmin.forgebreak;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakForgeLimitPlugin
extends JavaPlugin
implements Listener {
    private NamespacedKey realCostKey;
    private boolean enabled = true;
    private boolean discountMode = true;
    private double discountRate = 0.45;
    private double discountRef = 255.0;

    public void onEnable() {
        this.saveDefaultConfig();
        this.realCostKey = new NamespacedKey((Plugin)this, "real-cost");
        this.reloadSettings();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        String banner = " _____ _                _    ___ ___\n|_   _(_)_ __  _   _   / \\  |_ _|_ _|\n  | | | | '_ \\| | | | / _ \\  | | | |\n  | | | | | | | |_| |/ ___ \\ | | | |\n  |_| |_|_| |_|\\__, /_/   \\_\\___|___|\n               |___/\n";
        banner.lines().forEach(line -> this.getLogger().info((String)line));
        this.getLogger().info("突破锻造上限 v" + this.getDescription().getVersion() + " - TinyAII 出品");
        this.getLogger().info("已突破铁砧过于昂贵限制：价格递增，经验足够即可无限锻造");
    }

    private void reloadSettings() {
        this.reloadConfig();
        this.enabled = this.getConfig().getBoolean("enabled", true);
        String mode = this.getConfig().getString("cost-mode", "discount").trim().toLowerCase();
        this.discountMode = !mode.equals("vanilla");
        this.discountRate = Math.min(0.99, Math.max(0.0, this.getConfig().getDouble("discount-rate", 0.45)));
        this.discountRef = Math.max(1.0, this.getConfig().getDouble("discount-ref", 255.0));
    }

    private int adjustCost(int original) {
        if (!this.discountMode) {
            return original;
        }
        double ratio = Math.min((double)original / this.discountRef, 1.0);
        double factor = 1.0 - this.discountRate * ratio;
        double adjusted = (double)original * factor;
        return Math.max(1, (int)Math.round(adjusted));
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        if (!this.enabled) {
            return;
        }
        AnvilView view = event.getView();
        view.setMaximumRepairCost(Integer.MAX_VALUE);
        int realCost = view.getRepairCost();
        if (realCost <= 0) {
            return;
        }
        int adjusted = this.adjustCost(realCost);
        if (adjusted == realCost && realCost <= 39) {
            return;
        }
        if (adjusted <= 39) {
            view.setRepairCost(adjusted);
            return;
        }
        ItemStack result = event.getResult();
        if (result == null || result.getType() == Material.AIR) {
            return;
        }
        result.editMeta(meta -> meta.getPersistentDataContainer().set(this.realCostKey, PersistentDataType.INTEGER, adjusted));
        event.setResult(result);
        view.setRepairCost(39);
        HumanEntity humanEntity = view.getPlayer();
        if (humanEntity instanceof Player) {
            Player p = (Player)humanEntity;
            p.sendMessage(String.valueOf(ChatColor.GREEN) + "[锻造] 本次锻造需要 " + adjusted + " 级经验");
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onAnvilClick(InventoryClickEvent event) {
        if (!this.enabled) {
            return;
        }
        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }
        if (event.getRawSlot() != 2) {
            return;
        }
        ItemStack result = event.getCurrentItem();
        if (result == null) {
            return;
        }
        ItemMeta meta = result.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(this.realCostKey)) {
            return;
        }
        int realCost = meta.getPersistentDataContainer().getOrDefault(this.realCostKey, PersistentDataType.INTEGER, -1);
        meta.getPersistentDataContainer().remove(this.realCostKey);
        result.setItemMeta(meta);
        event.setCurrentItem(result);
        if (realCost <= 0) {
            return;
        }
        HumanEntity humanEntity = event.getWhoClicked();
        if (!(humanEntity instanceof Player)) {
            return;
        }
        Player player = (Player)humanEntity;
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (player.getLevel() < realCost) {
            event.setCancelled(true);
            player.sendMessage(String.valueOf(ChatColor.GREEN) + "[锻造] 经验不足！需要 " + realCost + " 级经验");
            return;
        }
        int diff = realCost - 39;
        if (diff > 0) {
            player.giveExpLevels(-diff);
        }
        player.sendMessage(String.valueOf(ChatColor.GREEN) + "[锻造] 锻造成功！本次消耗 " + realCost + " 级经验");
    }
}

