package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.http.HttpErrorException;
import dev.piste.vayna.interactions.util.exceptions.*;
import dev.piste.vayna.interactions.util.interfaces.ISlashCommand;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.DiscordEmoji;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.io.IOException;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class VoteSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException, InvalidRiotIdException, GuildConnectionsMissingException, InsufficientPermissionException, RSOConnectionPrivateException, InvalidRegionException, RSOConnectionMissingException {
        event.deferReply(true).queue();

        User botUser = event.getJDA().getSelfUser();
        String voteUri = String.format("https://top.gg/bot/%s/vote", botUser.getId());

        Embed embed = new Embed()
                .setTitle(language.getEmbedTitlePrefix() + language.getTranslation("command-vote-embed-title"))
                .setDescription(language.getTranslation("command-vote-embed-desc")
                        .replaceAll("%botUser:mention%", botUser.getAsMention()));

        event.getHook().editOriginalEmbeds(embed.build()).setActionRow(
                Button.link(voteUri, language.getTranslation("button-vote-name")).withEmoji(DiscordEmoji.TOPGG.getAsDiscordEmoji())
        ).queue();
    }

    @Override
    public String getName() {
        return "vote";
    }

    @Override
    public String getDescription(Language language) {
        return language.getTranslation("command-vote-desc");
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        return Commands.slash(getName(), getDescription(LanguageManager.getDefaultLanguage()));
    }

}