package com.techypie.myyoutubeapp.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.techypie.myyoutubeapp.AdapterVideo
import com.techypie.myyoutubeapp.DatabasaHelper
import com.techypie.myyoutubeapp.Video
import com.techypie.myyoutubeapp.databinding.FragmentVideoBinding

class FragmentVideo() : Fragment() {

    lateinit var binding : FragmentVideoBinding
    lateinit var databasaHelper: DatabasaHelper
   lateinit var adapterVideo : AdapterVideo
    lateinit var videoList : MutableList<Video>

    lateinit var filterList : MutableList<Video>
    lateinit var searchList : MutableList<Video>
    var isSearchFound = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentVideoBinding.inflate(layoutInflater)
        databasaHelper = DatabasaHelper(context)
        videoList = ArrayList()
        filterList = ArrayList()
        searchList = ArrayList()


        addFilter()
        searchVideo()
        return binding.root
    }

    fun searchVideo()
    {
        binding.searchET.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                searchList.clear()
                var searchText = binding.searchET.text.toString()


                if (searchText.length==0)
                {
                    showRecyclerView()
                    binding.noDatafoundTV.visibility = View.GONE
                    return
                }


                for (video in videoList)
                {
                    if (video.title.contains(searchText,true)){
                        searchList.add(video)
                        isSearchFound = true
                    }
                }

                if (isSearchFound){
                    binding.noDatafoundTV.visibility = View.GONE
                }
                else{
                    binding.noDatafoundTV.visibility = View.VISIBLE
                }

                binding.recyclerView.adapter = null
                adapterVideo = AdapterVideo(context,searchList)
                binding.recyclerView.adapter = adapterVideo

                isSearchFound = false
            }

        })
    }

    fun showRecyclerView(){
        videoList.clear()
        binding.recyclerView.adapter = null
        videoList = databasaHelper.getAllVideo()
         adapterVideo = AdapterVideo(context,videoList)
        binding.recyclerView.adapter = adapterVideo
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        showRecyclerView()
        binding.filterCategorySP.setSelection(0)
    }

    fun addFilter()
    {

        binding.filterCategorySP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                filterList.clear()
                var selectedItem = binding.filterCategorySP.selectedItem.toString()

                if (binding.filterCategorySP.selectedItemPosition == 0){
                    showRecyclerView()
                    return
                }

                if (selectedItem == "Education"){
                    filterList = databasaHelper.getEducationVideo()
                }
                else if(selectedItem == "Entertainment"){
                    filterList = databasaHelper.getEntertainmentVideo()
                }
                else if(selectedItem == "Gaming"){
                    filterList = databasaHelper.getGamingVideo()
                }
                else if(selectedItem == "Sports"){
                    filterList = databasaHelper.getSportsVideo()
                }
                else{
                    filterList = databasaHelper.getNewsVideo()
                }

                binding.recyclerView.adapter = null
                adapterVideo = AdapterVideo(context,filterList)
                binding.recyclerView.adapter = adapterVideo

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }


}