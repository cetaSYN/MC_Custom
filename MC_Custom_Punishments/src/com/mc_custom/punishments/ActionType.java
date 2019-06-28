package com.mc_custom.punishments;

public enum ActionType {
    BAN(0),
    UNBAN(1),
    KICK(2),
    MUTE(3),
    UNMUTE(4),
    FREEZE(5),
    THAW(6),
    WARN(7);

    private int num;

    ActionType(int num){
        this.num = num;
    }

    public int getInt(){
        return num;
    }

    public static ActionType getTypeByInt(int num){
        switch(num){
        case 0:
            return ActionType.BAN;
        case 1:
            return ActionType.UNBAN;
        case 2: 
            return ActionType.KICK;
        case 3:
            return ActionType.MUTE;
        case 4:
            return ActionType.UNMUTE;
        case 5:
            return ActionType.FREEZE;
        case 6:
            return ActionType.THAW;
        case 7:
            return ActionType.WARN;
        default:
            return null;
        }
    }
}
