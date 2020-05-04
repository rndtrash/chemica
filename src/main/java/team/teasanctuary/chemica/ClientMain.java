package team.teasanctuary.chemica;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import team.teasanctuary.chemica.blocks.EnergyBoxBlock;
import team.teasanctuary.chemica.blocks.CrusherBlock;
import team.teasanctuary.chemica.gui.BatteryBlockController;
import team.teasanctuary.chemica.gui.CrusherBlockController;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;

public class ClientMain implements ClientModInitializer {

    public static final Identifier CRUSHER_CRUSH_BUTTON_PACKET_ID = new Identifier("chemica", "crusher_crush_button_pressed");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModMain.CRANK_BLOCK, RenderLayer.getCutout());

        ScreenProviderRegistry.INSTANCE.registerFactory(EnergyBoxBlock.ID, (syncId, identifier, player, buf) -> new CottonInventoryScreen<BatteryBlockController>(new BatteryBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
        ScreenProviderRegistry.INSTANCE.registerFactory(CrusherBlock.ID, (syncId, identifier, player, buf) -> new CottonInventoryScreen<CrusherBlockController>(new CrusherBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
    }
}
