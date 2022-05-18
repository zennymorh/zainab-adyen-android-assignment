package com.adyen.android.assignment.ui.places

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.R
import com.adyen.android.assignment.api.model.Result

class PlacesListAdapter(private var placesList: List<Result>) :
    RecyclerView.Adapter<PlacesListAdapter.PlacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = placesList.size

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val result: Result = placesList[position]
        holder.bind(result)
    }

    fun updateList(list: List<Result>) {
        placesList = list
        notifyDataSetChanged()
    }

    class PlacesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.place_item, parent, false)
        ){

        private val nameText: TextView = itemView.findViewById(R.id.location_name_text)
        private val addressText: TextView = itemView.findViewById(R.id.address_text)
        private val timeZoneText: TextView = itemView.findViewById(R.id.timezone_text)

        fun bind(result: Result) {
            nameText.text = result.name
            addressText.text = result.location?.address
            timeZoneText.text = result.timezone
        }

    }
}