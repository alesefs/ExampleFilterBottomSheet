package com.example.searchandbottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

class FragmentPage1 : Fragment(), TagListener {

    private var cardList = listOf<CardModel>()
    private var cardFilteredList = mutableListOf<CardModel>()

    private var listFilteredByTag: MutableList<String> = mutableListOf()
    private var listFilteredByAction: MutableList<String> = mutableListOf()

    private lateinit var cardAdapter: CardAdapter

    private lateinit var listener: TagListener

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheet: ConstraintLayout
    private lateinit var btnBottomSheetPersistent: AppCompatButton

    private lateinit var bottomSheetFragment : BottomSheetDialogFragment

    companion object {
        private const val ALL = "all"
        private const val NO_DATA = "No Data Found"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_fragment_page, container, false)

        bottomSheet = view.findViewById(R.id.bottom_sheet_view)
        btnBottomSheetPersistent = view.findViewById(R.id.btn_bottom_sheet_persistent)

        cardList = cardListView()

        setupCardView(view)

        setupBottomSheet()

//        setupTagView(view)

        return view
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(context, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(context, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(context, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(context, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(context, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
//                    else -> Toast.makeText(context, "OTHER_STATE", Toast.LENGTH_SHORT).show()
//                }
            }
        })

        /*btnBottomSheetPersistent.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }*/

        /*
        childFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner
        ) { key, bundle ->
            val result = bundle.getString("bundleKey")
            print("AQUI: $result")

        }*/

        btnBottomSheetPersistent.setOnClickListener {
            bottomSheetFragment = CustomBottomSheetDialogFragment.newInstance(listener)
            bottomSheetFragment.show(parentFragmentManager, CustomBottomSheetDialogFragment.TAG)
        }
    }

    /*private fun setupTagView(view: View) {
        var tagList = tagListView()

        view.findViewById<RecyclerView>(R.id.rv_tag_items).run {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            adapter = TagAdapter(tagList) { tag ->
                Toast.makeText(context, tag.name, Toast.LENGTH_SHORT).show()

                listFilteredByTag = tag.name

                listener.chooseToTag(listFilteredByTag)
                updateCardList(listFilteredByTag)
            }
        }
    }*/

    private fun updateCardList(
        tagList: MutableList<String> = mutableListOf(),
        actionList: MutableList<String> = mutableListOf()
    ) {
        cardFilteredList = mutableListOf()

        val hasTag = tagList.isEmpty().not()
        val hasAction = actionList.isEmpty().not()

        if (hasTag && hasAction) {
            cardList.forEach { card ->
                tagList.forEach { tag ->
                    actionList.forEach { action ->
                        if (card.tag.lowercase(Locale.getDefault()) == tag.lowercase(Locale.getDefault()) &&
                            card.action.lowercase(Locale.getDefault()) == action.lowercase(Locale.getDefault())) {
                            cardFilteredList.add(card)
                        }
                    }
                }
            }

        } else if (hasTag && hasAction.not()) {
            cardList.forEach { card ->
                tagList.forEach { tag ->
                    if (card.tag.lowercase(Locale.getDefault()) == tag.lowercase(Locale.getDefault())) {
                        cardFilteredList.add(card)
                    }
                }
            }

        } else if (hasTag.not() && hasAction) {
            cardList.forEach { card ->
                actionList.forEach { action ->
                    if (card.action.lowercase(Locale.getDefault()) == action.lowercase(Locale.getDefault())) {
                        cardFilteredList.add(card)
                    }
                }
            }

        } else {
            cardList.forEach { card ->
                cardFilteredList.add(card)
            }
        }
        
        if (cardFilteredList.isEmpty()) {
            Toast.makeText(context, NO_DATA, Toast.LENGTH_SHORT).show()
            //setSnackbarWarning()
        } else {
            cardAdapter.setFilteredList(cardFilteredList)
        }
    }

    private fun setSnackbarWarning() {
        view?.let { Snackbar.make(it, NO_DATA, Snackbar.LENGTH_SHORT) }?.show()
    }

    private fun setupCardView(view: View) {

        cardAdapter = CardAdapter(cardList) { card ->
            Toast.makeText(context, card.title, Toast.LENGTH_SHORT).show()
        }

        view.findViewById<RecyclerView>(R.id.rv_card_items).run {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = cardAdapter
        }
    }

    private fun cardListView() = listOf(
        CardModel(R.drawable.ic_launcher_foreground, "title0", "body", "futebol", "small"),
        CardModel(R.drawable.ic_launcher_foreground, "title1", "body", "volei", "xsmall"),
        CardModel(R.drawable.ic_launcher_foreground, "title2", "body", "basquete", "small"),
        CardModel(R.drawable.ic_launcher_foreground, "title3", "body", "futebol", "medium"),
        CardModel(R.drawable.ic_launcher_foreground, "title4", "body", "volei", "large"),
        CardModel(R.drawable.ic_launcher_foreground, "title5", "body", "basquete", "xlarge"),
        CardModel(R.drawable.ic_launcher_foreground, "title6", "body", "futebol", "ultra"),
        CardModel(R.drawable.ic_launcher_foreground, "title7", "body", "volei", "small"),
        CardModel(R.drawable.ic_launcher_foreground, "title8", "body", "basquete", "medium"),
        CardModel(R.drawable.ic_launcher_foreground, "title9", "body", "basquete", "large")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun chooseToTag(item: MutableList<String>) {
        if (item.isNotEmpty()) {
            listFilteredByTag = item
            updateCardList(listFilteredByTag, listFilteredByAction)
        }
    }

    override fun chooseToAction(item: MutableList<String>) {
        if (item.isNotEmpty()) {
            listFilteredByAction = item
            updateCardList(listFilteredByTag, listFilteredByAction)
        }
    }

}
