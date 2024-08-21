package com.hfad.transitions

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)

        imageView.transitionName = image

        imageView.setImageResource(R.drawable.favorite_fill_24px)

        postponeEnterTransition()
        imageView.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        imageView.viewTreeObserver.addOnGlobalLayoutListener {
            if (ViewCompat.isLaidOut(fab)) {
                animateFab(fab)
            }
        }
    }

    private fun animateFab(fab: ExtendedFloatingActionButton) {
        // Параметры анимации для плавного появления FAB
        fab.alpha = 0f
        fab.scaleX = 0.8f
        fab.scaleY = 0.8f
        fab.visibility = View.VISIBLE

        fab.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setInterpolator(OvershootInterpolator())
            .setDuration(2000L)
            .start()
    }
}