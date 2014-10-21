package powercrystals.powerconverters.power.railcraft;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.helper.ConfigurationHelper;
import powercrystals.powerconverters.init.PowerSystems;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;

public class TileEntityRailCraftConsumer extends TileEntityEnergyConsumer<IFluidHandler> implements IFluidHandler {
	private FluidTank _steamTank;
	private double _mBLastTick;

	public TileEntityRailCraftConsumer() {
		super(PowerSystems.powerSystemSteam, 0, IFluidHandler.class);
		_steamTank = new FluidTank(1 * FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (_steamTank != null && _steamTank.getFluid() != null) {
			double amount = Math.min(_steamTank.getFluid().amount, ConfigurationHelper.throttleSteamConsumer.getInt());
			double energy = amount * PowerSystems.powerSystemSteam.getInternalEnergyPerInput();
			energy = storeEnergy(energy);
			double toDrain = amount - (energy / PowerSystems.powerSystemSteam.getInternalEnergyPerInput());
			_steamTank.drain((int)toDrain, true);
			_mBLastTick = toDrain;
		} else {
			_mBLastTick = 0;
		}
	}

	@Override
	public int getVoltageIndex() {
		return 0;
	}

	@Override
	public double getInputRate() {
		return _mBLastTick;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || resource.fluidID != PowerConverterCore.steamId)
			return 0;
		return _steamTank.fill(resource, doFill);

	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && fluid.getID() == PowerConverterCore.steamId;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { _steamTank.getInfo() };
	}
}