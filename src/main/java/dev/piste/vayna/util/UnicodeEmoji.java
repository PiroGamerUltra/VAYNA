package dev.piste.vayna.util;

import net.dv8tion.jda.api.entities.emoji.Emoji;

/**
 * @author Piste | https://github.com/PisteDev
 */
@SuppressWarnings("unused")
public enum UnicodeEmoji {

    UnicodeEmoji();

    public enum Region {

        EUROPE("eu", "🇪🇺"),
        NORTH_AMERICA("na", "🇺🇸"),
        BRAZIL("br", "🇧🇷"),
        LATIN_AMERICA("latam", "🇨🇱"),
        KOREA("kr", "🇰🇷"),
        ASIA_PACIFIC("ap", "🇯🇵");

        private final String id;
        private final String unicode;

        Region(String id, String unicode) {
            this.id = id;
            this.unicode = unicode;
        }

        public String getUnicode() {
            return unicode;
        }

        public Emoji getAsDiscordEmoji() {
            return Emoji.fromUnicode(unicode);
        }

        public static Region getRegionById(String id) {
            for(Region region : Region.values()) {
                if(region.id.equals(id)) {
                    return region;
                }
            }
            return null;
        }

    }

}