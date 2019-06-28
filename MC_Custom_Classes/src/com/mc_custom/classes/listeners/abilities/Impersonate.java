package com.mc_custom.classes.listeners.abilities;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.handlers.PlayerHandler;
import com.mc_custom.core.listeners.BaseListener;
import com.mc_custom.core.utils.ChatColor;
import com.mc_custom.core.utils.Utils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Impersonate implements BaseListener {

	private PlayerHandler<MCCustomPlayer> player_handler;

	public Impersonate() {
		player_handler = MC_Custom_Classes.getPlayerHandler();
	}
/*

	*//*
	 * http://www.spigotmc.org/resources/libs-disguises.81/
	 * dev.bukkit.org/bukkit-plugins/protocollib/
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void impersonate(final PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		try {
			if(player_handler.getPlayer(player).hasAbilityInRegion(AbilityType.IMPERSONATE)
					&& player.getItemInHand().getType() == Material.SADDLE) {
				if(event.getRightClicked() instanceof Player) {
					DisguiseAPI.undisguiseToAll(player);
					player.sendMessage(ChatColor.GOLD + "Undisguised");
					custom3Effect(player);
				}
				else if(event.getRightClicked() instanceof LivingEntity) {
					if(event.getRightClicked().getType().equals(EntityType.PIG)) {
						Pig pig = (Pig)event.getRightClicked();
						//Note: Cannot disguise as a pig if they have a saddle
						if(!player.isSneaking() && !pig.hasSaddle()) {
							//Disguise as pig, remove old pig and spawn new one to fix visual glitch
							pig.getWorld().spawnEntity(pig.getLocation(), EntityType.PIG);
							pig.remove();
							player.updateInventory(); // Fix for visual inventory glitch (Blame Bukkit)
							DisguiseAPI.disguiseToAll(player, new MobDisguise(DisguiseType.getType(event.getRightClicked().getType()), true));
							player.sendMessage(ChatColor.GOLD + "Disguised as: " + event.getRightClicked().getType());
							custom3Effect(player);
							event.setCancelled(true);//It builds f
						}
					}
					else {
						DisguiseAPI.disguiseToAll(player, new MobDisguise(DisguiseType.getType(event.getRightClicked().getType()), true));
						player.sendMessage(ChatColor.GOLD + "Disguised as: " + event.getRightClicked().getType());
						custom3Effect(player);
					}
				}
			}
		}
		catch(NotOnlineException e) {
			e.printStackTrace();
		}
	}

	private void custom3Effect(Player player) {
		if(!player.hasMetadata("vanished") || !player.getMetadata("vanished").get(0).asBoolean()) {
			player.getWorld().playEffect(player.getEyeLocation(), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation().subtract(0, 0.5, 0), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation().subtract(1, 0.5, 0), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation().subtract(-1, 0.5, 0), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation().subtract(0, 0.5, 1), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation().subtract(0, 0.5, -1), Effect.HAPPY_VILLAGER, 30);
			player.getWorld().playEffect(player.getEyeLocation(), Effect.HAPPY_VILLAGER, 30);

			player.getWorld().playSound(player.getLocation(), Sound.SILVERFISH_HIT, 1, 1);
		}
	}

	/**
	 * Prevents mobs from targeting those that are disguised.
	 *
	 * @param event
	 */
	@EventHandler
	public void targetDiguise(final EntityTargetEvent event) {
		Entity target = event.getTarget();
		if(target instanceof Player) {
			if(DisguiseAPI.isDisguised((Player)target, target)) {
				event.setCancelled(true);
				event.setTarget(null);
			}
		}
	}

	@EventHandler //TODO: Needs fixing
	public void attackDisguise(final EntityDamageByEntityEvent event) {
		Entity attacker = event.getDamager();
		Entity entity = event.getEntity();
		if(attacker instanceof Player && entity instanceof Creature) {
			Player player = (Player)attacker;
			Creature creature = (Creature)entity;
			if(DisguiseAPI.isDisguised(player, player)) {
				creature.setTarget(player);
			}
		}
	}


	/**
	 * Removes disguise when damage is taken.
	 *
	 * @param event
	 */
	@EventHandler
	public void impersonateDamageTaken(final EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player) {
			if(DisguiseAPI.isDisguised((Player)entity, entity)
					&& !event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
				Player player = (Player)entity;
				DisguiseAPI.undisguiseToAll(entity);
				player.sendMessage(ChatColor.GOLD + "You are now undisguised");
				custom3Effect(player);
			}
		}
	}

	@EventHandler
	public void impersonateMoveEvent(final PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(DisguiseAPI.isDisguised(player, player)) {
			if(player.getFoodLevel() < 10 || Utils.hasNegativePotionEffect(player)) {
				DisguiseAPI.undisguiseToAll(player);
				player.sendMessage(ChatColor.GOLD + "You are now undisguised");
				custom3Effect(player);
			}
			else if(player.getGameMode() != GameMode.CREATIVE) {
				player.setExhaustion(player.getExhaustion() + 0.07f);
			}
		}
	}
}