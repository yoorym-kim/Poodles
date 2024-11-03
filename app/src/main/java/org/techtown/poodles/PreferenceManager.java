package org.techtown.poodles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.gson.Gson;

import org.techtown.poodles.data.response.ResponseMessage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PreferenceManager {
    private Context context;
    public PreferenceManager(Context context) {
        this.context = context;
    }
    private static final String FILE_NAME = "POODLES_PREFERENCES";
    private static final String IDEA_LIST = "IDEA_LIST";

    public SharedPreferences sharedPreferences() {
        try {
            return EncryptedSharedPreferences.create(
                    FILE_NAME,
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveIdea(ResponseMessage.IdeaModel ideaModel) {
        SharedPreferences pref = sharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(pref.getStringSet(IDEA_LIST, new HashSet<>()));
        set.add(new Gson().toJson(ideaModel));
        editor.putStringSet(IDEA_LIST, set);
        editor.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ResponseMessage.IdeaModel> getIdeaList() {
        ArrayList<String> ideaJson = new ArrayList<>(sharedPreferences().getStringSet(IDEA_LIST, new HashSet<>()));

        return ideaJson.stream().map(it -> {
            return new Gson().fromJson(it, ResponseMessage.IdeaModel.class);
        }).collect(Collectors.toList());
    }

    public void saveString(String key, String value) {
        sharedPreferences().edit()
                .putString(key, value)
                .apply();
    }

    private String getString(String key) {
        String value = sharedPreferences().getString(key, "");
        return value;
    }

}
