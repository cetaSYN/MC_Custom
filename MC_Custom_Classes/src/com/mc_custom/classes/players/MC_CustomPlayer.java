package com.mc_custom.classes.players;

import com.mc_custom.classes.MC_Custom_Classes;
import com.mc_custom.classes.classes.MCCustomClass;
import com.mc_custom.classes.classes.MCCustomType;
import com.mc_custom.classes.listeners.abilities.AbilityType;
import com.mc_custom.classes.utils.BlockContainer;
import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.database.Column;
import com.mc_custom.core.database.QueryBuilder;
import com.mc_custom.core.players.BasePlayer;
import com.mc_custom.core.players.Nickname;
import com.mc_custom.core.utils.PluginLogger;
import com.mc_custom.core.utils.Utils;
import com.mc_custom.core.utils.WorldGuardHook;

import org.bukkit.GameMode;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MCCustomPlayer extends BasePlayer {
	private BlockContainer levitated_block;
	private boolean levitation_enabled = false;
	private MCCustomClass player_class;
	private Date last_change;

	public MCCustomPlayer(String player_name,Nickname nickname, UUID uuid, int player_id) {
		super(player_name, nickname, uuid, player_id);
		getDateAndClass();
	}

	public long getChangeSecondsRemaining(){
		if(last_change==null){
			return 0;
		}
		Date change_date = Utils.addDays(-5); //Goal dat
		return last_change.getTime() - change_date.getTime();
	}

	public void setLastChange(Date last_change) {
		this.last_change = last_change;
	}

	public MCCustomClass getMCCustomClass() {
		return player_class;
	}

	public void setMCCustomClass(MCCustomClass player_class) {
		this.player_class = player_class;
	}

	public void applyAttributes() {
		if (getGameMode() == GameMode.SURVIVAL || getGameMode() == GameMode.ADVENTURE) {
			setWalkSpeed(getMCCustomClass().getWalkSpeed());
			setAllowFlight(getMCCustomClass().canFly());
			setFlySpeed(getMCCustomClass().getFlySpeed());
		}
		else if (getGameMode() == GameMode.CREATIVE || getGameMode() == GameMode.SPECTATOR) {
			setWalkSpeed(MCCustomClass.CREATIVE_WALK_SPEED);
			setAllowFlight(MCCustomClass.CREATIVE_CAN_FLY);
			setFlySpeed(MCCustomClass.CREATIVE_FLY_SPEED);
		}
	}

	public void setLevitatedBlock(BlockContainer levitated_block) {
		this.levitated_block = levitated_block;
	}

	public BlockContainer getLevitatedBlock() {
		return levitated_block;
	}

	public boolean isLevitatingBlock() {
		return levitated_block != null;
	}

	public boolean isLevitationEnabled() {
		return levitation_enabled;
	}

	public void setLevitationEnabled(boolean enabled) {
		levitation_enabled = enabled;
	}

	public boolean hasAbilityInRegion(AbilityType check_ability) {
		return player_class.hasAbility(check_ability)
				&& (!MC_Custom_Classes.pluginExists("WorldGuard") || WorldGuardHook.hasAbilityInRegion(getPlayer(), check_ability.name()));
	}

	/**
	 * Adds or updates database entry containing player's name, class, and date of last class-change.
	 */
	public void save() {
		MC_Custom_Classes.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "INSERT INTO `classes` (`player_id`, `player_class`, `last_change`) VALUES (?, ?, NOW()) " +
							"ON DUPLICATE KEY UPDATE `player_class` = ?, `last_change` = NOW()")
							.setInt(getId())
							.setString(player_class.getClassName())
							.setString(player_class.getClassName())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void resetChangeTime() {
		MC_Custom_Classes.runTaskAsynchronously(new Runnable() {
			@Override
			public void run() {
				try {
					long duration = System.currentTimeMillis();
					new QueryBuilder(MC_Custom_Core.getDBConnection(), "UPDATE `classes` SET `last_change` = DATE_SUB(`last_change`, INTERVAL 10 DAY) WHERE `player_id` = ?")
							.setInt(getId())
							.update();
					PluginLogger.database().info(Utils.debugRunDuration(duration, 1));
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Returns class of the player according to database.
	 */
	private void getDateAndClass() {
		try {
			long duration = System.currentTimeMillis();
			Column<String> player_class = new Column<>(String.class);
			Column<java.sql.Date> last_change = new Column<>(java.sql.Date.class);
			new QueryBuilder(MC_Custom_Core.getDBConnection(), "SELECT `player_class`, `last_change` FROM `classes` WHERE `player_id` = ?")
					.setInt(getId())
					.executeQuery()
					.fetchOne(player_class, last_change);
			MCCustomClass player_class = MCCustomType.getMCCustomClass(player_class.getValue());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date_string;
			date_string = (last_change.getValue() == null ? null : last_change.getValue().toString());
			this.last_change = (date_string == null ? null : format.parse(date_string));

			this.player_class = player_class;
			PluginLogger.database().info(Utils.debugRunDuration(duration));
		}
		catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
}
