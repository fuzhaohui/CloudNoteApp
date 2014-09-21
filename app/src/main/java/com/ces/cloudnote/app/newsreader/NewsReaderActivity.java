/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ces.cloudnote.app.newsreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;

import com.ces.cloudnote.app.R;

/**
 * Main activity: shows headlines list and articles, if layout permits.
 *
 * This is the main activity of the application. It can have several different layouts depending
 * on the SDK version, screen size and orientation. The configurations are divided in two large
 * groups: single-pane layouts and dual-pane layouts.
 *
 * In single-pane mode, this activity shows a list of headlines using a {@link HeadlinesFragment}.
 * When the user clicks on a headline, a separate activity (a {@link ArticleActivity}) is launched
 * to show the news article.
 *
 * In dual-pane mode, this activity shows a {@HeadlinesFragment} on the left side and an
 * {@ArticleFragment} on the right side. When the user selects a headline on the left, the
 * corresponding article is shown on the right.
 *
 * If an Action Bar is available (large enough screen and SDK version 11 or up), navigation
 * controls are shown in the Action Bar (whether to show tabs or a list depends on the layout).
 * If an Action Bar is not available, a regular image and button are shown in the top area of
 * the screen, emulating an Action Bar.
 */
public class NewsReaderActivity extends Fragment
        implements HeadlinesFragment.OnHeadlineSelectedListener,
                   CompatActionBarNavListener,
                   OnClickListener  {

    private View parentView;
    boolean mIsDualPane = false;
    HeadlinesFragment mHeadlinesFragment;
    ArticleFragment mArticleFragment;
    int mCatIndex = 0;
    int mArtIndex = 0;
    NewsCategory mCurrentCat;
    final String CATEGORIES[] = { "Top Stories", "Politics", "Economy", "Technology" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.main_layout, container, false);
        setUpViews();
        return parentView;
    }

    public void setUpViews() {
        // find our fragments
        mHeadlinesFragment = (HeadlinesFragment) getActivity().getSupportFragmentManager().findFragmentById(
                R.id.headlines);
        mArticleFragment = (ArticleFragment) getActivity().getSupportFragmentManager().findFragmentById(
                R.id.article);

        // Determine whether we are in single-pane or dual-pane mode by testing the visibility
        // of the article view.
        View articleView = parentView.findViewById(R.id.article);
        mIsDualPane = articleView != null && articleView.getVisibility() == View.VISIBLE;

        // Register ourselves as the listener for the headlines fragment events.
        mHeadlinesFragment.setOnHeadlineSelectedListener(this);

        // Set up headlines fragment
        mHeadlinesFragment.setSelectable(mIsDualPane);
        //restoreSelection(savedInstanceState);

        // Set up the category button (shown if an Action Bar is not available)
        Button catButton = (Button) parentView.findViewById(R.id.categorybutton);
        if (catButton != null) {
            catButton.setOnClickListener(this);
        }
    }

    /** Restore category/article selection from saved state. */
    void restoreSelection(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            setNewsCategory(savedInstanceState.getInt("catIndex", 0));
            if (mIsDualPane) {
                int artIndex = savedInstanceState.getInt("artIndex", 0);
                mHeadlinesFragment.setSelection(artIndex);
                onHeadlineSelected(artIndex);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setNewsCategory(0);
    }

    /** Sets the displayed news category.
     *
     * This causes the headlines fragment to be repopulated with the appropriate headlines.
     */
    void setNewsCategory(int categoryIndex) {
        mCatIndex = categoryIndex;
        mCurrentCat = NewsSource.getInstance().getCategory(categoryIndex);
        mHeadlinesFragment.loadCategory(categoryIndex);

        // If we are displaying the article on the right, we have to update that too
        if (mIsDualPane) {
            mArticleFragment.displayArticle(mCurrentCat.getArticle(0));
        }

        // If we are displaying a "category" button (on the ActionBar-less UI), we have to update
        // its text to reflect the current category.
        Button catButton = (Button) parentView.findViewById(R.id.categorybutton);
        if (catButton != null) {
            catButton.setText(CATEGORIES[mCatIndex]);
        }
    }

    /** Called when a headline is selected.
     *
     * This is called by the HeadlinesFragment (via its listener interface) to notify us that a
     * headline was selected in the Action Bar. The way we react depends on whether we are in
     * single or dual-pane mode. In single-pane mode, we launch a new activity to display the
     * selected article; in dual-pane mode we simply display it on the article fragment.
     *
     * @param index the index of the selected headline.
     */
    @Override
    public void onHeadlineSelected(int index) {
        mArtIndex = index;
        if (mIsDualPane) {
            // display it on the article fragment
            mArticleFragment.displayArticle(mCurrentCat.getArticle(index));
        }
        else {
            // use separate activity
            Intent i = new Intent(parentView.getContext(), ArticleActivity.class);
            i.putExtra("catIndex", mCatIndex);
            i.putExtra("artIndex", index);
            startActivity(i);
        }
    }

    /** Called when a news category is selected.
     *
     * This is called by our CompatActionBarNavHandler in response to the user selecting a
     * news category in the Action Bar. We react by loading and displaying the headlines for
     * that category.
     *
     * @param catIndex the index of the selected news category.
     */
    @Override
    public void onCategorySelected(int catIndex) {
        setNewsCategory(catIndex);
    }


    /** Called when news category button is clicked.
     *
     * This is the button that we display on UIs that don't have an action bar. This button
     * calls up a list of news categories and switches to the given category.
     */
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
        builder.setTitle("Select a Category");
        builder.setItems(CATEGORIES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setNewsCategory(which);
            }
        });
        AlertDialog d = builder.create();
        d.show();
    }
}
