package com.example.jobsearchapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView jobListView;
    private JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        jobListView = findViewById(R.id.jobListView);

        ArrayList<Job> jobs = new ArrayList<>();
        jobAdapter = new JobAdapter(this, jobs);
        jobListView.setAdapter(jobAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                // Fetch jobs based on query (mock data for demonstration)
                ArrayList<Job> searchResults = fetchJobs(query);
                // Update ListView with search results
                jobAdapter.clear();
                jobAdapter.addAll(searchResults);
            }
        });

        // ListView item click listener (example)
        jobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectedJob = (Job) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Clicked: " + selectedJob.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Dummy method to fetch jobs (replace with actual API call)
    private ArrayList<Job> fetchJobs(String query) {
        ArrayList<Job> results = new ArrayList<>();
        // Mock data
        results.add(new Job("Android Developer", "ABC Company"));
        results.add(new Job("Web Developer", "XYZ Inc."));
        results.add(new Job("Data Scientist", "123 Corp."));
        results.add(new Job("Software Developer", "VY company."));
        results.add(new Job("iOS Developer", "YSV Tech"));
        results.add(new Job("Web Developer", "123 Web Solutions"));
        results.add(new Job("Software Engineer", "Tech Innovations Inc."));
        results.add(new Job("Data Scientist", "Data Crunchers"));
        results.add(new Job("Network Engineer", "Connectivity Solutions"));
        results.add(new Job("UX/UI Designer", "Pixel Perfect Designs"));
        results.add(new Job("Product Manager", "Visionary Products"));
        results.add(new Job("Project Manager", "Agile Enterprises"));
        results.add(new Job("Business Analyst", "Insightful Analytics"));
        results.add(new Job("Digital Marketer", "Online Presence Ltd."));
        results.add(new Job("Content Writer", "Creative Wordsmiths"));
        results.add(new Job("Graphic Designer", "Artistic Creations Inc."));
        results.add(new Job("Financial Analyst", "Capital Insights Group"));
        results.add(new Job("Human Resources Manager", "Talent First Solutions"));
        results.add(new Job("Sales Manager", "Top Tier Sales"));
        results.add(new Job("Operations Manager", "Efficiency Experts Inc."));
        results.add(new Job("Mechanical Engineer", "Precision Engineering Works"));
        results.add(new Job("Civil Engineer", "Urban Infrastructure Solutions"));
        results.add(new Job("Electrical Engineer", "Power Grid Technologies"));
        results.add(new Job("Architect", "Design Build Architects"));
        results.add(new Job("Teacher", "Education Empowerment Institute"));
        results.add(new Job("Nurse", "Care Connect Hospitals"));
        results.add(new Job("Doctor", "Healthcare Excellence Clinic"));
        results.add(new Job("Pharmacist", "Medicine World Pharmacy"));
        results.add(new Job("Dentist", "Smile Bright Dental Care"));
        results.add(new Job("Lawyer", "Legal Eagles Firm"));
        results.add(new Job("Legal Assistant", "Legal Support Services"));
        results.add(new Job("Paralegal", "Legal Aid Partners"));
        results.add(new Job("Judge", "Judicial Excellence Court"));
        results.add(new Job("Legal Secretary", "Law Firm Solutions"));

        return results;
    }
}