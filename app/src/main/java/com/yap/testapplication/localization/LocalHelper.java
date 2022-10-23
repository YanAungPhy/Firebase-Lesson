package com.yap.testapplication.localization;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Toast;
import java.util.Locale;
import static android.content.Context.MODE_PRIVATE;

public class LocalHelper {
    Context context;

    public LocalHelper(Context context) {
        this.context = context;
    }

    public void setLanguage(String language_code) {
        Locale locale = new Locale(language_code);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language_code);
        editor.apply();
    }

    public void loadLanguage() {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLanguage(language);
        //Toast.makeText(context, language, Toast.LENGTH_SHORT).show();
    }
}

