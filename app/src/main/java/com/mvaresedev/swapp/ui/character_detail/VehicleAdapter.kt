package com.mvaresedev.swapp.ui.character_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvaresedev.swapp.R
import com.mvaresedev.swapp.databinding.HolderVehicleBinding
import com.mvaresedev.swapp.domain.models.Vehicle

class VehicleAdapter : RecyclerView.Adapter<VehicleAdapter.VehicleVH>() {

    private val vehicles = ArrayList<Vehicle>()

    fun submitData(vehicles: List<Vehicle>) {
        this.vehicles.clear()
        this.vehicles.addAll(vehicles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleVH {
        val binding = HolderVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleVH(binding)
    }

    override fun onBindViewHolder(holder: VehicleVH, position: Int) {
        holder.bindData(vehicles[position])
    }

    override fun getItemCount() = vehicles.size

    class VehicleVH(private val binding: HolderVehicleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(vehicle: Vehicle) {
            val res = binding.root.resources
            binding.manufacturerTxt.text = vehicle.manufacturer
            binding.nameTxt.text =  vehicle.name
            binding.modelTxt.text = res.getString(R.string.vehicle_model_format, vehicle.model)
            binding.classTxt.text = res.getString(R.string.vehicle_class_format, vehicle.vehicleClass)
            binding.costTxt.text = res.getString(R.string.vehicle_cost_format, vehicle.cost)
            binding.lengthTxt.text = res.getString(R.string.vehicle_length_format, vehicle.length)
            binding.maxSpeedTxt.text = res.getString(R.string.vehicle_max_speed_format, vehicle.maxSpeed)
        }

    }
}