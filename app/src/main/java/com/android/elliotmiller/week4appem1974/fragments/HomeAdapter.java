package com.android.elliotmiller.week4appem1974.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.elliotmiller.week4appem1974.R;
import com.android.elliotmiller.week4appem1974.model.DatabaseHandler;

/**
 * Created by azeezolaniran on 06/08/2017.
 */

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {
    private Cursor cursor;
    private Context context;
    private Home.HomeInterface listener;


    public HomeAdapter(Context ctx, Home.HomeInterface mL, Cursor crsr) {
        this.context = ctx;
        this.listener = mL;
        this.cursor = crsr;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        cursor.moveToPosition(position);
        holder.tvId.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID)));
        holder.tvFirstName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_FIRST_NAME)));
        holder.tvLastName.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_LAST_NAME)));
        holder.tvMake.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_MAKE)));
        holder.tvModel.setText(cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_MODEL)));
        holder.tvCost.setText("$ " + cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_COST)));
    }


    @Override
    public int getItemCount() {
        if (cursor == null){
            return 0;
        }
        return cursor.getCount();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvId, tvFirstName, tvLastName, tvModel, tvMake, tvCost;

        public Holder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvFirstName = itemView.findViewById(R.id.tv_first_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvModel = itemView.findViewById(R.id.tv_model);
            tvMake = itemView.findViewById(R.id.tv_make);
            tvCost = itemView.findViewById(R.id.tv_cost);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cursor.moveToPosition(getAdapterPosition());
                    listener.selectCustomer(
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_FIRST_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_LAST_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_MAKE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_MODEL)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHandler.COLUMN_COST))
                    );
                }
            });
        }
    }
}
