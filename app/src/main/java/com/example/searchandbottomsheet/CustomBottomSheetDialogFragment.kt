package com.example.searchandbottomsheet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetDialogFragment(listener: TagListener) : BottomSheetDialogFragment() {

    private var mListener: TagListener? = null

    private var listFilteredByTag: MutableList<String> = mutableListOf()
    private var listFilteredByAction: MutableList<String> = mutableListOf()

    private lateinit var tagAdapter: TagAdapter
    private lateinit var actionAdapter: TagAdapter

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(listener: TagListener) = CustomBottomSheetDialogFragment(listener)
    }

    init {
        this.mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_bottom_sheet_view, container, false)

        setupBtnFilterView(view)
        setupTagView(view)
        setupActionView(view)

        return view
    }

    private fun setupBtnFilterView(view: View) {
        view.findViewById<AppCompatButton>(R.id.btn_bottom_sheet_filter).apply {
            setOnClickListener {
                mListener?.chooseToTag(listFilteredByTag)
                mListener?.chooseToAction(listFilteredByAction)
            }
        }

    }

    override fun onStart() {
        super.onStart()

        listFilteredByTag.clear()
        listFilteredByAction.clear()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as TagListener?
        } catch (e: java.lang.ClassCastException) {
            print(e)
        }
    }

    override fun onDetach() {
        super.onDetach()

        listFilteredByTag.clear()
        listFilteredByAction.clear()

        val pf: Fragment? = this.parentFragment
        pf?.onResume()
    }

    private fun setupTagView(view: View) {
        val tagList = tagListView()

        tagAdapter = TagAdapter(tagList) { tag ->
            if (tag.active) {
                listFilteredByTag.add(tag.name)
            } else {
                listFilteredByTag.remove(tag.name)
            }
        }

        view.findViewById<RecyclerView>(R.id.rv_tag_items).run {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.SPACE_AROUND
            }
            setHasFixedSize(true)
            adapter = tagAdapter
        }
    }

    private fun setupActionView(view: View) {
        val actionList = tagActionView()

        actionAdapter = TagAdapter(actionList) { tag ->
            if (tag.active) {
                listFilteredByAction.add(tag.name)
            } else {
                listFilteredByAction.remove(tag.name)
            }
        }

        view.findViewById<RecyclerView>(R.id.rv_action_items).run {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.SPACE_AROUND
            }
            setHasFixedSize(true)
            adapter = actionAdapter
        }
    }

    private fun tagListView() = mutableListOf(
        TagModel(0, "all"),
        TagModel(1, "xsmall"),
        TagModel(2, "small"),
        TagModel(3, "medium"),
        TagModel(4, "large"),
        TagModel(5, "xlarge"),
        TagModel(6, "ultra")
    )

    private fun tagActionView() = mutableListOf(
        TagModel(0, "all"),
        TagModel(1, "futebol"),
        TagModel(2, "volei"),
        TagModel(3, "basquete")
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
