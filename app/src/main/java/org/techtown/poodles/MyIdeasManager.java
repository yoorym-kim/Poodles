package org.techtown.poodles;
// MyIdeasManager.java

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyIdeasManager {

    // SharedPreferences 파일명
    private static final String PREF_NAME = "MyIdeasPrefs";

    private Context context;
    private String android_id;

    // Context를 받아 android_id를 초기화하는 생성자
    public MyIdeasManager(Context context) {
        this.context = context;
        this.android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("안드로이드아이디", android_id);
    }

    // 아이디어를 저장하는 메서드
    public void saveIdea(String idea, Context context) {
        try {
            // 사용자의 아이디를 이용하여 아이디어를 암호화
            String encryptedIdea = LEA_Crypto.encode(idea, LEA_Crypto.PBKDF(android_id));
            // 암호화된 아이디어를 SharedPreferences에 저장
            saveToSharedPreferences(context, "encrypted_idea", encryptedIdea);
            Log.d("암호화", encryptedIdea);
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가할 수 있습니다.
        }
    }

    // 저장된 아이디어를 불러오고 복호화하는 메서드
    public String loadIdea(Context context) {
        try {
            // 저장된 암호화된 아이디어를 SharedPreferences에서 불러옴
            String encryptedIdea = loadFromSharedPreferences(context, "encrypted_idea");

            // 사용자의 아이디를 이용하여 아이디어를 복호화
            String decryptedIdea = LEA_Crypto.decode(encryptedIdea, LEA_Crypto.PBKDF(android_id));
            Log.d("복호화", decryptedIdea);
            return decryptedIdea;

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가할 수 있습니다.
            return null;
        }
    }

    // SharedPreferences에 문자열 저장
    private void saveToSharedPreferences(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // SharedPreferences에서 문자열 불러오기
    private String loadFromSharedPreferences(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }

    public void saveMultipleIdeas(String idea1, String idea2, String idea3, Context context) {
        try {
            // 사용자의 아이디를 이용하여 각 아이디어를 암호화
            String encryptedIdea1 = LEA_Crypto.encode(idea1, LEA_Crypto.PBKDF(android_id));
            String encryptedIdea2 = LEA_Crypto.encode(idea2, LEA_Crypto.PBKDF(android_id));
            String encryptedIdea3 = LEA_Crypto.encode(idea3, LEA_Crypto.PBKDF(android_id));

            // 저장된 아이디어 개수 확인
            int ideaCount = getSavedIdeaCount(context);

            // 일련번호를 붙여서 저장
            saveToSharedPreferences(context, "encrypted_idea" + (ideaCount + 1), encryptedIdea1);
            saveToSharedPreferences(context, "encrypted_idea" + (ideaCount + 2), encryptedIdea2);
            saveToSharedPreferences(context, "encrypted_idea" + (ideaCount + 3), encryptedIdea3);

            Log.d("암호화", encryptedIdea1);
            Log.d("암호화", encryptedIdea2);
            Log.d("암호화", encryptedIdea3);
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가할 수 있습니다.
        }
    }

    // 저장된 아이디어 개수를 반환하는 메서드
    private int getSavedIdeaCount(Context context) {
        int count = 0;
        try {
            // 저장된 아이디어들을 SharedPreferences에서 불러오기
            while (loadFromSharedPreferences(context, "encrypted_idea" + (count + 1)) != null) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가할 수 있습니다.
        }
        return count;
    }


    // SharedPreferences에서 모든 아이디어를 불러오는 메서드
    public List<String> loadAllIdeas(Context context) {
        List<String> allIdeas = new ArrayList<>();

        try {
            // 저장된 암호화된 아이디어들을 SharedPreferences에서 불러오기
            for (int i = 1; i <= 9; i++) {
                String key = "encrypted_idea" + i;
                String encryptedIdea = loadFromSharedPreferences(context, key);

                // 복호화하여 리스트에 추가
                if (encryptedIdea != null) {
                    String decodedIdea = LEA_Crypto.decode(encryptedIdea, LEA_Crypto.PBKDF(android_id));
                    if (!allIdeas.contains(decodedIdea)) {
                        allIdeas.add(decodedIdea);
                    }
                }
            }

            return allIdeas;

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리를 추가할 수 있습니다.
            return null;
        }
    }

}
