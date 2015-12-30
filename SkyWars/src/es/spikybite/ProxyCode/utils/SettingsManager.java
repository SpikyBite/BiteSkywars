package es.spikybite.ProxyCode.utils;

import java.io.File;
import java.io.InputStream;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import es.spikybite.ProxyCode.Skywars;

public class SettingsManager {

	private FileConfiguration config;
	private File file;
	private Skywars main;
	
	public SettingsManager(Skywars main, String resourceName){
		this.main = main;
		this.file = new File(this.main.getDataFolder(), resourceName+".yml");
		this.config = new YamlConfiguration().loadConfiguration(this.file);
		InputStream readConfig = this.main.getResource(resourceName+".yml");
		YamlConfiguration setDefaults = new YamlConfiguration().loadConfiguration(readConfig);
		try {
			
			if(!this.file.exists()){
				
				this.config.addDefaults(setDefaults);
				this.config.options().copyDefaults(true);
				this.config.save(this.file);
			}else {
			this.config.load(this.file);
			}
				
		} catch (Exception e) {
		
		}
	}
	
	public void sDefault(String path, String value){
		if(!config.contains(path)){
			config.set(path, value);
			save();
		}
	}
	
	public void save(){
		try{
			this.config.save(this.file);
		}catch(Exception e){
			
		}
	}
	
	public FileConfiguration getConfig(){
		return config;
	}
	
	public File getFile(){
		return file;
	}
	
	public  String get(String path){
	return org.bukkit.ChatColor.translateAlternateColorCodes('&', config.getString(path));	
	}
	public int getint(String path){
	return config.getInt(path);	
	}
}