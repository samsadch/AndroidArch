package com.samsad.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.samsad.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )
        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        //Check the activity intent resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            //hide menu item if not resolves
            menu?.findItem(R.id.share)?.setVisible(false)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> share()
        }
        return /*NavigationUI.onNavDestinationSelected(
            item,
            view!!.findNavController()
        ) ||*/ super.onOptionsItemSelected(item)

    }

    fun getShareIntent(): Intent {
        var args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        /*val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions)
        )*/
        return /*shareIntent*/ShareCompat.IntentBuilder.from(activity!!)
            .setText(getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions))
            .setType("text/plain")
            .intent


    }

    fun share() {
        startActivity(getShareIntent())
    }
}
