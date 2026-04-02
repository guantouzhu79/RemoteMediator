package com.rip.remotemediator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rip.remotemediator.databinding.FragmentFirstBinding
import com.rip.remotemediator.AlbumAdapter
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels <AlbumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    // 收集数据提交adapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AlbumAdapter()
        binding.apply {
            albumRecyclerView.adapter = adapter
            val layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
            layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
//            albumRecyclerView.spanSizeLookup = object : StaggeredGridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return 1 // 每个Item占1列
//                }
//
//                override fun getSpanIndex(position: Int, spanCount: Int): Int {
//                    // 按位置锁死列：第0、2、4...个Item永远在左列，1、3、5...永远在右列
//                    return position % spanCount
//                }
//            }
            albumRecyclerView.layoutManager = layoutManager
            albumRecyclerView.addItemDecoration(StaggeredDividerItemDecoration(requireActivity(),5))

        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.albumData.collect { pagingData ->
                    adapter.submitData(pagingData)

                    }
                }
            }
        }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}