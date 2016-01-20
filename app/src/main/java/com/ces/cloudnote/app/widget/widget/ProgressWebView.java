package com.ces.cloudnote.app.widget.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressWebView extends WebView {

	private ProgressBar progressbar;

	public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(params);

		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyle);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.CENTER;

		layout.addView(progressbar, params2);

		addView(layout);
		setWebChromeClient(new WebChromeClient());
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog.Builder(view.getContext())
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();
            return true;
        }

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
