package com.example.projekt3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt3.DAO.BoughtInsuranceViewModel;
import com.example.projekt3.Model.BoughtInsurance;
import com.example.projekt3.Model.Insurance;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class ListInsurancesAdapter extends RecyclerView.Adapter<ListInsurancesAdapter.ViewHolder> {
    private static final String TAG = ListInsurancesActivity.class.getName();

    private ArrayList<Insurance> localDataSet;
    private Context context;
    private FirebaseUser user;

    private OnItemClick mOnItemClick;

    private int lastPosition = -1;

    public ListInsurancesAdapter(Context context, ArrayList<Insurance> localDataSet, FirebaseUser user, OnItemClick listener) {
        this.context = context;
        this.localDataSet = localDataSet;
        this.user = user;
        this.mOnItemClick = listener;
    }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView company;
            private final TextView price;

            public ViewHolder(View view) {
                super(view);
                company = view.findViewById(R.id.company);
                price = view.findViewById(R.id.price);

                Button buyBtn = view.findViewById(R.id.buy);
                buyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user.getEmail()!=null) {
                            Log.i(TAG, "Megvéve: " + user.getEmail());
                            Log.i(TAG, String.valueOf(company.getText()));
                            insertToDb(new BoughtInsurance(user.getEmail(), price.getText().toString(), company.getText().toString()));
                        }else {
                            Log.i(TAG, "Vásárláshoz bejelentkezés szükséges!");
                        }
                    }
                });
            }

            public void bindTo(Insurance currentItem) {
                this.company.setText(currentItem.getCompany());
                this.price.setText(currentItem.getPricePerMonth()+" Ft / Hónap");


            }
        }


        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.insurance, viewGroup, false));
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element

            Insurance currIns = localDataSet.get(position);
            viewHolder.bindTo(currIns);


            if (viewHolder.getAdapterPosition() > lastPosition){
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
                viewHolder.itemView.startAnimation(animation);
                lastPosition = viewHolder.getAdapterPosition();
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }

        public void insertToDb(BoughtInsurance insurance){
            mOnItemClick.onClick(insurance);
        }
    }