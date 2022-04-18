package com.bozdi.vapehubbeta.managerFragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bozdi.vapehubbeta.GoodsListDialog
import com.bozdi.vapehubbeta.R

class OrderNew : Fragment() {

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = context?.let { Dialog(it) }
        dialog?.setTitle("Пидр")
        dialog?.setContentView(R.layout.fragment_goods_list_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val res = inflater.inflate(R.layout.fragment_order_new, container, false)

        res.findViewById<Button>(R.id.addGoodButton).setOnClickListener {
            dialog?.show();
        }
        return res
    }


}