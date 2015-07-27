package covers1624.powerconverters.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import covers1624.powerconverters.client.render.TileUniversalConduitRender;
import covers1624.powerconverters.tile.main.TileEnergyConduit;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ProxyClient implements IPCProxy {

	@Override
	public void initRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyConduit.class, new TileUniversalConduitRender());
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}
