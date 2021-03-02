package com.example.study_buddy.model;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {

    private String id;
    private String preview;
    private String updatedAt;
    private User participant;
    private Integer count;

    private static final String ROBO_HASH_BASE_URL = "https://robohash.org";
    private static final String QUERY_PARAM = "set";

    public Chat(String id, String preview, String updatedAt, User participant, Integer count) {
        this.id = id;
        this.preview = preview;
        this.updatedAt = updatedAt;
        this.participant = participant;
        this.count = count;
    }

    public static Uri generateProfileImageUri(Chat chat) {

        return Uri.parse(ROBO_HASH_BASE_URL).buildUpon()
                .appendPath(chat.getParticipant().getFirstName() + chat.getId())
                .appendQueryParameter(QUERY_PARAM, "set4")
                .build();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getUpdatedAt() {
        String pattern = "MM/d/yy hh:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        String formattedDate;

        try {
            Date updatedAtDate = dateFormat.parse(updatedAt);
            formattedDate = dateFormat.format(updatedAtDate);

        } catch (ParseException e) {
            formattedDate = dateFormat.format(new Date());
        }

        return formattedDate;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
