package com.jakealves.mixin;

import com.jakealves.HeadsToInventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Unique
	private final LivingEntity livingEntity = (LivingEntity) (Object) this;

	@Inject(method = "dropLoot", at = @At("HEAD"))
	private void uniheads$placeHead(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
		if (this.livingEntity instanceof ServerPlayerEntity player) {
			String playerName = player.getName().getString();
			HeadsToInventory.LOGGER.info(String.format("registered that player %s has died", playerName));

			ItemStack skull = Items.PLAYER_HEAD.getDefaultStack();

			skull.setCustomName(player.getStyledDisplayName());

			if (source.getAttacker() instanceof ServerPlayerEntity killer) {
				String killerName = killer.getName().getString();
				// TODO set LORE on skull to mention killer name
				HeadsToInventory.LOGGER
						.info(String.format("player %s was killed by %s", playerName, killerName));
			}

			player.getInventory().insertStack(skull);
			HeadsToInventory.LOGGER.info(String.format("inserted skull in %s inventory", playerName));
		}
	}

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
		HeadsToInventory.LOGGER.info("extended Entity with LivingEntityMixin");
	}
}