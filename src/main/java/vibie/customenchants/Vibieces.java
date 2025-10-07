package vibie.customenchants;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vibie.customenchants.commandregister.VibiecesCommand;
import vibie.customenchants.lootinjector.LootInjector;
import vibie.customenchants.register.ModEnchantments;


public class Vibieces implements ModInitializer {
	public static final String MOD_ID = "vibieces";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEnchantments.register();
		LootInjector.registerLootpoolModifier();
		VibiecesCommand.register();
	}
}
