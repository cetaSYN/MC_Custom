package com.mc_custom.core.players;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.events.CoreGameModeChangeEvent;
import com.mc_custom.core.utils.chatbuilders.ChatBuilder;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.PermissionUtils;
import com.mc_custom.core.utils.PlayerWeatherType;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class BasePlayer implements Permissible {
	protected final String player_name;
	protected final UUID uuid;
	protected Nickname nickname;
	protected ChatBuilder builder;
	protected Player player;
	protected String prefix;
	protected int player_id;
	protected boolean has_open_inventory = false;
	protected PlayerWeatherType weather = PlayerWeatherType.SERVER;

	/**
	 * Used only by CorePlayer
	 * Nickname and player Id are set after this object is initialized.
	 *
	 * @param player_name The player name to set.
	 * @param uuid        The player uuid to set.
	 */
	protected BasePlayer(String player_name, UUID uuid) {
		this.player_name = player_name;
		this.uuid = uuid;
	}

	public BasePlayer(Player player) {
		this(player.getName(), player.getUniqueId());
		this.player = player;
	}

	public BasePlayer(String player_name, Nickname nickname, UUID uuid, int player_id) {
		this(player_name, uuid);
		this.player_id = player_id;
		this.nickname = nickname;
	}

	public String getPlayerName() {
		return this.player_name;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof BasePlayer) {
			final BasePlayer p = (BasePlayer) obj;
			return this.player_id == p.getId();
		}
		return false;
	}

	/**
	 * Returns the player_id number of the this.player.
	 *
	 * @return player_id
	 */
	public int getId() {
		return this.player_id;
	}

	/**
	 * Sets the player_id number of the this.player.
	 *
	 * @param player_id The player Id to set
	 */
	public void setId(int player_id) {
		this.player_id = player_id;
	}

	/**
	 * Appends a String to the current prefix for this this.player.
	 *
	 * @param prefix The prefix to set.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Appends a String to the current prefix for this this.player.
	 *
	 * @param prefix The prefix to set.
	 */
	public void appendPrefix(String prefix) {
		if (this.prefix == null) {
			setPrefix(prefix);
		}
		else {
			this.prefix += prefix;
		}
	}

	/**
	 * Get the name for the this.player.
	 * Adds tilde (~) and converts color for nicknames.
	 * Adds prefix for the permissions group.
	 *
	 * @return nickname if it exists, otherwise playername
	 */
	public String getName() {
		if (this.nickname.get() != null) {
			return this.prefix + "~" + ChatColor.convertColor(this.nickname.get() + ChatColor.RESET);
		}
		else {
			return this.prefix + getPlayerName() + ChatColor.RESET;
		}
	}

	public void setPlayerName(String nick) {
		if (this.player.isOnline()) {
			if (nick != null) {
				nick = ChatColor.convertColor(this.prefix + "~" + nick + ChatColor.RESET);
				if (nick != null) { // This is basically here to keep IDEA from complaining
					if (nick.length() >= 16) {
						this.player.setPlayerListName(nick.substring(0, 15));
					}
					else {
						this.player.setPlayerListName(nick);
					}
					this.player.setDisplayName(nick);
				}
			}
			else {
				this.player.setPlayerListName(this.prefix + this.player.getName());
				this.player.setDisplayName(this.prefix + this.player.getName());
			}
		}
	}

	/**
	 * Gets the player's nickname.
	 */
	public Nickname getNickname() {
		return this.nickname;
	}

	/**
	 * Sets the player's nickname.
	 *
	 * @param new_nick New nickname to be used.
	 */
	public void setNickname(final String new_nick) {
		//Verify nickname isn't already used.
		//TODO: Check that there are no date restrictions
		this.nickname.saveNickname(new_nick);
	}

	/**
	 * Sets the player's nickname.
	 *
	 * @param new_nick New nickname to be used.
	 */
	public void staffSetNickname(final String new_nick) {
		//Verify nickname isn't already used.
		this.nickname.saveNickname(new_nick);
	}

	/**
	 * Removes the player's nickname.
	 */
	public void removeNickname() {
		this.nickname.saveNickname(null);
	}

	public void setGameMode(GameMode gm) {
		this.player.setGameMode(gm);
		// Fix for not being able to fly when using /gmc
		// Becuase fuck you Bukkit, Imma make my own event
		CoreGameModeChangeEvent gamemode_event = new CoreGameModeChangeEvent(this.player, gm);
		MC_Custom_Core.callEvent(gamemode_event);
	}

	public GameMode getGameMode() {
		return this.player.getGameMode();
	}

	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}

	public boolean isOnline() {
		return this.player.isOnline();
	}

	public Location getLocation() {
		return this.player.getLocation();
	}

	public World getWorld() {
		return this.player.getWorld();
	}

	public PlayerInventory getInventory() {
		return this.player.getInventory();
	}

	public boolean performCommand(String command) {
		return this.player.performCommand(command);
	}

	public void chat(String message) {
		this.player.chat(message);
	}

	public Block getTargetBlock(Set<Material> set, int var2) {
		return this.player.getTargetBlock(set, var2);
	}

	public Inventory getEnderChest() {
		return this.player.getEnderChest();
	}

	/**
	 * @return Returns the true if the player is vanished.
	 */
	public boolean isVanished() {
		return this.player.hasMetadata("vanished") && this.player.getMetadata("vanished").get(0).asBoolean();
	}

	public boolean isFlying() {
		return this.player.isFlying();
	}

	public Vector getVelocity() {
		return this.player.getVelocity();
	}

	public void setWalkSpeed(float value) {
		this.player.setWalkSpeed(value);
	}

	public float getWalkSpeed() {
		return this.player.getWalkSpeed();
	}

	public void setFlySpeed(float value) {
		this.player.setFlySpeed(value);
	}

	public float getFlySpeed() {
		return this.player.getFlySpeed();
	}

	public void setAllowFlight(boolean value) {
		this.player.setAllowFlight(value);
	}

	public void setVelocity(Vector v) {
		this.player.setVelocity(v);
	}

	public InventoryView openInventory(Inventory inventory) {
		return this.player.openInventory(inventory);
	}

	public ItemStack getItemInHand() {
		return this.player.getItemInHand();
	}

	public void setPlayerListName(String name) {
		this.player.setPlayerListName(name);
	}

	public String getPlayerListName() {
		return this.player.getPlayerListName();
	}

	public void setDisplayName(String name) {
		this.player.setDisplayName(name);
	}

	public String getDisplayName() {
		return this.player.getDisplayName();
	}

	public void setHealth(double health) {
		this.player.setHealth(health);
	}

	public double getMaxHealth() {
		return this.player.getMaxHealth();
	}

	public void setFoodLevel(int food) {
		this.player.setFoodLevel(food);
	}

	public int getFoodLevel() {
		return this.player.getFoodLevel();
	}

	public void setItemInHand(ItemStack item) {
		this.player.setItemInHand(item);
	}

	public Collection<PotionEffect> getActivePotionEffects() {
		return this.player.getActivePotionEffects();
	}

	public void removeActivePotionEffects() {
		for (PotionEffect potion_effect : this.player.getActivePotionEffects()) {
			this.player.removePotionEffect(potion_effect.getType());
		}
	}

	@Override
	public boolean isPermissionSet(String s) {
		return this.player.isPermissionSet(s);
	}

	@Override
	public boolean isPermissionSet(Permission permission) {
		return this.player.isPermissionSet(permission);
	}

	@Override
	public boolean hasPermission(String perm) {
		return PermissionUtils.hasWildcardPermission(this.player, perm);
	}

	@Override
	public boolean hasPermission(Permission permission) {
		return PermissionUtils.hasWildcardPermission(this.player, permission.getName());
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
		return this.player.addAttachment(plugin, s, b);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return this.player.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
		return this.player.addAttachment(plugin, s, b, i);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int i) {
		return this.player.addAttachment(plugin, i);
	}

	@Override
	public void removeAttachment(PermissionAttachment permission_attachment) {
		this.player.removeAttachment(permission_attachment);
	}

	@Override
	public void recalculatePermissions() {
		this.player.recalculatePermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return this.player.getEffectivePermissions();
	}

	@Override
	public boolean isOp() {
		return this.player.isOp() || hasPermission("*");
	}

	@Override
	public void setOp(boolean b) {
		this.player.setOp(b);
	}

	public void giveExp(int amount) {
		this.player.giveExp(amount);
	}

	public float getExp() {
		return this.player.getExp();
	}

	public int getTotalExperience() {
		return this.player.getTotalExperience();
	}

	public float getExpToLevel() {
		return this.player.getExpToLevel();
	}

	public void setExp(float exp) {
		this.player.setExp(exp);
	}

	public void setTotalExperience(int exp) {
		this.player.setTotalExperience(exp);
	}

	public void openWorkbench(Location location, boolean b) {
		this.player.openWorkbench(location, b);
	}

	public void setFallDistance(int i) {
		this.player.setFallDistance(i);
	}

	public void playSound(Sound sound, float i, float i1) {
		this.player.playSound(getLocation(), sound, i, i1);
	}

	public boolean hasPlayedBefore() {
		return this.player.hasPlayedBefore();
	}

	public void sendRawMessage(String m) {
		this.player.sendRawMessage(m);
	}

	public boolean isInsideVehicle() {
		return this.player.isInsideVehicle();
	}

	public void leaveVehicle() {
		this.player.leaveVehicle();
	}

	public boolean teleport(Location loc) {
		return this.player.teleport(loc);
	}

	public void setChatBuilder(ChatBuilder builder) {
		this.builder = builder;
	}

	public ChatBuilder getChatBuilder() {
		return this.builder;
	}

	public boolean isBuilding() {
		return this.builder != null;
	}

	public void updateInventory() {
		this.player.updateInventory();
	}

	public boolean addPotionEffect(PotionEffect effect) {
		return addPotionEffect(effect, false);
	}

	public boolean addPotionEffects(Collection<PotionEffect> effects) {
		boolean success = true;
		PotionEffect effect;
		for (Iterator iterator = effects.iterator(); iterator.hasNext(); success &= addPotionEffect(effect)) {
			effect = (PotionEffect) iterator.next();
		}
		return success;
	}

	public boolean addPotionEffect(PotionEffect effect, boolean b) {
		return this.player.addPotionEffect(effect, b);
	}

	public void setPlayerWeather(PlayerWeatherType weather) {
		this.weather = weather;
		switch (weather) {
			case SERVER:
				this.player.resetPlayerWeather();
				break;
			case CLEAR:
				this.player.setPlayerWeather(WeatherType.CLEAR);
				break;
			case RAIN:
			case STORM:
				this.player.setPlayerWeather(WeatherType.DOWNFALL);
				break;
		}
	}

	public PlayerWeatherType getPlayerWeather() {
		return this.weather;
	}

	public void setHasOpenInventory(boolean has_open_inventory) {
		this.has_open_inventory = has_open_inventory;
	}

	public boolean hasOpenInventory() {
		return this.has_open_inventory;
	}
}
