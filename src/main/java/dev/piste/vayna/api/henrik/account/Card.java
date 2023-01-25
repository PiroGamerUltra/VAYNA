package dev.piste.vayna.api.henrik.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private String small;
    private String large;
    private String wide;

    public String getSmall() {
        return small;
    }

    public String getLarge() {
        return large;
    }

    public String getWide() {
        return wide;
    }
}
