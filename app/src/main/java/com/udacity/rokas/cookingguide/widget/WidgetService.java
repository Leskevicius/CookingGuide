package com.udacity.rokas.cookingguide.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by rokas on 8/1/17.
 */

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListProvider(this, intent);
    }
}
