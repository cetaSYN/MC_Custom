package com.mc_custom.core.configuration;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.utils.PluginLogger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Handles options that are configured via config.yml
 */
public class DatabaseConfiguration {

	private static File data_folder;
	private static String database_url;
	private static String database_username;
	private static String database_password;

	public static HikariDataSource loadConfig(File file) {
		loadConfigFile(file);
		return initConnectionPool();
	}

	private static HikariDataSource initConnectionPool() {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(6);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("url", database_url);
		config.addDataSourceProperty("user", database_username);
		config.addDataSourceProperty("password", database_password);
		config.setConnectionTimeout(1500); //1.5 Secs
		config.setIdleTimeout(300000); //5 Mins
		config.setMaxLifetime(18000000); //30 Mins
		config.setInitializationFailFast(true);
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 250);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		config.addDataSourceProperty("useServerPrepStmts", true);
		return new HikariDataSource(config);
	}

	/**
	 * Loads the config file for the plugin.
	 *
	 * @param data_folder The location the plugin configuration folder is located.
	 */
	private static void loadConfigFile(File data_folder) {
		DatabaseConfiguration.data_folder = data_folder;
		File config_file = new File(data_folder, "config.yml");

		//write the config file, will return false if error
		if (writeConfig(config_file)) {
			try {
				YamlConfiguration config = YamlConfiguration.loadConfiguration(config_file);

				String database_host = config.getString("sql.host");
				String database_port = config.getString("sql.port");
				String database = config.getString("sql.database");

				database_username = config.getString("sql.username");
				database_password = config.getString("sql.password");
				database_url = "jdbc:mysql://" + database_host + ":" + database_port + "/" + database;

			}
			catch (Exception ex) {
				PluginLogger.core().severe("Configuration file failed to load!");
			}
		}
		else {
			PluginLogger.core().warning("Using default values.");
		}
	}

	/**
	 * Writes the config file to specified location for easier access.
	 *
	 * @return true if the file was created successfully, or already exists.
	 * false if the file was not created successfully.
	 */
	private static Boolean writeConfig(File output_file) {
		if (output_file.exists()) {
			return true;
		}

		InputStream in = null;
		OutputStream out = null;

		try {
			data_folder.mkdirs();
			output_file.delete();
			output_file.createNewFile();

			in = MC_Custom_Core.class.getResourceAsStream('/' + "config.yml");
			out = new BufferedOutputStream(new FileOutputStream(output_file));

			byte[] buffer = new byte[1024];
			int num_read;
			while ((num_read = in.read(buffer)) != -1) {
				out.write(buffer, 0, num_read);
			}
			PluginLogger.core().info("Configuration file was created successfully");
			return true;
		}
		catch (IOException ex) {
			PluginLogger.core().warning("Failed to create configuration file.");
			PluginLogger.core().warning("Do you have permission to write to this location?");
			PluginLogger.core().warning(ex.getMessage());
			return false;
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}

				if (out != null) {
					out.close();
				}
			}
			catch (IOException ioe) {
				PluginLogger.core().warning("An error has occurred.");
				PluginLogger.core().warning(ioe.getMessage());
			}
		}
	}
}