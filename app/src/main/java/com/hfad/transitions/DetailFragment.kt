package com.hfad.transitions

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.transition.MaterialContainerTransform

class DetailFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "extra_title"

        fun newInstance(title: String): DetailFragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 600L
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = requireArguments()
        val image = args.getString(ARG_TITLE)!!
        Toast.makeText(requireContext(), "$image", Toast.LENGTH_SHORT).show()
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.transitionName = image

        imageView.setImageResource(R.drawable.favorite_fill_24px)

        postponeEnterTransition()
        imageView.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }
    }
}