/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Nov 24, 2013, 6:40:49 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.transvector;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTransvector extends TileEntity {

	private static final String TAG_X_TARGET = "xt";
	private static final String TAG_Y_TARGET = "yt";
	private static final String TAG_Z_TARGET = "zt";
	private static final String TAG_CHEATY_MODE = "cheatyMode";
	private static final String TAG_CAMO = "camo";
	private static final String TAG_CAMO_META = "camoMeta";

	public int camo;
	public int camoMeta;

	public int x = 0, y = -1, z = 0;
	private boolean cheaty;

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);

		writeCustomNBT(par1nbtTagCompound);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);

		readCustomNBT(par1nbtTagCompound);
	}

	public void writeCustomNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_X_TARGET, x);
		cmp.setInteger(TAG_Y_TARGET, y);
		cmp.setInteger(TAG_Z_TARGET, z);
		cmp.setBoolean(TAG_CHEATY_MODE, cheaty);
		cmp.setInteger(TAG_CAMO, camo);
		cmp.setInteger(TAG_CAMO_META, camoMeta);
	}

	public void readCustomNBT(NBTTagCompound cmp) {
		x = cmp.getInteger(TAG_X_TARGET);
		y = cmp.getInteger(TAG_Y_TARGET);
		z = cmp.getInteger(TAG_Z_TARGET);
		cheaty = cmp.getBoolean(TAG_CHEATY_MODE);
		camo = cmp.getInteger(TAG_CAMO);
		camoMeta = cmp.getInteger(TAG_CAMO_META);
	}

	final TileEntity getTile() {
		if(!worldObj.blockExists(x, y, z))
			return null;

		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);

		if((tile == null && tileRequiredAtLink()) || ((Math.abs(x - xCoord) > getMaxDistance() || Math.abs(y - yCoord) > getMaxDistance() || Math.abs(z - zCoord) > getMaxDistance()) && !cheaty)) {
			y = -1;
			return null;
		}

		return tile;
	}

	public abstract int getMaxDistance();

	boolean tileRequiredAtLink() {
		return !cheaty;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet) {
		super.onDataPacket(manager, packet);
		readCustomNBT(packet.data);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}
