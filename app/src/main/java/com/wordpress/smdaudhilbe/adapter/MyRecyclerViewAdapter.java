package com.wordpress.smdaudhilbe.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wordpress.smdaudhilbe.mohammed_2284.materializemyapp.R;

import java.util.List;

/**
 * Created by mohammed-2284 on 10/03/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private final List<ViewModel> items;
    private OnItemClickListenerInterface clickListener;

    public MyRecyclerViewAdapter(List<ViewModel> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListenerInterface iListener) {
        this.clickListener = iListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewIs = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        viewIs.setOnClickListener(onClickListener);
        return new MyViewHolder(viewIs);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewModel viewModel = items.get(position);

        //  textView
        holder.textIs.setText(viewModel.getTextString());

        //  imageView
        holder.imageIs.setImageBitmap(null);
        Picasso.with(holder.imageIs.getContext()).load(viewModel.getImageString()).into(holder.imageIs);

        holder.itemView.setTag(viewModel);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //  View onClickListener
    OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(final View v) {
            // Give some time to the ripple to finish the effect
            if (clickListener != null) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        clickListener.onItemClickInterface(v, (ViewModel) v.getTag());
                    }
                }, 200);
            }
        }
    };

    //  ItemClickInterface
    public interface OnItemClickListenerInterface {
        public void onItemClickInterface(View view, ViewModel viewModel);
    }

    //  ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageIs;
        private final TextView textIs;

        public MyViewHolder(View itemViewIs) {
            super(itemViewIs);

            imageIs = (ImageView) itemViewIs.findViewById(R.id.imageView);
            textIs = (TextView) itemViewIs.findViewById(R.id.textView);
        }
    }

    //  POJO
    public static class ViewModel {

        private String textString;
        private String imageString;

        public ViewModel(String textString, String imageString) {
            this.textString = textString;
            this.imageString = imageString;
        }

        public String getTextString() {
            return textString;
        }

        public String getImageString() {
            return imageString;
        }
    }
}