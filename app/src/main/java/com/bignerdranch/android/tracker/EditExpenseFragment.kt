package com.bignerdranch.android.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.tracker.databinding.FragmentEditExpenseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditExpenseFragment : Fragment() {

    private var _binding: FragmentEditExpenseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle save button click
        binding.btnSave.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        // Get input values
        val date = binding.etDate.text.toString()
        val amount = binding.etAmount.text.toString().toDouble()
        val category = binding.etCategory.text.toString().trim() // Trim whitespace
        val date1 = date.toLongOrNull() ?: 0L

        // Validate category
        val validCategories = listOf("Food", "Entertainment", "Housing", "Utilities", "Fuel", "Automotive", "Misc")
        if (category !in validCategories) {
            // Show toast indicating invalid category
            Toast.makeText(requireContext(), "Invalid category", Toast.LENGTH_SHORT).show()
            return
        }

        // Create Expense object
        val expense = Expense(date = date1, amount = amount, category = category)

        // Start a coroutine to perform the insert operation
        lifecycleScope.launch {
            val database = withContext(Dispatchers.IO) {
                ExpenseDatabase.getInstance(requireContext())
            }
            val expenseDao = database.expenseDao()

            // Insert the expense into the database
            withContext(Dispatchers.IO) {
                expenseDao.insertExpense(expense)
            }

            // Close fragment
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
