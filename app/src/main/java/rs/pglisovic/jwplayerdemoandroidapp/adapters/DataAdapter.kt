package rs.pglisovic.jwplayerdemoandroidapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import rs.pglisovic.jwplayerdemoandroidapp.Feature
import rs.pglisovic.jwplayerdemoandroidapp.R

class DataAdapter(
    val context: Context,
    private var items: List<Feature>,
    var listener: DataAdapterListener) : RecyclerView.Adapter<DataAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_row, parent, false)

        val viewHolder = ItemViewHolder(view)

        viewHolder.view.setOnClickListener {
            val index = viewHolder.adapterPosition
            val item = items[index]
            if (!item.title.isNullOrEmpty()) {
                listener.onItemClicked(item)
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DataAdapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        if (!item.available) {
            (holder.view as CardView).setCardBackgroundColor(ContextCompat.getColor(context, R.color.redWithAlpha))
        } else if (item.available && item.hasNote) {
            (holder.view as CardView).setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellowWithAlpha))
        } else {
            holder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }


    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvJWFeature)
    }

    interface DataAdapterListener {
        fun onItemClicked(item: Feature)
    }
}