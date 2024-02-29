package com.bignerdranch.android.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class ExpenseListFragment : Fragment() {

    // Define UI elements
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var dateEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var filterByDateButton: Button
    private lateinit var filterByCategoryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense, container, false)

        // Initialize UI elements
        recyclerView = view.findViewById(R.id.expenseRecyclerView)
        dateEditText = view.findViewById(R.id.dateEditText)
        categoryEditText = view.findViewById(R.id.categoryEditText)
        filterByDateButton = view.findViewById(R.id.filterByDateButton)
        filterByCategoryButton = view.findViewById(R.id.filterByCategoryButton)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ExpenseAdapter(listOf()) // Initialize with an empty list
        recyclerView.adapter = adapter

        // Fetch all expenses from the database and update the adapter
        fetchAllExpenses()

        // Set onClickListener for filterByDateButton
        filterByDateButton.setOnClickListener {
            filterExpensesByDate(dateEditText.text.toString())
        }

        // Set onClickListener for filterByCategoryButton
        filterByCategoryButton.setOnClickListener {
            filterExpensesByCategory(categoryEditText.text.toString())
        }

        return view
    }

    // Method to fetch all expenses from the database
    private fun fetchAllExpenses() {
        lifecycleScope.launch {
            val expenseDao = ExpenseDatabase.getInstance(requireContext()).expenseDao()
            expenseDao.getAllExpenses().collect { expenses ->
                adapter.updateExpenses(expenses)
            }
        }
    }

    private fun filterExpensesByDate(date: String) {
        lifecycleScope.launch {
            val expenseDao = ExpenseDatabase.getInstance(requireContext()).expenseDao()
            expenseDao.getExpensesByDate(date).collect { expenses ->
                adapter.updateExpenses(expenses)
            }
        }
    }

    private fun filterExpensesByCategory(category: String) {
        lifecycleScope.launch {
            val expenseDao = ExpenseDatabase.getInstance(requireContext()).expenseDao()
            expenseDao.getExpensesByCategory(category).collect { expenses ->
                adapter.updateExpenses(expenses)
            }
        }
    }
}


