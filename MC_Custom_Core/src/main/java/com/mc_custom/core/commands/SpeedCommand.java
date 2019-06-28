package com.mc_custom.core.commands;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.exceptions.InvalidArgumentException;
import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.exceptions.NotOnlineException;
import com.mc_custom.core.exceptions.PlayerOnlyException;
import com.mc_custom.core.exceptions.TooFewArgumentsException;
import com.mc_custom.core.exceptions.TooManyArgumentsException;
import com.mc_custom.core.players.CorePlayer;

import java.text.DecimalFormat;

public class SpeedCommand extends BaseCommand {

	@Override
	public String[] getCommandNames() {
		return new String[]{"speed", "setspeed", "speedset"};
	}

	@Override
	public String getRequiredPermissions() {
		return "core.speed";
	}

	@Override
	public String[] exec(MC_CustomCommand command) throws TooManyArgumentsException, TooFewArgumentsException,
			NoPermissionException, InvalidArgumentException, PlayerOnlyException, NotOnlineException {

		if (!command.fromPlayer()) {
			throw new PlayerOnlyException();
		}
		if (command.getArgs().length > 2) {
			throw new TooManyArgumentsException();
		}
		if(command.getArgs().length < 1){
			throw new TooFewArgumentsException();
		}
		CorePlayer sender = MC_Custom_Core.getPlayerHandler().getPlayer(command.getSenderPlayer());
		DecimalFormat df = new DecimalFormat("#.##");
		String speed_type = command.getArg(0).toLowerCase();
		if(!speed_type.equals("fly") && !speed_type.equals("walk")){
			throw new InvalidArgumentException("Speed type must be \"walk\" or \"fly\"");
		}
		if (command.getArgs().length > 0) {
			// /speed [1-5]
			float speed = 0;
			String string_speed = command.getArg(1);
			try {
				speed = Integer.parseInt(string_speed);
				if (speed > 0 && speed < 6) {
					if(speed_type.equals("fly")) {
						sender.setFlySpeed(speed / 10);
					}
					else{
						sender.setWalkSpeed(speed / 10);
					}
					return new String[]{"Your " + speed_type + " speed was set to " + df.format(speed)};
				}
				else {
					throw new InvalidArgumentException(string_speed + "is not a valid range. Must be 1-5.");
				}
			}
			catch (NumberFormatException e) {
				throw new InvalidArgumentException(string_speed + "is not a valid number.");
			}
		}
		else {
			float fly_speed = sender.getFlySpeed() * 10;
			return new String[]{"Your current " + speed_type + " speed is: " + df.format(fly_speed)};
		}
	}

	@Override
	public String[] getHelp() {
		return new String[]{
				"Sets the speed of a player",
				"Syntax: /speed <fly|walk> [1-5]"
		};
	}
}