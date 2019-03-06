package com.example.appv1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class VenuesTab extends Fragment
{
    private static final String TAG = "VenuesTab";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.tab_venues, container, false);

        Log.d(TAG, "onCreate:started.");

        mNames.add("Londoner");
        mImageUrls.add("https://media-cdn.tripadvisor.com/media/photo-s/0e/38/13/c0/the-main-room.jpg");

        mNames.add("Marty");
        mImageUrls.add("http://www.martyrestaurants.com/wp-content/gallery/marty-city-gallery/7k6c5009.jpg");

        mNames.add("Moonshine");
        mImageUrls.add("https://scontent-otp1-1.xx.fbcdn.net/v/t1.0-9/49899569_1187907231390393_45230433738162176_n.jpg?_nc_cat=111&_nc_ht=scontent-otp1-1.xx&oh=62c0a3c003f258f7180b79518207ce9b&oe=5CECFDBF");

        mNames.add("Infinity");
        mImageUrls.add("https://cdn.bestjobs.eu/employer_gallery/5831cd5f5b2c6.jpg");

        mNames.add("Janis");
        mImageUrls.add("https://cluj-napoca.xyz/media/JC020917037-1024x683.jpg");

        mNames.add("Spital");
        mImageUrls.add("http://www.monitorulcj.ro//documente/stories/2015/09/10/Spitalul%20Polaris%20Medical%20_4.jpg");

        mNames.add("Nobori");
        mImageUrls.add("https://i.ytimg.com/vi/37-Hu0UharU/maxresdefault.jpg");

        mNames.add("Dreghici acasa");
        mImageUrls.add("https://scontent-otp1-1.xx.fbcdn.net/v/t1.0-9/47276801_2067288973360207_8574316600558092288_n.jpg?_nc_cat=107&_nc_ht=scontent-otp1-1.xx&oh=8e0bc3da3e2d761a425fc26c4ec334b1&oe=5D28AF11");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(view.getContext(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

}
