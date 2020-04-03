import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class Bot extends ListenerAdapter {
    public static void main(String[] arguments) throws Exception {
        new JDABuilder(ConfigLoader.loadConfig("Discord", "BOT_TOKEN")).addEventListeners(new Bot()).build();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event){
        System.out.println("Received a message:\n\t"+ event.getMessage().getContentDisplay());
    }
}