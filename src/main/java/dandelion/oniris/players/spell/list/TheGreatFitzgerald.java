package dandelion.oniris.players.spell.list;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import dandelion.oniris.Oniris;
import dandelion.oniris.events.onHitBySpell;
import dandelion.oniris.events.onSpellSense;
import dandelion.oniris.players.PlayerStats;
import dandelion.oniris.players.spell.Spell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.Support;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class TheGreatFitzgerald extends Spell {

    @Override
    public void launch(Player sender, Oniris oniris, PlayerStats data) {
        Gui gui = new Gui(oniris, 2, "Money to Spend");
        OutlinePane pane = new OutlinePane(0,0,9,1);
        OutlinePane optionPane = new OutlinePane(0,1,9,1);
        AtomicInteger money = new AtomicInteger();
        gui.setOnGlobalClick(e -> e.setCancelled(true));

        gui.setOnBottomClick(e -> {
            if(e.getCurrentItem().getType() != Material.AIR){
                if(e.getCurrentItem().getItemMeta().getLore().contains(ChatColor.GREEN+"-Argent-")) {
                    String value = e.getCurrentItem().getItemMeta().getLore().get(1);
                    money.addAndGet(Integer.parseInt(value.split(" ")[2]));
                    e.getCurrentItem().setAmount(e.getCurrentItem().getAmount()-1);

                    gui.setTitle("Money : " + money);
                    gui.update();
                }
            }
        });

        optionPane.addItem(new GuiItem(FonctionUtils.createItem("Confirmer", Material.NETHER_STAR, ItemType.DISPLAY_ITEM, Ranks.COMMON, 1,1).getItemStack(), e -> {
            pane.clear();
            data.addBuff(new TheGreatFitgeraldSupport().addData("moneySpent", money.intValue()), money.longValue()*1000);
            sender.closeInventory();
            sender.sendMessage("Benished by Fitzgerald of " + money);
        }));

        gui.addPane(pane);
        gui.addPane(optionPane);
        gui.show(sender);
    }

    public static class TheGreatFitgeraldSupport extends Support {
        public TheGreatFitgeraldSupport() {
            super("The Great Fitzgerald", Ranks.HERO, "Boost vos pouvoir selon l'argent dépensé");
        }

        @Override
        public void onInteract(PlayerInteractEvent event) {

        }

        @Override
        public void onLaunchSpell(Player player, PlayerStats stats, SpellType s, Spell spell) {

        }

        @Override
        public void onHit(EntityDamageByEntityEvent event, PlayerStats stats, Player p) {

        }

        @Override
        public void onShootArrow(EntityShootBowEvent event) {

        }

        @Override
        public void onHitByEntity(EntityDamageByEntityEvent entityEvent) {

        }

        @Override
        public void onHitBySpell(onHitBySpell e, Player player, PlayerStats stats, Entity caster, SpellType s, Spell spell) {

        }

        @Override
        public void onSpellSense(onSpellSense e) {

        }

        @Override
        public void onTick() {

        }

        @Override
        public void onDie() {

        }
    }
}
