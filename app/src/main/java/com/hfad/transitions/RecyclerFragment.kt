package com.hfad.transitions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale

class RecyclerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Item 1", "Item 2", "Item 3")
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ItemAdapter(items) { imageView, title ->
            //val fragment = DetailFragment.newInstance(title)
            val fragment = DetailFragment.newInstance(imageView.transitionName)
            exitTransition = MaterialElevationScale(false).apply {
                duration = 600L
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = 600L
            }

            fragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = 600L
            }


            Log.d("transitionName", imageView.transitionName)
            parentFragmentManager.beginTransaction()
                //.addSharedElement(imageView, imageView.transitionName)

                .addSharedElement(imageView, imageView.transitionName)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}