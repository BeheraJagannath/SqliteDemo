package com.example.squlitetutorial;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    SqliteHelper sqliteHelper ;
    Context context;
    ArrayList<Event> eventList;
    Onclicklistener onclicklistener;
    CustomAdapter(Context context, ArrayList<Event> eventList,Onclicklistener onclicklistener) {
        this .context = context;
        this.eventList=eventList;
        this.onclicklistener = onclicklistener ;
        sqliteHelper = new SqliteHelper(context);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infleter = LayoutInflater.from(context);
        View view = infleter.inflate(R.layout.row,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewActivity.class);
                i.putExtra("viewUpdate", true);
                i.putExtra("name", event.getName());
                i.putExtra("email", event.getEmail());
                i.putExtra("mobile", event.getMobile());
                i.putExtra("gender", event.getGender());
                context.startActivity(i);
            }
        });
       holder.ic_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onclicklistener.OnDeleteClicked(event ,position ,eventList);

           }
       });
        holder.person_name.setText(event.getName());
        holder.person_email.setText(event.getEmail());
        holder.person_mobilen.setText(event.getMobile());
        holder.person_gender.setText(event.getGender());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView person_name, person_email, person_mobilen, person_gender;
        private ImageView ic_delete ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            person_name = itemView.findViewById(R.id.person_name);
            person_email = itemView.findViewById(R.id.person_email);
            person_mobilen = itemView.findViewById(R.id.person_mobilen);
            person_gender = itemView.findViewById(R.id.person_gender);
            ic_delete = itemView.findViewById(R.id.ic_delete);
        }
    }
}