package com.example.study_buddy.utils;

import android.util.Log;

import com.example.study_buddy.model.Chat;
import com.example.study_buddy.model.Post;
import com.example.study_buddy.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    /**
     * Reads a single post JSON object and turns it into a Post object.
     *
     * @param postJSON JSON containing post data
     * @return Post object on successful parsing null otherwise.
     */
    public static Post parsePostJSON(JSONObject postJSON) {
        Post post = null;

        try {
            String id = postJSON.getString("id");
            String title = postJSON.getString("title");
            String description = postJSON.getString("description");
            String createdAt = postJSON.getString("createdAt");
            JSONArray tagsJSON = postJSON.getJSONArray("tags");
            JSONObject userJSON = postJSON.getJSONObject("user");


            String tags[] = new String[tagsJSON.length()];

            for (int i = 0; i < tagsJSON.length(); i++) {
                tags[i] = tagsJSON.getString(i);
            }

            User author = new User(userJSON.getString("id"),
                    userJSON.getString("firstName"),
                    userJSON.getString("lastName"));

            post = new Post(id, title, description, createdAt, tags, author);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return post;
    }

    /**
     * Takes a JSON string of an array of post data and parses it to extract
     * individual Post objects.
     *
     * @param jsonString String to parse.
     * @return Array of Post or null if unsuccessful.
     */
    public static Post[] parsePostsFromString(String jsonString) {
        try {
            JSONArray postsJSON = new JSONArray(jsonString);
            Post posts[] = new Post[postsJSON.length()];

            for (int i = 0; i < postsJSON.length(); i++) {
                JSONObject postJSON = postsJSON.getJSONObject(i);
                posts[i] = JSONUtils.parsePostJSON(postJSON);
            }

            return posts;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Takes a chat JSON object and parses it to create a Chat object.
     *
     * @param chatJSON JSON containing chat data.
     * @return Chat object if successful null otherwise.
     */
    public static Chat parseChatJSON(JSONObject chatJSON) {
        Chat chat = null;

        try {
            String id = chatJSON.getString("id");
            String preview = chatJSON.getString("preview");
            String updatedAt = chatJSON.getString("updatedAt");
            JSONObject userJSON = chatJSON.getJSONObject("user");
            Integer count = chatJSON.getInt("count");

            User participant = new User(userJSON.getString("id"),
                    userJSON.getString("firstName"),
                    userJSON.getString("lastName"));

            chat = new Chat(id, preview, updatedAt, participant, count);

            Log.d("JSONUtils", "Successfully parsed chat");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chat;
    }

    public static Chat[] parseChatsFromString(String jsonString) {
        try {
            JSONArray chatsJSON = new JSONArray(jsonString);
            Chat chats[] = new Chat[chatsJSON.length()];

            for (int i = 0; i < chatsJSON.length(); i++) {
                JSONObject postJSON = chatsJSON.getJSONObject(i);
                chats[i] = JSONUtils.parseChatJSON(postJSON);
            }

            Log.d("JSONUtils", "Successfully parsed all chats");

            return chats;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
