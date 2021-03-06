package team.teasanctuary.chemica.gui;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import team.teasanctuary.chemica.ClientMain;

import java.util.function.Supplier;

public class EnergyBoxController extends CottonCraftingController {
    public EnergyBoxController(int syncId, PlayerInventory playerInventory, BlockContext context) {
        super(null, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(90, 50);

        WLabel label = new WLabel("Battery Block");
        label.setAlignment(Alignment.CENTER);

        root.add(label, 4, 0);

//        WDynamicLabel label2 = new WDynamicLabel(() -> String.valueOf(propertyDelegate.get(0)));
//        label.setAlignment(Alignment.CENTER);
//
//        root.add(label2, 2, 2);

        WBar energyBar = new WBar(ClientMain.ENERGY_BAR_BG_TEXTURE,  ClientMain.ENERGY_BAR_TEXTURE, 0, 1, WBar.Direction.UP);
        energyBar.withTooltip("chemica.tooltip.battery_block.energyTooltip");
        energyBar.setProperties(propertyDelegate);

        root.add(energyBar, 4, 1, 1, 3);

        root.add(new WItemSlot(blockInventory, 0, 1, 1, false, true), 2, 2);
        root.add(new WItemSlot(blockInventory, 1, 1, 1, false, true), 6, 2);

        root.add(createPlayerInventoryPanel(), 0, 5);

        root.validate(this);
    }
}
