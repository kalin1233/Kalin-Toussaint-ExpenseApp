package com.bignerdranch.android.tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Show the initial fragment (e.g., ExpenseListFragment)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExpenseListFragment())
                .commit()
        }


        val editButton: Button = findViewById(R.id.editButton)
        editButton.setOnClickListener {
            navigateToEditExpenseFragment()
        }


        val listButton: Button = findViewById(R.id.listButton)
        listButton.setOnClickListener {
            navigateToExpenseListFragment()
        }
    }

    // Method to navigate to EditExpenseFragment
    private fun navigateToEditExpenseFragment() {
        val fragment = EditExpenseFragment()
        navigateToFragment(fragment)
    }

    // Method to navigate to ExpenseListFragment
    private fun navigateToExpenseListFragment() {
        val fragment = ExpenseListFragment()
        navigateToFragment(fragment)
    }

    // Method to navigate to another fragment
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
