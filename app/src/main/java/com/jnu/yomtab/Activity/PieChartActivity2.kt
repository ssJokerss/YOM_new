package com.jnu.yomtab.Activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jnu.yomtab.R
import com.jnu.yomtab.data.PieBean
import com.jnu.yomtab.fragment.downFragment.arrayList_person
import com.jnu.yomtab.view.PieView
import java.util.*

class PieChartActivity2 : AppCompatActivity(), PieView.ClickListener {
    override fun onArcClick(i: Int) {

    }

    override fun onCenterClick() {
        Toast.makeText(applicationContext, "圆被点击", Toast.LENGTH_SHORT).show()
    }

    private var pieView:PieView?=null

    private val color = intArrayOf(R.color.a, R.color.b, R.color.c, R.color.d, R.color.e, R.color.f)
    private var Dates: ArrayList<PieBean>? = null
    private var colors: ArrayList<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        var k =0
        for (i in arrayList_person.indices) {
            k++
        }
        val value = arrayOfNulls<Int>(k)
        val name = arrayOfNulls<String>(k)

        for (i in arrayList_person.indices) {
            value[i]= arrayList_person[i].money.toInt();
            name[i]= arrayList_person[i].date;
        }

        Dates = ArrayList<PieBean>()
        colors = ArrayList<Int>()

        pieView = findViewById(R.id.pieView)
        //设置文字颜色 默认白色
        pieView?.setTextColor(Color.WHITE)
        //设置中间圆大小 0不显示中间圆 1到10中间圆逐渐减小
        pieView?.setCenterCir(2)
        //设置中间文字,在中间圆半径pieView.setCenterCir值1-5中间时才会显示
        pieView?.setCenterText(resources.getString(R.string.percent2))
        //设置中间文字颜色 默认黑色
        pieView?.setCenterTextColor(Color.RED)
        //是否显示百分比文字 默认true
        pieView?.setPercentageTextShow(true)
        //是否显示动画 默认true
        pieView?.setShowAnimation(true)
        //是否绘制分割线 默认true
        pieView?.setDrawLine(true)
        //设置分割线颜色默认白色
        pieView?.setLineColor(Color.WHITE)
        //是否绘制中心阴影 默认true
        pieView?.isShadow(true)
        //点击是否自动旋转到底部 默认true
        pieView?.setTouchStart(true)
        //点击是否切割扇形 默认true
        pieView?.setTouchCarve(true)
        //设置事件监听
        pieView?.setListener(this)

        for (i in value.indices) {
            colors?.add(color[i])
            val pieBean = PieBean()
            pieBean.name = name[i]
            pieBean.value = value[i]!!.toFloat()
            Dates?.add(pieBean)
        }
        //设置所有颜色
        pieView?.setmColors(colors)
        //设置数据
        pieView?.setData(Dates)



    }


}