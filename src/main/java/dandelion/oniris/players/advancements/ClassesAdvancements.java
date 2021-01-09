package dandelion.oniris.players.advancements;

import eu.endercentral.crazy_advancements.*;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public enum ClassesAdvancements {
    CHOOSECLASS(Material.DIAMOND_SWORD, 1, "{\"text\":\"Première Classe\",\"color\":\"gold\"}", "{\"text\":\"Progressez grace au /progress\",\"color\":\"gold\"}", "textures/block/yellow_concrete.png", false, true, AdvancementVisibility.ALWAYS),

    WARRIORPATH(CHOOSECLASS, Material.DIAMOND_SWORD, 1, "{\"text\":\"Chemin du guerrier\",\"color\":\"gold\"}","{\"text\":\"Bravo vous avez choisit la voie due Guerrier !\",\"color\":\"gold\"}", 1,3, true, false, AdvancementVisibility.HIDDEN),
    CHAMPION(WARRIORPATH, Material.IRON_SWORD, 2, "{\"text\":\"Champion\",\"color\":\"gold\"}","{\"text\":\"Vous êtes desormais assez fort pour devenir un Champion !\",\"color\":\"gold\"}", 3,3, true, false, AdvancementVisibility.PARENT_GRANTED),

    MAGEPATH(CHOOSECLASS, Material.IRON_SHOVEL, 1, "{\"text\":\"Chemin du Magicien\",\"color\":\"gold\"}","{\"text\":\"Bravo vous avez choisit la voie du Magicien !\",\"color\":\"gold\"}", 1,0, true, false, AdvancementVisibility.HIDDEN),
    PRIEST(MAGEPATH, Material.IRON_SHOVEL, 2, "{\"text\":\"Prêtre\",\"color\":\"gold\"}","{\"text\":\"Vous êtes désormais assez pur pour devenir Prêtre !\",\"color\":\"gold\"}", 3,0, true, false, AdvancementVisibility.PARENT_GRANTED),

    ARCHERPATH(CHOOSECLASS, Material.BOW, 1, "{\"text\":\"Chemin de l'Archer\",\"color\":\"gold\"}","{\"text\":\"Bravo vous avez choisit la voie de l'Archer !\",\"color\":\"gold\"}", 1,-3, true, false, AdvancementVisibility.HIDDEN),
    HUNTER(ARCHERPATH, Material.IRON_SWORD, 3, "{\"text\":\"Chasseur\",\"color\":\"gold\"}","{\"text\":\"Vous êtes désormais assez abile pour devenir Chasseur!\",\"color\":\"gold\"}", 3,-3, true, false, AdvancementVisibility.PARENT_GRANTED)




    ;

    private Advancement advancement;
    ClassesAdvancements parent;
    Material icon;
    int requiredCriteria;
    String title, description, background_texture;
    float x, y;
    boolean showToast, displayMessage;
    AdvancementVisibility visibility;

    ClassesAdvancements(Material icon, int requiredCriteria, String title, String description, String background_texture, boolean showToast, boolean displayMessage, AdvancementVisibility visibility) {
        this.parent = null;
        this.icon = icon;
        this.requiredCriteria = requiredCriteria;
        this.title = title;
        this.description = description;
        this.background_texture = background_texture;
        this.x = 0;
        this.y = 0;
        this.showToast = showToast;
        this.displayMessage = displayMessage;
        this.visibility = visibility;
    }

    ClassesAdvancements(ClassesAdvancements parent, Material icon, int requiredCriteria, String title, String description, float x, float y, boolean showToast, boolean displayMessage, AdvancementVisibility visibility) {
        this.parent = parent;
        this.icon = icon;
        this.requiredCriteria = requiredCriteria;
        this.title = title;
        this.description = description;
        this.background_texture = null;
        this.x = x;
        this.y = y;
        this.showToast = showToast;
        this.displayMessage = displayMessage;
        this.visibility = visibility;
    }

    public Advancement getAdvancement() {
        return advancement;
    }

    public ClassesAdvancements getParent() {
        return parent;
    }

    public void setParent(ClassesAdvancements parent) {
        this.parent = parent;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public int getRequiredCriteria() {
        return requiredCriteria;
    }

    public void setRequiredCriteria(int requiredCriteria) {
        this.requiredCriteria = requiredCriteria;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground_texture() {
        return background_texture;
    }

    public void setBackground_texture(String background_texture) {
        this.background_texture = background_texture;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isShowToast() {
        return showToast;
    }

    public void setShowToast(boolean showToast) {
        this.showToast = showToast;
    }

    public boolean isDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(boolean displayMessage) {
        this.displayMessage = displayMessage;
    }

    public AdvancementVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(AdvancementVisibility visibility) {
        this.visibility = visibility;
    }

    static Advancement[] advancements;

    public static void registerAdvancements(AdvancementManager manager) {
        ArrayList<Advancement> advancementsLoaded = new ArrayList<>();
        for(ClassesAdvancements adv : ClassesAdvancements.values()) {
            AdvancementDisplay display = new AdvancementDisplay(adv.getIcon(), new JSONMessage(adv.getTitle()), new JSONMessage(adv.getDescription()), AdvancementDisplay.AdvancementFrame.TASK, adv.showToast, adv.displayMessage, adv.visibility);
            if(adv.getParent() == null) {
                display.setBackgroundTexture(adv.getBackground_texture());
            } else {
                display.setCoordinates(adv.getX(), adv.getY());
            }
            adv.advancement = new Advancement(adv.getParent() == null ? null : adv.getParent().getAdvancement(), new NameKey("classes_advancements", adv.name().toLowerCase()), display);
            adv.advancement.setCriteria(adv.requiredCriteria);
            advancementsLoaded.add(adv.getAdvancement());
        }

        advancements = advancementsLoaded.toArray(new Advancement[advancementsLoaded.size()]);

        for(Player pl : Bukkit.getOnlinePlayers())
            manager.loadProgress(pl, advancements);

        manager.addAdvancement(advancements);

    }

    public static Advancement[] getAdvancements() {
        return advancements;
    }
}
