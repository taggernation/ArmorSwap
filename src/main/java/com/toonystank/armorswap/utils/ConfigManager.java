/*
 *     TaggerNationLib - Common utility library for our products.
 *     Copyright (C) 2022  TaggerNation
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.toonystank.armorswap.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private File file;
    private FileConfiguration config;
    private final Plugin plugin;

    /**
     * Initializes the Config.
     * @param plugin Instance of the plugin you want to initialize the config for
     * @param fileName String
     * @param force boolean enable/disable force file update
     * @param copy boolean either copy the file from the plugin or not
     */
    public ConfigManager(Plugin plugin, String fileName, boolean force, boolean copy) throws IOException {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        if (copy) {
            try {
                copy(force);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            if (file.createNewFile()) {
                plugin.getLogger().info("Created new file: " + file.getName());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Initializes the Config. in given path
     * @param plugin Instance of the plugin you want to initialize the config for
     * @param fileName String
     * @param path String path you want to initialize the config in
     * @param force boolean enable/disable force file update
     * @param copy boolean either copy the file from the plugin or not
     */
    public ConfigManager(Plugin plugin, String fileName, String path, boolean force, boolean copy) throws IOException {
        this.plugin = plugin;
        String filePath = plugin.getDataFolder() + File.separator + path;
        this.file = new File(plugin.getDataFolder() + File.separator + path, fileName);
        if (!file.exists()) {
            plugin.getLogger().info("creating new File");
            plugin.getLogger().info("Creating folder: " + new File(plugin.getDataFolder() + File.separator + path).mkdirs());
            if (copy) try {
                copy(force, filePath);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            else plugin.getLogger().info("Creating file: " + file.createNewFile());
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Get Initialized file
     * @return File object
     */
    public File getFile() {
        return file;
    }

    /**
     * Get Initialized config.
     * @return FileConfiguration
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Save the config.
     */
    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload the config.
     * @throws IOException IOException
     * @throws InvalidConfigurationException InvalidConfigurationException
     */
    public void reload() throws IOException, InvalidConfigurationException {
        config.load(file);
    }

    /**
     * Copy the config.
     * @param force boolean enable/disable force copy
     */
    public void copy( boolean force) {
        if (!file.exists()) {
            plugin.saveResource(file.getName(), force);
        }
    }
    /**
     * Copy the config to the given path.
     * @param force boolean enable/disable force copy
     * @param path String path to save the resource
     */
    public void copy( boolean force, String path) {
        plugin.saveResource(path, force);
    }

    /**
     * Update the Config with the newer version of the file
     * @param currentVersion String
     * @param versionPath String
     * @throws IOException IOException
     * @throws InvalidConfigurationException InvalidConfigurationException
     */
    public void updateConfig(@NotNull String currentVersion, @NotNull String versionPath) throws IOException, InvalidConfigurationException {
        String version = null;
        try {
            version = this.getString(versionPath);
        }catch (NullPointerException e) {
            plugin.getLogger().info("No version found in config.yml... Creating new version of the config");
        }
        if (version == null || !version.equals(currentVersion)) {
            File newFile = new File(file.getParentFile(), "old_" + version + "_" + getFile().getName());
            File oldFile = getFile();
            if (oldFile.renameTo(newFile)) {
                this.file = new File(plugin.getDataFolder(), getFile().getName());
                plugin.saveResource(getFile().getName(), true);
                this.config = YamlConfiguration.loadConfiguration(this.file);
            }
        }
    }


    /**
     * update the config's existing path with given value.
     * @param path String
     * @param value Object
     * @throws IllegalArgumentException IOException
     */
    public void update(String path, Object value) {
        if (config.contains(path)) {
            config.set(path, value);
            save();
        }else {
            throw new IllegalArgumentException(path + " does not exist" + " in " + file.getName());
        }
    }

    /**
     * Set the given path with given value.
     * @param path String
     * @param value Object
     */
    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    /**
     * Get the given path.
     * @param path String
     * @return Object
     */
    public Object get(String path) {
        return config.get(path);
    }

    /**
     * Get the given path as a list.
     * @param path String
     * @return List
     */
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    /**
     * Get the given path as a boolean.
     * @param path String
     * @return boolean
     */
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    /**
     * Get the given path as an int.
     * @param path String
     * @return int
     */
    public int getInt(String path) {
        return config.getInt(path);
    }

    /**
     * Get the given path as a double.
     * @param path String
     * @return double
     */
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    /**
     * Get the given path as a long.
     * @param path String
     * @return long
     */
    public String getString(String path) {
        return config.getString(path);
    }
    public void setFile(File file) {
        this.file = file;
    }

}
