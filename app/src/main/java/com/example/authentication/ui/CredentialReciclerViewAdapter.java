package com.example.authentication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.authentication.R;
import com.example.authentication.database.entity.Credential;

import java.util.List;

public class CredentialReciclerViewAdapter extends  RecyclerView.Adapter<CredentialReciclerViewAdapter.ViewHolder>{

    public List<Credential>  mValues;
    public Context context;


    public CredentialReciclerViewAdapter(Context cont ,List<Credential> credentials){
        mValues = credentials;
        context = cont;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_credential,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null){
            holder.mCredential = mValues.get(position);
            holder.tvNumCuenta.setText(String.valueOf(holder.mCredential.getIdCuenta() ) );
            holder.tvName.setText(holder.mCredential.getNombre());

            Glide.with(context)
                    .load(R.mipmap.estudiante)
                    .into(holder.imgView);


        }
    }


    @Override
    public int getItemCount() {
        if (mValues != null){
            return mValues.size();
        }else {
            return 0;
        }
    }

    public void setData(List<Credential> credetials) {
        mValues = credetials;
        notifyDataSetChanged();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvName;
        public final TextView tvNumCuenta;
        public final ImageView imgView;
        public Credential mCredential;



       public ViewHolder(View view){
           super(view);
           mView = view;
           tvName = view.findViewById(R.id.tvNameCredential);
           tvNumCuenta = view.findViewById(R.id.tvCuentaCredential);
           imgView = view.findViewById(R.id.img_student);
       }

    }

}
