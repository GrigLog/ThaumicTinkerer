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
 * File Created @ [Dec 25, 2013, 12:20:05 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import vazkii.tinkerer.client.lib.LibResources;

public class ItemIchorclothArmorAdv extends ItemIchorclothArmor {

	public ItemIchorclothArmorAdv(int par1, int par2) {
		super(par1, par2);
		if(ticks())
			MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_GEM_2 : LibResources.MODEL_ARMOR_ICHOR_GEM_1;
	}

	boolean ticks() {
		return false;
	}

	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			ItemStack armor = player.getCurrentArmor(3 - armorType);
			if(armor != null && armor.getItem() == this)
				tickPlayer(player);
		}
	}

	void tickPlayer(EntityPlayer player) {
		// NO-OP
	}

}
