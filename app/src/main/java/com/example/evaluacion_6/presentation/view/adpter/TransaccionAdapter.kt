package com.example.evaluacion_6.presentation.view.adpter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion_6.R
import com.example.evaluacion_6.data.remote.response.Transaccion.Data
import java.util.Locale

class TransaccionAdapter() : ListAdapter<Data, TransaccionAdapter.TransaccionViewHolder>(TransaccionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaccionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_transaccion, parent, false)
        return TransaccionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransaccionViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class TransaccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val transaccionIdView: TextView = itemView.findViewById(R.id.txtViewItemTransaccionId)
        private val accountIdView: TextView = itemView.findViewById(R.id.txtViewItemAccountId)
        private val toAccountIdView: TextView = itemView.findViewById(R.id.txtViewItemToAccountId)
        private val amountView: TextView = itemView.findViewById(R.id.txtViewItemAmount)
        private val dateView: TextView = itemView.findViewById(R.id.txtViewItemDate)
        private val userIdView: TextView = itemView.findViewById(R.id.txtViewItemUserId)

        fun bind(data: Data) {

            transaccionIdView.text = "Transaccion ID: ${data.id}"
            accountIdView.text = "Account ID: ${data.accountId}"
            toAccountIdView.text = "To Account ID: ${data.to_account_id}"
            amountView.text = "Amount: ${data.amount}"
            userIdView.text = "User ID: ${data.userId}"


            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM d, hh:mm a", Locale.getDefault())

            try {
                val parsedDate = inputFormat.parse(data.date)
                val formattedDate = outputFormat.format(parsedDate)
                dateView.text = "Date: $formattedDate"
            } catch (e: Exception) {
                dateView.text = "Date: ${data.date}"
                e.printStackTrace()
            }
        }
    }

    private class TransaccionDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}

