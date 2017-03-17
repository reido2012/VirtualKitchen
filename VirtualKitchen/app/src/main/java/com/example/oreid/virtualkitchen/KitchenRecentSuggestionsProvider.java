package com.example.oreid.virtualkitchen;

import android.app.SearchManager;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * Content provider for recent search suggestions.
 *
 * Created by hollie on 08/03/2017.
 */

public class KitchenRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static final String TAG = "KitchenSearchProvider";

    public static final String AUTHORITY =
            KitchenRecentSuggestionsProvider.class.getName();

    public static final int MODE = DATABASE_MODE_QUERIES;

    public static final String[] COLUMNS = {
            "_id",
            SearchManager.SUGGEST_COLUMN_TEXT_1
    };

    public KitchenRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String query = selectionArgs[0];
        if (!SearchManager.SUGGEST_URI_PATH_QUERY.equals(query)) {
            MatrixCursor cursor = new MatrixCursor(COLUMNS);

            try {
                List<FoodItem> list = VKData.getInstance().getFoodDB().findByName(query);
                int n = 0;
                for (FoodItem obj : list) {
                    cursor.addRow(createRow(new Integer(n), obj.getName()));
                    n++;
                }

            } catch (Exception e) {
                Log.e(TAG, "Failed to lookup " + query, e);
            }
            return cursor;
        } else {
            return null;
        }
    }

    private Object[] createRow(Integer id, String text) {
        return new Object[] {id, text};
    }


}