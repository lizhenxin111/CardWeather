package net;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import utils.AppContext;

public class VolleyErrorListener implements Response.ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(AppContext.getAppContext(), VolleyErrorHelper.getMessage(error, AppContext.getAppContext()), Toast.LENGTH_SHORT).show();
    }
}
