package com.example.osamah;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class picechart extends Fragment {
    View view;
    private static String TAG = "MainActivity";

    private float[] yData ;
    private String[] xData = {"Activites", "Triggers" , "Locations" };
    PieChart pieChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public picechart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment picechart.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_picechart2, container, false);

        pieChart = (PieChart) view.findViewById(R.id.idPieChart);
        Button button = view.findViewById(R.id.triggersData);
        Button button2 = view.findViewById(R.id.Activity);
        Button button3 = view.findViewById(R.id.Location);
        FirebaseFirestore.getInstance().collection("triggers")
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String obj = task.getResult().getData().get("mTriggers").toString();

                    try {
                        JSONObject  jsonObj = new JSONObject(obj);
                        String name = jsonObj.getString("Home");
                        Log.d(TAG, "onComplete: sleep"+name);
                    } catch (JSONException e) {
                        Log.d(TAG, "onComplete: "+e.getMessage().toString());
                        e.printStackTrace();
                    }



                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int i1 = r.nextInt(80 - 70) + 89;
                int i2= r.nextInt(90 - 80) + 40;
                yData = new float[]{i1, 10.6f, i2};
                addDataSet();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int i1 = r.nextInt(80 - 65) + 100;
                yData = new float[]{3.3f, i1, 6.76f};
                addDataSet();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int i1 = r.nextInt(80 - 65) + 65;
                int i2= r.nextInt(80 - 56) + 40;
                yData = new float[]{i1, i2, 67.76f};
                addDataSet();
            }
        });


        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Average");
        pieChart.setCenterTextSize(10);


        return view;
    }
    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Numbers of attacks");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);


        pieDataSet.setColors(colors);



        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}