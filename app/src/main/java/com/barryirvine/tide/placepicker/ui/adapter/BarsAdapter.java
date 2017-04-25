package com.barryirvine.tide.placepicker.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.barryirvine.tide.placepicker.BR;
import com.barryirvine.tide.placepicker.R;
import com.barryirvine.tide.placepicker.model.Place;
import com.barryirvine.tide.placepicker.viewmodel.PlaceViewModel;

import java.util.List;

public class BarsAdapter extends RecyclerView.Adapter<BarsAdapter.ViewHolder> {

    private final List<Place> mItems;
    private Location mLocation;

    public BarsAdapter(final List<Place> items, final Location location) {
        mItems = items;
        mLocation = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, @LayoutRes final int viewType) {
        return new BarsAdapter.ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
    }

    @LayoutRes
    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_place;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(new PlaceViewModel(holder.itemView.getContext(), mItems.get(position), mLocation));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding mBinding;

        ViewHolder(@NonNull final ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(@NonNull final PlaceViewModel feedItemViewModel) {
            mBinding.setVariable(BR.viewModel, feedItemViewModel);
            mBinding.executePendingBindings();
        }
    }
}
