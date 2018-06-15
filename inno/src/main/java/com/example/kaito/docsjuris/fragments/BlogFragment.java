package com.example.kaito.docsjuris.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.kaito.docsjuris.R;
import com.example.kaito.docsjuris.adapters.GridviewAdapter;

/**
 * Created by KaiTo on 4/28/2018.
 */

public class BlogFragment extends Fragment {

    public int[] IMAGES = {R.drawable.appartments,R.drawable.shield,R.drawable.hospital,
            R.drawable.family,R.drawable.old_paper,R.drawable.balance};
    private Context context;
    private View rootView;
    private GridView gridview;
    private GridviewAdapter gridviewAdapter;

    public static BlogFragment newInstance() {
        BlogFragment fragment = new BlogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_blog, container, false);
        context=rootView.getContext();

        return rootView;
    }
}