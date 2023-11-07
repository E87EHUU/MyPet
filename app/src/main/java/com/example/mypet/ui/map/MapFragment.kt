package com.example.mypet.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mypet.app.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory

class MapFragment : Fragment() {

    private var _ui: FragmentMapBinding? = null
    private val ui get() = _ui!!

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _ui = FragmentMapBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        ui.mapView.onStart()
    }

    override fun onStop() {
        ui.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }
}