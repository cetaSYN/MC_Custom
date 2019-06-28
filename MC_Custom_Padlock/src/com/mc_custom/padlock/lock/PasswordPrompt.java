package com.mc_custom.padlock.lock;

public class PasswordPrompt extends ValidatingPrompt{

    @Override
    public String getPromptText(ConversationContext arg0) {
        return "Password: " + arg0.getSessionData("data");
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext arg0, String arg1) {
        return END_OF_CONVERSATION;
    }

    @Override
    protected boolean isInputValid(ConversationContext arg0, String arg1) {
        return true;
    }
}
