package tech.kdgaming1.easyconfigs.easyconfighandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static tech.kdgaming1.easyconfigs.EasyConfigs.MOD_ID;
import tech.kdgaming1.easyconfigs.chatutils.ECChatUtils;
import tech.kdgaming1.easyconfigs.config.ECConfigs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

public class ECConfigFileManager {

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static void saveConfigs(int slot) throws IOException {
        String configDir = ECSetup.configDir;
        String saveDir = Paths.get(ECSetup.ECDir, "EasyConfigSave" + slot).toString();
        Path saveDirPath = Paths.get(ECSetup.ECDir, "EasyConfigSave" + slot);

        if (Files.exists(saveDirPath)) {
            throw new FileAlreadyExistsException("Save slot " + slot + " already exists.");
        } else {
            ECOptionsApplier.apply(configDir, saveDir);
        }
    }

    public static void loadConfigs(int slot) throws IOException {
        Path loadDir = Paths.get(ECSetup.ECDir, "save" + slot);

        if (!Files.exists(loadDir)) {
            throw new FileNotFoundException("Save slot " + slot + " does not exist.");
        } else {
            ECConfigs.wantToCopy = true;
            ECConfigs.copySlot = slot;
            ECConfigs.getConfiguration().save();
            LOGGER.info("The copy value is now: {}", ECConfigs.wantToCopy);
            LOGGER.info("The copy config value have been set to true to allow the default options to be applied again. Restart the game to apply the default options. The value will be set to false after the default options have been applied.");
            ECChatUtils.printChatMessage("§aConfigs set to load from slot " + slot + ". Restart the game to apply the options.");
        }


    }
}