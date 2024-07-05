package com.jakealves;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeadsToInventory implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("heads-to-inventory");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing module!");
	}
}