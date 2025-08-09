# Job-Search-Application
Job Search Mobile Application using Android Studio, Firebase, and Java, enables users to register, log in, and search for jobs seamlessly. The app features user authentication, and, real-time database integration for an enhanced job-hunting experience.

activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome to Job Search Portal"
        android:textSize="24sp"
        android:padding="16dp" />

    <Button
        android:id="@+id/loginButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Login"
        android:layout_marginTop="16dp" />

    <Button
        android:id="@+id/registerButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Register"
        android:layout_marginTop="16dp" />
</LinearLayout>

MainActivity.java

package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, redirect to JobSearchActivity
            Intent intent = new Intent(MainActivity.this, JobSearchActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        }

        // Navigate to LoginActivity
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Navigate to RegisterActivity
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

activity_register.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
android:padding="16dp">

<EditText
    android:id="@+id/usernameEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Username" />

<EditText
    android:id="@+id/emailEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Email"
    android:inputType="textEmailAddress" />

<EditText
    android:id="@+id/passwordEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Password"
    android:inputType="textPassword" />

<Button
    android:id="@+id/registerButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Register" />
</LinearLayout>

RegisterActivity.java

package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = databaseReference.push().getKey();
        User user = new User(username, email, password);
        if (userId != null) {
            databaseReference.child(userId).setValue(user);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, JobSearchActivity.class)); // Navigate to job search activity
            finish(); // Close the registration activity
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    static class User {
        public String username;
        public String email;
        public String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }
}

activity_login.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
android:padding="16dp">

<EditText
    android:id="@+id/emailEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Email"
    android:inputType="textEmailAddress" />

<EditText
    android:id="@+id/passwordEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Password"
    android:inputType="textPassword" />

<Button
    android:id="@+id/loginButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Login" />
</LinearLayout>

LoginActivity.java

package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dbEmail = userSnapshot.child("email").getValue(String.class);
                    String dbPassword = userSnapshot.child("password").getValue(String.class);
                    if (dbEmail != null && dbEmail.equals(email) && dbPassword != null && dbPassword.equals(password)) {
                        userExists = true;
                        break;
                    }
                }
                if (userExists) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, JobSearchActivity.class)); // Navigate to job search activity
                    finish(); // Close the login activity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

activity_job_search.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical"
android:padding="16dp">

<!-- Job Name EditText -->
<EditText
    android:id="@+id/jobNameEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter Job Name"
    android:inputType="text"
    android:padding="12dp"
    android:layout_marginBottom="12dp"/>

<!-- Search Button -->
<Button
    android:id="@+id/searchButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Search"
    android:padding="12dp"
    android:layout_marginBottom="12dp"/>

<!-- Jobs ListView -->
<ListView
    android:id="@+id/jobsListView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:divider="@android:color/darker_gray"
    android:dividerHeight="1dp"
    android:padding="12dp"/>

</LinearLayout>


AndroidManifest.xml

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Job_Portal"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- LoginActivity -->
        <activity android:name=".LoginActivity" />

        <!-- RegisterActivity -->
        <activity android:name=".RegisterActivity" />

        <!-- JobSearchActivity -->
        <activity android:name=".JobSearchActivity" />

    </application>

</manifest>

JobSearchActivity

package com.example.job_portal;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class JobSearchActivity extends AppCompatActivity {

    private EditText jobNameEditText;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);

        jobNameEditText = findViewById(R.id.jobNameEditText);
        Button searchButton = findViewById(R.id.searchButton);
        ListView jobsListView = findViewById(R.id.jobsListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("jobs");
        jobList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        jobsListView.setAdapter(adapter);

        searchButton.setOnClickListener(v -> searchJobs());
    }

    private void searchJobs() {
        String jobName = jobNameEditText.getText().toString().trim();

        if (jobName.isEmpty()) {
            Toast.makeText(this, "Please enter a job name", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobList.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    String jobTitle = jobSnapshot.child("title").getValue(String.class);
                    String company = jobSnapshot.child("company").getValue(String.class);
                    String location = jobSnapshot.child("location").getValue(String.class);

                    if (jobTitle != null && jobTitle.contains(jobName)) {
                        jobList.add(jobTitle + " at " + company + " in " + location);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JobSearchActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


Firebase URL: https://job-portal-99bee-default-rtdb.firebaseio.com/  



