package com.ces.cloudnote.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.ces.cloudnote.app.ImageActivity;
import com.ces.cloudnote.app.R;
import com.ces.cloudnote.app.adapter.ImageAdapter;
import com.ces.cloudnote.app.utils.Util;

/**
 * Created by fuzhaohui on 14-9-20.
 */
public class GridFragment extends BaseFragment {

    private GridView gv_img;
    private ImageAdapter adapter;
    private TextView tv_noimg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        gv_img = (GridView) view.findViewById(R.id.gv_img);
        tv_noimg = (TextView) view.findViewById(R.id.iv_noimg);
        gv_img.setFastScrollEnabled(true);
        adapter = new ImageAdapter(view.getContext());
        gv_img.setAdapter(adapter);
        gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(view.getContext(),
                        ImageActivity.class);
                intent.putExtra("path", adapter.getItem(position));
                startActivity(intent);
            }
        });
        Util.initImageLoader(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage();
    }

    private void loadImage() {
        adapter.addAll(Util.getGalleryPhotos(getActivity()));
        if (adapter.isEmpty()) {
            tv_noimg.setVisibility(View.VISIBLE);
        } else {
            tv_noimg.setVisibility(View.GONE);
            String s = "file://" + adapter.getItem(0);
            //ImageLoader.getInstance().displayImage(s, iv_icon);
            //ImageLoader.getInstance().displayImage(s, iv_bottom);
        }
    }

}
