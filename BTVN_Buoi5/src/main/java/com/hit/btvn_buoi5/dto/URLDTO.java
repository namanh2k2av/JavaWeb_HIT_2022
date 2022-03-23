package com.hit.btvn_buoi5.dto;

public class URLDTO {
    private String link;
    private String linkShort;

    public URLDTO() {
    }

    public URLDTO(String link, String linkShort) {
        this.link = link;
        this.linkShort = linkShort;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkShort() {
        return linkShort;
    }

    public void setLinkShort(String linkShort) {
        this.linkShort = linkShort;
    }
}
