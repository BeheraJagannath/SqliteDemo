package com.example.squlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reddit.indicatorfastscroll.FastScrollItemIndicator;
import com.reddit.indicatorfastscroll.FastScrollerThumbView;
import com.reddit.indicatorfastscroll.FastScrollerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements Onclicklistener {
    RecyclerView recyclerView ;
    ArrayList<Event> arrayList;
    CustomAdapter customAdapter;
    SqliteHelper sqliteHelper;
    RelativeLayout no_data ,rec1 ;
    FloatingActionButton floatingActionButton ;
    FastScrollerView fastScrollerView ;
    FastScrollerThumbView fastScrollerThumbView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        no_data = findViewById(R.id.no_data);
        floatingActionButton = findViewById(R.id.add);
        rec1 = findViewById(R.id.rec1);
        fastScrollerView = findViewById(R.id.contacts_letter_fastscroller);
        fastScrollerThumbView = findViewById(R.id.fastscroller_thumb);

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(1)
                .monitor();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        AppRate.showRateDialogIfMeetsConditions(this);

        sqliteHelper = new SqliteHelper(this);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewActivity.class));
            }
        });
        setupLetterFastscroller();


    }

    private void setupLetterFastscroller() {
        fastScrollerThumbView.setupWithFastScroller(fastScrollerView);
        fastScrollerView.setupWithRecyclerView(
                recyclerView,
                (position) -> {
                    Event item = arrayList.get(position);
                    return new FastScrollItemIndicator.Text(
                            item.getName().substring(0, 1).toUpperCase()
                    );
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        setrecyclerview();
    }

    private void setrecyclerview() {
        arrayList = sqliteHelper.readCourses();
        if (arrayList.size() != 0) {
            customAdapter = new CustomAdapter(this, arrayList,this);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            customAdapter.notifyDataSetChanged();
            no_data.setVisibility(View.GONE);
            rec1.setVisibility(View.VISIBLE);
            Collections.sort(arrayList, new Comparator<Event>() {
                @Override
                public int compare(Event event, Event t1) {
                    String a = event.getName() ;
                    String b = t1.getName() ;
                    return a.compareToIgnoreCase(b) ;

                }
            });
        } else {
            no_data.setVisibility(View.VISIBLE);
            Log.d("size", String.valueOf(arrayList.size()));
            rec1.setVisibility(View.GONE);

        }

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(arrayList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    @Override
    public void OnDeleteClicked( Event event, int position,ArrayList<Event> eventList) {
        sqliteHelper.deleteCourse( event.getName());
        customAdapter.notifyDataSetChanged();
        customAdapter.notifyItemRemoved(position);
        arrayList.remove(position);
        if (eventList.size()==0){
            no_data.setVisibility(View.VISIBLE);
            rec1.setVisibility(View.GONE);
        }else {
            no_data.setVisibility(View.GONE);
            rec1.setVisibility(View.VISIBLE);

        }
        Toast.makeText(MainActivity.this, "Deleted the course", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
       finishAffinity();
    }
}