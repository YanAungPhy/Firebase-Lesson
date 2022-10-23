package com.yap.testapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.yap.testapplication.R;
import com.yap.testapplication.adapter.PlaceHolderListAdapter;
import com.yap.testapplication.modal.PlaceHolderListModel;
import com.yap.testapplication.viewmodel.PlaceHolderListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements PlaceHolderListAdapter.OnItemClickListener {

    View rootView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<PlaceHolderListModel> placeHolderListModels = new ArrayList<>();
    PlaceHolderListAdapter placeHolderListAdapter;
    PlaceHolderListViewModel viewModel;

    //https://www.youtube.com/watch?v=UEXZQId3hIg  ဒီမှာရှာရန်

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        progressBar = rootView.findViewById(R.id.progress_bar);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        placeHolderListAdapter = new PlaceHolderListAdapter(getContext(), placeHolderListModels,this);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placeHolderListAdapter);
        viewModel = ViewModelProviders.of(getActivity()).get(PlaceHolderListViewModel.class);
        progressBar.setVisibility(View.VISIBLE);
        viewModel.makeApiCall();
        viewModel.getPlaceHolderList().observe(getActivity(), new Observer<List<PlaceHolderListModel>>() {
            @Override
            public void onChanged(List<PlaceHolderListModel> placeHolderModels) {
                if (placeHolderModels != null) {
                    placeHolderListModels = placeHolderModels;
                    placeHolderListAdapter.setPlaceHolderListModels(placeHolderListModels);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(getContext(), LoginActivity.class);
//        intent.putExtra("TITLE",placeHolderListModels.get(position).getTitle());
//        startActivity(intent);

       //Toast.makeText(getContext(),placeHolderListModels.get(position).getTitle()+"",Toast.LENGTH_SHORT).show();

    }
}