package com.roc.chatclient.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.roc.chatclient.ChatApplication;
import com.roc.chatclient.entity.User;

import java.util.ArrayList;

public final class CommonUtils {
    public static void showLongToast( String pMsg) {
        Toast.makeText(ChatApplication.getInstance(), pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String pMsg) {
        Toast.makeText(ChatApplication.getInstance(), pMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param user
     */
    public static void setUserInitialLetter(User user) {
        final String DefaultLetter = "#";
        String letter = DefaultLetter;

        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return DefaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return DefaultLetter;
                }
                ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0)
                {
                    HanziToPinyin.Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return DefaultLetter;
                    }
                    return letter;
                }
                return DefaultLetter;
            }
        }

        if ( !TextUtils.isEmpty(user.NickName) ) {
            letter = new GetInitialLetter().getLetter(user.NickName);
            user.setInitialLetter(letter);
            return;
        }
        if (letter == DefaultLetter && !TextUtils.isEmpty(user.Name)) {
            letter = new GetInitialLetter().getLetter(user.Name);
        }
        user.setInitialLetter(letter);
    }
}
