package com.example.projekt3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt3.Model.BoughtInsurance;

import java.util.List;

public class ProfileViewAdapter extends RecyclerView.Adapter<ProfileViewAdapter.InsuranceViewHolder>{

    private final LayoutInflater mInflater;
    private List<BoughtInsurance> mInsurances;
    private Context context;

    ProfileViewAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public InsuranceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.bought_insurance, parent, false);
        return new InsuranceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InsuranceViewHolder holder, int position) {
        if (mInsurances != null) {
            BoughtInsurance current = mInsurances.get(position);
            holder.companyTextView.setText(current.getCompany());
            holder.priceTextView.setText(current.getPrice());
            holder.ownerTextView.setText(current.getOwner());

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_bottom);
            holder.itemView.startAnimation(animation);

        } else {
            holder.companyTextView.setText("NO INSURANCES");
            holder.priceTextView.setText("NO INSURANCES");
            holder.ownerTextView.setText("NO INSURANCES");
        }
    }

    void setInsurances(List<BoughtInsurance> insurances){
        mInsurances = insurances;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mInsurances != null)
            return mInsurances.size();
        else return 0;
    }

    class InsuranceViewHolder extends RecyclerView.ViewHolder {
        private final TextView companyTextView;
        private final TextView priceTextView;
        private final TextView ownerTextView;

        private InsuranceViewHolder(View itemView) {
            super(itemView);
            companyTextView = itemView.findViewById(R.id.company);
            priceTextView = itemView.findViewById(R.id.price);
            ownerTextView = itemView.findViewById(R.id.owner);
        }
    }

}
