package dev.piste.vayna.interactions.commands.slash;

import dev.piste.vayna.apis.HttpErrorException;
import dev.piste.vayna.apis.riot.RiotAPI;
import dev.piste.vayna.apis.riot.gson.PlatformData;
import dev.piste.vayna.apis.riot.gson.platformdata.Status;
import dev.piste.vayna.translations.Language;
import dev.piste.vayna.translations.LanguageManager;
import dev.piste.vayna.util.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class StatusSlashCommand implements ISlashCommand {

    @Override
    public void perform(SlashCommandInteractionEvent event, Language language) throws HttpErrorException, IOException, InterruptedException {
        event.deferReply(true).queue();

        String riotLanguageCode = language.getLanguageCode().replaceAll("-", "_");
        PlatformData platformData = new RiotAPI().getPlatformData(event.getOption("region").getAsString());

        List<MessageEmbed> embedList = new ArrayList<>();

        for(Status status : platformData.getMaintenances()) {
            String title = null;
            for(Status.Content content : status.getTitles()) {
                if(content.getLocale().equals(riotLanguageCode)) {
                    title = content.getContent();
                }
            }
            if(title == null) throw new NullPointerException("Error while getting title of maintenance (ID: " + status.getId() + ")!");
            List<String> updates = new ArrayList<>();
            for(Status.Update update : status.getUpdates()) {
                for(Status.Content content : update.getTranslations()) {
                    if(content.getLocale().equals(riotLanguageCode)) {
                        updates.add(content.getContent());
                    }
                }
            }
            LocalDateTime localDateTime = LocalDateTime.parse(status.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
            Date date = Date.from(localDateTime.atZone(ZoneId.of("MET")).toInstant());
            Embed embed = new Embed()
                    .setTitle(language.getEmbedTitlePrefix() + title);
            if(updates.size() > 1) {
                for(int i = 0; i < updates.size(); i++) {
                    embed.addField(language.getTranslation("command-status-embed-field-1-name").replaceAll("%updateIndex%", String.valueOf(i)), ">>> " + updates.get(i), false);
                }
            } else {
                embed.setDescription(">>> " + updates.get(0));
            }
            embed.addField(language.getTranslation("command-status-embed-field-2-name"), "<t:" + date.getTime()/1000 + ":F>", true);
            embedList.add(embed.build());
        }

        event.getHook().editOriginalEmbeds(embedList).queue();
    }

    @Override
    public CommandData getCommandData() throws HttpErrorException, IOException, InterruptedException {
        OptionData optionData = new OptionData(OptionType.STRING, "region", "Region", true)
                .addChoice("Europe", "eu")
                .addChoice("North America", "na")
                .addChoice("Korea", "kr")
                .addChoice("Asia Pacific", "ap")
                .addChoice("Brazil", "br")
                .addChoice("Latin America", "latam");
        return Commands.slash(getName(), getDescription()).addOptions(optionData);
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getDescription() {
        return LanguageManager.getLanguage().getTranslation("command-status-description");
    }
}