package dev.piste.vayna.interactions.util.exceptions;

/**
 * @author Piste | https://github.com/PisteDev
 */
public class InvalidRegionException extends Exception {

    private final String name;
    private final String tag;

    public InvalidRegionException(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getRiotId() {
        return name + "#" + tag;
    }

}