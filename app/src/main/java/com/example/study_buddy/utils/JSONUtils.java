package com.example.study_buddy.utils;

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
}
