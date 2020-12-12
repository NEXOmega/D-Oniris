package dandelion.oniris.commands.equipemetns;

import dandelion.oniris.Oniris;
import dandelion.oniris.players.spell.Elements;
import dandelion.oniris.players.spell.LocationSpell;
import dandelion.oniris.players.spell.SpellType;
import dandelion.oniris.players.support.SupportType;
import dandelion.oniris.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RPGItemCommand implements TabExecutor {
    Oniris oniris;

    private List<String> lore = new ArrayList<>();

    public RPGItemCommand(Oniris oniris) {
        this.oniris = oniris;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("equip")) {
                    ItemStack item = new ItemStack(Material.matchMaterial(args[1]));
                    ItemMeta meta = item.getItemMeta();
                    String level = ChatColor.translateAlternateColorCodes('&', args[2]);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        sb.append(ChatColor.translateAlternateColorCodes('&', args[i]));
                        sb.append(" ");
                    }
                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("name"), PersistentDataType.STRING, sb.toString());
                    sb.append(level);
                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("level"), PersistentDataType.INTEGER, Integer.parseInt(level.replaceAll("[^0-9]", "")));
                    meta.setDisplayName(sb.toString());

                    lore.clear();
                    lore.add(ChatColor.YELLOW + "-Equipement-");
                    meta.setLore(lore);
                    meta.setUnbreakable(true);

                    item.setItemMeta(meta);

                    p.getInventory().addItem(item);

                }
                else if (args[0].equalsIgnoreCase("attr")) {
                    if (p.getItemOnCursor() == null) return false;
                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();
                    if (args.length >= 3) {
                        if (args.length >= 4) {
                            if (args[1].equalsIgnoreCase("add")) {
                                if (!meta.hasLore()) {
                                    lore.clear();
                                    lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
                                    meta.setLore(lore);
                                } else {
                                    lore = meta.getLore();
                                    if(!lore.contains(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----")) {
                                        lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
                                        meta.setLore(lore);
                                    }

                                }
                                if (args[2].equalsIgnoreCase("atk")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.RED, args[3], "Attaque", true));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("strength"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    item.setItemMeta(meta);

                                }
                                else if (args[2].equalsIgnoreCase("mage")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.DARK_PURPLE, args[3], "Magie", true));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("magie"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    item.setItemMeta(meta);

                                }
                                else if (args[2].equalsIgnoreCase("def")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.LIGHT_PURPLE, args[3], "Defence", true));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("defence"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    item.setItemMeta(meta);

                                }
                                else if (args[2].equalsIgnoreCase("vit")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.YELLOW, args[3], "Vitality", true));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("vitality"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    item.setItemMeta(meta);

                                }
                                else if (args[2].equalsIgnoreCase("spd")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.WHITE, args[3], "Vitesse", true));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("speed"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                                    item.setItemMeta(meta);

                                }
                            } else if (args[1].equalsIgnoreCase("sub")) {
                                if (!meta.hasLore()) {
                                    lore.clear();
                                    lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
                                    meta.setLore(lore);
                                } else {
                                    lore = meta.getLore();
                                    if(!lore.contains(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----")) {
                                        lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Attributs " + ChatColor.AQUA + "----");
                                        meta.setLore(lore);
                                    }
                                }
                                if (args[2].equalsIgnoreCase("atk")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.RED, args[3], "Attaque", false));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("strength"), PersistentDataType.INTEGER, Integer.parseInt(args[3])*(-1));
                                    item.setItemMeta(meta);

                                } else if (args[2].equalsIgnoreCase("mage")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.DARK_PURPLE, args[3], "Magie", false));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("magie"), PersistentDataType.INTEGER, Integer.parseInt(args[3])*(-1));
                                    item.setItemMeta(meta);

                                } else if (args[2].equalsIgnoreCase("def")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.LIGHT_PURPLE, args[3], "Defence", false));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("defence"), PersistentDataType.INTEGER, Integer.parseInt(args[3])*(-1));
                                    item.setItemMeta(meta);

                                } else if (args[2].equalsIgnoreCase("vit")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.YELLOW, args[3], "Vitality", false));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("vitality"), PersistentDataType.INTEGER, Integer.parseInt(args[3])*(-1));
                                    item.setItemMeta(meta);

                                } else if (args[2].equalsIgnoreCase("spd")) {

                                    if (meta.hasLore()) {
                                        lore = meta.getLore();
                                        lore.add(generateAttr(ChatColor.WHITE, args[3], "Vitesse", false));
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("strength"), PersistentDataType.INTEGER, Integer.parseInt(args[3])*(-1));
                                    item.setItemMeta(meta);

                                }
                            }
                        }

                        if (args[1].equalsIgnoreCase("remove")) {
                            lore.clear();
                            lore = meta.getLore();
                            lore.remove(Integer.parseInt(args[2]));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                    if (args[1].equalsIgnoreCase("infos")) {
                        item.getItemMeta().getLore().forEach(l -> {
                            p.sendMessage(l.replaceAll("[^0-9]", ""));
                        });
                    }


                }
                else if (args[0].equalsIgnoreCase("Scroll")) {
                    int arg = 1;
                    ItemStack scroll = new ItemStack(Material.INK_SAC, 1, (byte) 7);
                    ItemMeta meta = scroll.getItemMeta();
                    if (args[arg].startsWith("c:&")) {
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[arg].replace("c:", "")));
                        arg++;
                    }

                    meta.setDisplayName((meta.hasDisplayName() ? meta.getDisplayName() : "") + "Parchemin de sort");
                    if (args[arg].startsWith("n:")) {
                        meta.setDisplayName(meta.getDisplayName() + " : " + ChatColor.translateAlternateColorCodes('&', args[arg]
                                .replace("n:", "")
                                .replace("&", "§")
                                .replace("_", " ")));
                        arg++;
                    }
                    if (!meta.hasLore()) {
                        lore.clear();
                        meta.setLore(lore);
                    }
                    for (int i = arg; i < args.length; i++) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', args[i]));
                    }

                    meta.setLore(lore);
                    scroll.setItemMeta(meta);
                    ((Player) commandSender).getInventory().addItem(scroll);
                }
                else if (args[0].equalsIgnoreCase("Spell")) {
                    if (p.getItemOnCursor() == null) return false;
                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();

                    if (args[1].equalsIgnoreCase("init")) {
                        lore.clear();
                        lore.add(ChatColor.AQUA + "----" + ChatColor.RED + " Sorts " + ChatColor.AQUA + "----");
                    } else {
                        lore = meta.getLore();
                        for (int i = 1; i < args.length; i++) {
                            lore.add(ChatColor.YELLOW + args[i]);
                        }
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                else if(args[0].equalsIgnoreCase("edit")) {
                    if(args[1].equalsIgnoreCase("type")){
                        if(p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                            p.sendMessage(ChatColor.RED + "Vous devez avoir un objet en main !");
                            return false;
                        }
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("weapon"), PersistentDataType.STRING, args[2].toLowerCase());
                        item.setItemMeta(meta);
                        p.sendMessage("Votre arme est désormais un(e) : " + args[2]);
                    }
                    else if(args[1].equalsIgnoreCase("rarity")) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("rarity"), PersistentDataType.STRING, args[2]);
                        meta.setDisplayName(Ranks.valueOf(args[2].toUpperCase()).getChatColor() + ChatColor.stripColor(meta.getDisplayName()));
                        item.setItemMeta(meta);
                        p.sendMessage("Votre arme est désormais de rareté : " + args[2]);
                    }
                    else if(args[1].equalsIgnoreCase("modeldata")) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setCustomModelData(Integer.parseInt(args[2]));
                        item.setItemMeta(meta);
                        p.sendMessage("L'arme a changé de model");
                    }
                    else if(args[1].equalsIgnoreCase("support")) {
                        if(p.getInventory().getItemInMainHand().getType().equals(Material.SHIELD) ||p.getInventory().getItemInMainHand().getType().equals(Material.PAPER)) {
                            ItemStack item = p.getInventory().getItemInMainHand();
                            ItemMeta meta = item.getItemMeta();
                            meta.getPersistentDataContainer().set(NamespacedKey.minecraft("support"), PersistentDataType.STRING, args[2].toUpperCase());
                            item.setItemMeta(meta);
                            p.sendMessage("Votre bouclier a désormais le support : " + args[2]);
                        }
                    }
                    else if(args[1].equalsIgnoreCase("teach")) {
                        if(args[2].equalsIgnoreCase("lore")) {
                            if(args[3].equalsIgnoreCase("support")) {
                                p.getInventory().getItemInMainHand().getItemMeta().getLore().add("Enseignement du support : " + args[4]);
                            } else if (args[3].equalsIgnoreCase("spell")) {
                                p.getInventory().getItemInMainHand().getItemMeta().getLore().add("Enseignement du sort : " + args[4]);
                            }
                        } else {
                            if(args[3].equalsIgnoreCase("support")) {
                                ItemStack item = p.getInventory().getItemInMainHand();
                                ItemMeta meta = item.getItemMeta();
                                meta.getPersistentDataContainer().set(NamespacedKey.minecraft("toteachsupport"), PersistentDataType.STRING, args[4]);
                                item.setItemMeta(meta);
                            } else if (args[3].equalsIgnoreCase("spell")) {
                                ItemStack item = p.getInventory().getItemInMainHand();
                                ItemMeta meta = item.getItemMeta();
                                meta.getPersistentDataContainer().set(NamespacedKey.minecraft("toteachspell"), PersistentDataType.STRING, args[4]);
                                item.setItemMeta(meta);
                            }
                        }
                    }
                    else if(args[1].equalsIgnoreCase("spell")) {
                        if(p.getInventory().getItemInMainHand().getType().equals(Material.SNOWBALL) ) {
                            if(p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() >= 1 && p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() <= 9) {
                                ItemStack itemStack = ((Player)commandSender).getInventory().getItemInMainHand();
                                ItemMeta meta = itemStack.getItemMeta();
                                if(SpellType.valueOf(args[2].toUpperCase()).getSpell().isAssignableFrom(LocationSpell.class)) {
                                    meta.getPersistentDataContainer().set(NamespacedKey.minecraft("spell"), PersistentDataType.STRING, args[2]);
                                    itemStack.setItemMeta(meta);
                                    p.sendMessage("Votre carte a désormais le sort : " + args[2]);
                                } else {
                                    p.sendMessage("Ce sort ne peux pas être envoyé d'une location donc celui-ci ne peux être associé à la carte");
                                }

                            } else {
                                p.sendMessage("Vous devez avoir une carte en main !");
                            }

                        } else {
                            p.sendMessage("Vous devez avoir une carte en main !");
                        }
                    }
                    else if(args[1].equalsIgnoreCase("element")) {
                        if(args[2].equalsIgnoreCase("list")){
                            p.sendMessage(ChatColor.RED + "Erreur, la liste des éléments valables sont : \n" + ChatColor.BLUE + Elements.getElements());
                            return true;
                        }
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("element"), PersistentDataType.STRING, args[2]);
                        item.setItemMeta(meta);
                        p.sendMessage("Votre objet est désormais de l'élément : " + args[2]);
                        if(args.length > 3) {
                            if(args[3].equalsIgnoreCase("show")) {
                                meta.getLore().add("Element : " + args[2]);
                                item.setItemMeta(meta);
                            }
                        }


                    }
                    else if(args[1].equalsIgnoreCase("slot")) {
                         if(p.getInventory().getItemInMainHand().getType().equals(Material.POTION)) {
                             ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                             meta.getPersistentDataContainer().set(NamespacedKey.minecraft("slot"), PersistentDataType.INTEGER, Integer.parseInt(args[2]));
                             p.getInventory().getItemInMainHand().setItemMeta(meta);
                             p.sendMessage(ChatColor.YELLOW + "Vous pouvez desormais ajouter " + args[2] + " Ingredients a votre potion");
                         } else {
                             p.sendMessage(ChatColor.RED + "Vous devez avoir une potion en main");
                         }
                    }
                    else if (args[1].equalsIgnoreCase("imb")) {
                         ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
                         PersistentDataContainer newPersistentDataContainer = meta.getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
                         newPersistentDataContainer.set(NamespacedKey.minecraft("support"), PersistentDataType.STRING, args[2].toUpperCase());
                         newPersistentDataContainer.set(NamespacedKey.minecraft("duration"), PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                         meta.getPersistentDataContainer().set(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER, newPersistentDataContainer);
                         meta.getPersistentDataContainer().set(NamespacedKey.minecraft("requiredlevel"), PersistentDataType.INTEGER, Integer.parseInt(args[4]));
                        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("exp"), PersistentDataType.INTEGER, Integer.parseInt(args[5]));
                         p.getInventory().getItemInMainHand().setItemMeta(meta);
                        System.out.println(meta.getPersistentDataContainer().get(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER));
                        System.out.println(p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("imbsupport"), PersistentDataType.TAG_CONTAINER));
                    }
                    else if(args[1].equalsIgnoreCase("damages")) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("damages"), PersistentDataType.INTEGER, Integer.parseInt(args[2]));
                        item.setItemMeta(meta);
                        p.sendMessage("Votre arme fait désormais " + args[2] + " dégats");
                    }
                }
                else if (args[0].equalsIgnoreCase("give")) {
                    p.getInventory().addItem(ItemUtils.valueOf(args[1].toUpperCase()).getItem().getItemStack());
                }
                else if (args[0].equalsIgnoreCase("item")) {
                    String name = args[1].replace("_"," ");
                    Material material = Material.valueOf(args[2]);
                    ItemType type = ItemType.valueOf(args[3]);
                    Ranks rank = Ranks.valueOf(args[4]);
                    int amount = Integer.parseInt(args[5]);
                    int modelData = Integer.parseInt(args[6]);
                    //TODO Add lore to object
                    DandelionItem dandelionItem = FonctionUtils.createItem(name, material, type, rank, amount,modelData);

                    p.getInventory().addItem(dandelionItem.getItemStack());
                }

            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tab = new ArrayList<>();
        if (args[0].equalsIgnoreCase("scroll")) {
            for (SpellType s : SpellType.values()) {
                if(s.name().startsWith(args[args.length-1]))
                    tab.add(s.name());
            }
            return tab;
        }
        else if(args[0].equalsIgnoreCase("equip")) {
            if(args.length == 2) {
                for(Material m : Material.values())
                    if(m.name().toLowerCase().startsWith(args[args.length-1]))
                        tab.add(m.name());
            } else if(args.length == 3) {
                return Arrays.asList("[0]","["+((Player)sender).getLevel()+"]");
            } else if (args.length == 4) {
                return Arrays.asList("Nom de L'item");
            }
            return tab;
        }
        else if(args[0].equalsIgnoreCase("attr")) {
            if(args.length == 2) {
                return Arrays.asList("add", "sub", "remove", "infos");
            }
            return Arrays.asList("atk", "mage", "def", "vit", "spd");
        }
        else if (args[0].equalsIgnoreCase("spell")) {
            tab.add("init");
            for (SpellType s : SpellType.values()) {
                if(s.getSpell().getName().startsWith(args[args.length-1]))
                    tab.add(s.getSpell().getName());
            }
            return tab;

        }
        else if(args[0].equalsIgnoreCase("edit")) {
            if(args.length == 2) {
                return Arrays.asList("type", "rarity", "modeldata", "support", "teach", "spell", "element", "slot", "imb", "damages");
            } else if(args.length >= 3) {
                if(args[1].equalsIgnoreCase("type")) {
                    for (WeaponsTypes weaponType: WeaponsTypes.values()) {
                        tab.add(weaponType.name().toLowerCase());
                    }
                    return tab;
                }
                else if(args[1].equalsIgnoreCase("rarity")) {
                    for (Ranks r: Ranks.values()) {
                        if(r.name().startsWith(args[args.length-1]))
                            tab.add(r.name());
                    }
                    return tab;
                }
                else if(args[1].equalsIgnoreCase("modeldata")) {
                    return Arrays.asList("0", "1", "2");
                }
                else if(args[1].equalsIgnoreCase("support")) {
                    for (SupportType s: SupportType.values() ) {
                        if(s.getSupport().getName().startsWith(args[args.length-1]))
                            tab.add(s.name());
                    }
                    return tab;
                }
                else if(args[1].equalsIgnoreCase("teach")) {
                    if(args.length == 4) {
                        return Arrays.asList("support", "spell");
                    }
                    if(args.length == 5) {
                        if(args[3].equalsIgnoreCase("support")) {
                            for (SupportType sp: SupportType.values()) {
                                if(sp.getSupport().getName().startsWith(args[args.length-1]))
                                    tab.add(sp.getSupport().getName());
                            }
                            return tab;
                        } else if(args[3].equalsIgnoreCase("spell")) {
                            for (SpellType s : SpellType.values()) {
                                if(s.getSpell().getName().startsWith(args[args.length-1]))
                                        tab.add(s.getSpell().getName());
                            }
                            return tab;
                        }
                    }
                    return Arrays.asList("lore", "nbt");
                }
                else if(args[1].equalsIgnoreCase("spell")) {
                    for (SpellType s : SpellType.values()) {
                        if(s.getSpell().getName().startsWith(args[args.length-1]))
                            tab.add(s.getSpell().getName());
                    }
                    return tab;
                }
                else if(args[1].equalsIgnoreCase("element")) {
                    for (Elements e: Elements.values()) {
                        tab.add(e.name());
                    }
                    return tab;
                }
                else if (args[1].equalsIgnoreCase("slot")) {
                    return Arrays.asList("1", "2", "3");
                }
                else if(args[1].equalsIgnoreCase("imb")) {
                    if(args.length == 3) {
                        for (SupportType s : SupportType.values()) {
                            if(s.getSupport().getName().startsWith(args[args.length-1]))
                                tab.add(s.getSupport().getName());
                        }
                        return tab;
                    } else if(args.length == 4) {
                        return Arrays.asList("Duration", "60", "300", "3600");
                    } else if(args.length == 5) {
                        return Arrays.asList("Level", "5", "10", "30");
                    } else if(args.length == 6) {
                        return Arrays.asList("Exp", "5", "20", "300");
                    }
                } else if(args[1].equalsIgnoreCase("damages")) {
                    return Arrays.asList("Damages", "10", "100", "1000");
                }
            }
        }
        else if(args[0].equalsIgnoreCase("give")) {
            for(ItemUtils i : ItemUtils.values()) {
                if(i.name().toLowerCase().startsWith(args[args.length-1]))
                    tab.add((i.name()));
            }
            return tab;
        }
        else if (args[0].equalsIgnoreCase("item")) {
            if (args.length == 2) {
                return Arrays.asList("name");
            } else if(args.length == 3) {
                for(Material m : Material.values())
                    if(m.name().toLowerCase().startsWith(args[args.length-1]))
                        tab.add(m.name());
            } else if(args.length == 4) {
                for(ItemType type : ItemType.values())
                    if(type.name().toLowerCase().startsWith(args[args.length-1]))
                        tab.add(type.name());
            } else if(args.length == 5) {
                for(Ranks ranks : Ranks.values())
                    if(ranks.name().toLowerCase().startsWith(args[args.length-1]))
                        tab.add(ranks.name());
            } else if(args.length == 6) {
                return Arrays.asList("amount", "1", "8", "16", "32", "64", "128");
            } else if(args.length == 7) {
                return Arrays.asList("modelData", "0", "1", "100");
            }
            return tab;
        }
        return Arrays.asList("scroll", "equip", "attr", "spell", "edit", "testor", "give", "item");
    }

    public static String generateAttr(ChatColor color, String nbr, String attr, boolean add) {
        String operator = (add) ? "+" : "-";

        if (attr.equals("Attaque"))
            return (ChatColor.GRAY + operator + color + " " + nbr + " " + ChatColor.GRAY + "D' " + color + attr);

        return (ChatColor.GRAY + operator + color + " " + nbr + " " + ChatColor.GRAY + "De " + color + attr);
    }
}
