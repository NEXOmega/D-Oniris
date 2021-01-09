package dandelion.oniris.players.spell;

import dandelion.oniris.players.spell.list.*;
import dandelion.oniris.utils.FonctionUtils;
import dandelion.oniris.utils.ItemType;
import dandelion.oniris.utils.ItemUtils;
import dandelion.oniris.utils.Ranks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static dandelion.oniris.utils.Ranks.*;

public enum SpellType {
    DISTORTION( Distortion.class,1,0,"distortion",300*1000, SUPERRARE,Elements.MAGIC,FonctionUtils.createItem("Sablier", Material.SAND, ItemType.DISPLAY_ITEM, SUPERRARE, 1,1).getItemStack(), Collections.singleton("Teleporte les entités a leurs positions initiale a l'activation du sort")),
    EYETELEPORT( EyeTeleport.class,15,0,"eyeteleport",30*1000, UNCOMMON,Elements.MAGIC, FonctionUtils.createItem("Eye Teleport", Material.ENDER_PEARL, ItemType.DISPLAY_ITEM, UNCOMMON,1,175).getItemStack(), Collections.singleton(ChatColor.GREEN+"Vous teleporte la ou vous regardez")),
    FIREBALL( Fireball.class,1,0,"fireball",5*1000, COMMON,Elements.FIRE, FonctionUtils.createItem("Fireball", Material.FIRE_CHARGE, ItemType.DISPLAY_ITEM, UNCOMMON,1,109).getItemStack(), Collections.singleton(ChatColor.GREEN+"Crée une boule de feu")),
    FIREWORK( Firework.class,1,0,"firework",15*1000, COMMON,Elements.MAGIC, FonctionUtils.createItem("Firework", Material.FIREWORK_ROCKET, ItemType.DISPLAY_ITEM, UNCOMMON,1,221).getItemStack(), Collections.singleton(ChatColor.GREEN+"Permet de lancer une fusée")),
    HEAL( Heal.class,1,0,"heal",60*1000, COMMON, Elements.WHITE, FonctionUtils.createItem("Heal", Material.APPLE, ItemType.DISPLAY_ITEM, COMMON,1,182).getItemStack(), Collections.singleton("Permet de se soigner")),
    JUDGEMENT( Judgement.class,1,0,"judgement",60*1000, RARE, Elements.ARCANE, FonctionUtils.createItem("Hoe du Jugement", Material.DIAMOND_HOE, ItemType.DISPLAY_ITEM, RARE, 1,63).getItemStack(), Collections.singleton("ChatColor.GREEN+\"Divise la vie des monstres par 0.3\"")),
    PORTALSPELL( PortalSpell.class,1,0,"portal",5*1000, RARE, Elements.MAGIC, FonctionUtils.createItem("Portail", Material.NETHER_PORTAL, ItemType.DISPLAY_ITEM, RARE, 1,179).getItemStack(), Collections.singleton(ChatColor.GREEN + "Permet d'ouvrir le menu des portails.")),
    PROPULSE( Propulse.class,1,0,"propulse",7*1000, RARE, Elements.MAGIC, FonctionUtils.createItem("Rocket Magique", Material.FIREWORK_ROCKET, ItemType.DISPLAY_ITEM, RARE, 1,431).getItemStack(), Collections.singleton("Permet de vous envoler")),
    LEAP( Leap.class,1,0,"leap",5*1000, COMMON, Elements.PHYSIC, FonctionUtils.createItem("Pied de Lapin-Garou", Material.RABBIT_FOOT, ItemType.DISPLAY_ITEM, COMMON,1,2).getItemStack(), Collections.singleton(ChatColor.GREEN + "Permet de faire un grand bon")),
    BERSERKMODE( BerserkMode.class,15,0, "berserkmode", 300*1000, UNCOMMON, Elements.PHYSIC,FonctionUtils.createItem("Eau de vie", Material.HONEY_BOTTLE, ItemType.DISPLAY_ITEM, UNCOMMON,1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Vous faites entrer en mode Berserk pour 30 secondes")),
    ZONEHEAL( ZoneHeal.class,15,0,"zoneheal",120*1000, UNCOMMON, Elements.WHITE, FonctionUtils.createItem("Pomme magique", Material.GOLDEN_APPLE, ItemType.DISPLAY_ITEM, UNCOMMON, 1,1).getItemStack(), Collections.singleton("Permet de soigner les joueurs autour de vous")),
    VISION( Vision.class,1,0,"vision",5000, RARE, Elements.MAGIC, FonctionUtils.createItem("3eme oeil", Material.ENDER_EYE, ItemType.DISPLAY_ITEM, RARE, 1,67).getItemStack(), Collections.singleton(ChatColor.GREEN+"Permet de voir la vie des entités aux alentours")),
    TIMEBREAKER( TimeBreaker.class,50,0, "timebreaker", 300*1000, HERO, Elements.ARCANE, FonctionUtils.createItem("Brise-Temps", Material.CLOCK, ItemType.DISPLAY_ITEM, HERO, 1,1).getItemStack(), Arrays.asList("§eCe sort complexe permet de tordre l'espace", "Sa puissance depend du niveau magique de l'utilisateur","§eSi celle ci est trop faible elle sera fortement diminué",ChatColor.GREEN+"Ralentis vos ennemis et accelere votre vitesse selon le nombre de monstre" )),
    TEMPORALREGEN( TemporalRegeneration.class,1,0,"temporalregen",900*1000, UNOBTAINABLE, Elements.ARCANE, FonctionUtils.createItem("Montre de Chronos", Material.CLOCK, ItemType.DISPLAY_ITEM, UNOBTAINABLE, 1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Permet de détecter les joueurs aux alentours")),
    DESPAIRAURA( DespairAura.class,1,0,"despairaura", 60*1000, SUPERRARE, Elements.BLACK,FonctionUtils.createItem("Maskpeur", Material.PUMPKIN, ItemType.DISPLAY_ITEM, SUPERRARE, 1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Tetanise les joueurs au alentours")),
    ARROWRAIN( ArrowRain.class,1,0, "arrowrain", 5*1000, COMMON, Elements.PHYSIC,FonctionUtils.createItem("Maelstorm", Material.BOW, ItemType.DISPLAY_ITEM, COMMON,1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Crée un déluge de flèche a un endroit")),
    CAMOSKIN( CamoSkin.class,15,0, "camoskin", 60*1000, UNCOMMON,Elements.PHYSIC,FonctionUtils.createItem("Cuir cameleon", Material.RABBIT_HIDE, ItemType.DISPLAY_ITEM, UNCOMMON, 1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Vous rend invisible pour 30 secondes")),
    SOULHUNT( SoulHunt.class,15,0, "soulhunt", 150*1000, RARE, Elements.PHYSIC, FonctionUtils.createItem("Flair du chasseur", Material.SKELETON_SKULL, ItemType.DISPLAY_ITEM, RARE, 1,1).getItemStack(), Collections.singleton(ChatColor.GREEN+"Permet de détecter les joueurs aux alentours")),
    FORCEDREST( ForcedRest.class, 25,0, "forcedrest", 120*1000, SUPERRARE, Elements.SOUND, FonctionUtils.createItem("Chanson du grand doudou", Material.MUSIC_DISC_11, ItemType.DISPLAY_ITEM, SUPERRARE,1,1).getItemStack(), Collections.singleton(ChatColor.GREEN + "Endort de force les joueurs aux alentours")),
    LOCATE(Locate.class, 0, 0, "Locate", 0, RARE,Elements.MAGIC ,FonctionUtils.createItem("Compas Magique", Material.COMPASS, ItemType.DISPLAY_ITEM, RARE,1,1).getItemStack(), Collections.singleton("Permet de savoir la localisation d'un joueur")),
    INSPECT(Inspect.class ,0,0, "Inspect", 60*1000, SUPERRARE, Elements.MAGIC, FonctionUtils.createItem("Inspect", Material.GLASS, ItemType.DISPLAY_ITEM, COMMON, 1,1).getItemStack(),Collections.singleton("Permet de récolter des informations sur un joueur")),
    THEGREATFITZGERALD(TheGreatFitzgerald.class,50,0,"The Great Fitzgerald", 900*1000, HERO, Elements.ARCANE, ItemUtils.GOLD_COIN.getItem().getItemStack(), Collections.singleton("Vous donne des bonus selon l'argent dépensé")),
    THEMAGICIEN(TheMagician.class,30,0, "The Magician", 900*1000, HERO, Elements.MAGIC, FonctionUtils.createItem("Cape du mage", Material.ELYTRA, ItemType.DISPLAY_ITEM, HERO, 1,1).getItemStack(), Collections.singleton("Vous protege des sort que vous vous etes deja pris")),
    PORTALTRAP(PortalTrap.class, 25,0,"Piege du Fourmilion", 90*1000, HERO, Elements.MAGIC, FonctionUtils.createItem("Piege du fourmilion", Material.MINECART, ItemType.DISPLAY_ITEM, HERO, 1,1).getItemStack(), Collections.singleton("Attire tous les joueurs vers un autre, après le temps imparti")),
    TESTSPELL(TestSpell.class, 0,0,"Test Spell", 0, UNOBTAINABLE, Elements.ARCANE, FonctionUtils.createItem("Sort de Test", Material.DEBUG_STICK, ItemType.ADMIN_TOOL, UNOBTAINABLE, 1,1).getItemStack(), Collections.singleton("Sort de test pour les admin"));

    Class<? extends Spell> spell;
    int level;
    int tier = 1;
    String name;
    long delay;
    Ranks rank;
    Elements elements;
    ItemStack itemStack;
    ArrayList<String> lore;



    SpellType(Class<? extends Spell> spell, int level, int tier, String name, long delay, Ranks rank, Elements elements, ItemStack itemStack, Set<String> lore) {
        this.spell = spell;
        this.level = level;
        this.name = name;
        this.delay = delay;
        this.rank = rank;
        this.tier = tier;
        this.itemStack = itemStack;
        this.lore = new ArrayList<>(lore);
        this.elements = elements;
    }

    SpellType(Class<? extends Spell> spell, int level, int tier, String name, long delay, Ranks rank, Elements elements, ItemStack itemStack, List<String> lore) {
        this.spell = spell;
        this.level = level;
        this.name = name;
        this.delay = delay;
        this.rank = rank;
        this.tier = tier;
        this.itemStack = itemStack;
        this.lore = new ArrayList<>(lore);
        this.elements = elements;
    }

    public Class<? extends Spell> getSpell() {
        return spell;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public long getDelay() {
        return delay;
    }

    public Ranks getRank() {
        return rank;
    }

    public int getTier() {
        return tier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public Elements getElements() {
        return elements;
    }
}
