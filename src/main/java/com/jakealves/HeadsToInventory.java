package com.jakealves;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.authlib.GameProfile;

public class HeadsToInventory implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("heads-to-inventory");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing module!");
	}

	public static NbtCompound nbtFromProfile(GameProfile profile) {
		NbtCompound nbtCompound = new NbtCompound();
		nbtCompound.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), profile));

		NbtCompound displaytag = new NbtCompound();
		displaytag.putString("Name", nameFromProfile(profile));
		nbtCompound.put("display", displaytag);

		return nbtCompound;
	}

	public static NbtCompound nbtFromTextureValue(UUID id, String texturevalue, String shownname) {
		NbtCompound nbtCompound = new NbtCompound();
		NbtCompound skullownertag = new NbtCompound();
		NbtCompound texturetag = new NbtCompound();
		NbtList texturelist = new NbtList();
		NbtCompound valuetag = new NbtCompound();
		NbtCompound displaytag = new NbtCompound();

		valuetag.putString("Value", texturevalue);
		texturelist.add(valuetag);
		texturetag.put("textures", texturelist);
		skullownertag.put("Properties", texturetag);
		skullownertag.putUuid("Id", id);
		nbtCompound.put("SkullOwner", skullownertag);
		displaytag.putString("Name", shownname);
		nbtCompound.put("display", displaytag);
		return nbtCompound;
	}

	public static NbtCompound addLore(NbtCompound nbt, PlayerEntity player) {
		NbtCompound display = nbt.getCompound("display");

		NbtList loreList = new NbtList();
		loreList.add(NbtString.of(getAttacker(player)));

		display.put("Lore", loreList);
		nbt.put("display", display);

		return nbt;
	}

	private static String nameFromProfile(GameProfile profile) {
		return profile.getName();
	}

	private static String getAttacker(PlayerEntity player) {
		String attackerName = nameFromProfile(player.getGameProfile());
		return attackerName;
	}
}