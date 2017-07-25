package piso.android.bakingapp.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import piso.android.bakingapp.Home;
import piso.android.bakingapp.R;

import static android.content.Context.MODE_PRIVATE;

public class widgetScreen extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int i=0; i<appWidgetIds.length; i++){
            int currentWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widget_screen);
            Intent intent = new Intent(context , Home.class);

            PendingIntent pending = PendingIntent.getActivity(context, 0,intent, 0);

            views.setOnClickPendingIntent(R.id.iv_img, pending);
            SharedPreferences preferences = context.getSharedPreferences("ingre", MODE_PRIVATE);
           String ingre=  preferences.getString("recipeIngre", "choose your Fav Recipe ");
            views.setTextViewText(R.id.tv_ingredient , ingre);
            appWidgetManager.updateAppWidget(currentWidgetId,views);
            //Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }
    }

}
