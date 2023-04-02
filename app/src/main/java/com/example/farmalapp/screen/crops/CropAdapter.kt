package com.example.farmalapp.screen.crops

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.farmalapp.R
import com.example.farmalapp.databinding.CardCropBinding


class CropAdapter() : RecyclerView.Adapter<CropAdapter.CropHolder>() {

    private val list = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CropHolder {
        val cardLayout = CardCropBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CropHolder(cardLayout)
    }

    override fun onBindViewHolder(holder: CropHolder, position: Int) {
        val crop = list[position]
        holder.bind(crop)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CropHolder(private val cardCropBinding: CardCropBinding) :
        RecyclerView.ViewHolder(cardCropBinding.root) {
        fun bind(crop: String) {
            cardCropBinding.crop = crop
            cardCropBinding.executePendingBindings()

            if (crop == "Apple") {
                cardCropBinding.imageView2.setImageResource(R.drawable.apple)
            }else if (crop=="Pear"){
                cardCropBinding.imageView2.setImageResource(R.drawable.armud)
            }else if (crop == "Peach"){
                cardCropBinding.imageView2.setImageResource(R.drawable.peach)
            }else if (crop=="Corn"){
                cardCropBinding.imageView2.setImageResource(R.drawable.corn)
            }
        }
    }

    fun updateList(newList: List<String>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

}