package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding
    var adapter = Adapter(Click { asteroid ->
        asteroid.let {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adapter = Adapter(Click { asteroid ->
//            asteroid.let {
//                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
//            }
//        })

        viewModel.Refresh()
        viewModel.pic.observe(viewLifecycleOwner) {
            viewModel.getCashed(it)
        }
        viewModel.asteroids.observe(viewLifecycleOwner) {
            adapter.aster = it
        }
        binding.apply {
            asteroidRecycler.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_today_menu -> viewModel.dailyAsteroid.observe(viewLifecycleOwner) {
                adapter.aster = it
            }
            R.id.show_week_menu -> viewModel.weeklyAsteroid.observe(viewLifecycleOwner) {
                adapter.aster = it
            }
            R.id.show_all_menu -> viewModel.asteroids.observe(viewLifecycleOwner) {
                adapter.aster = it
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
