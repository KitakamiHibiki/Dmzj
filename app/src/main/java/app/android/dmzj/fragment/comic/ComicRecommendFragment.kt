package app.android.dmzj.fragment.comic

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.android.dmzj.R
import app.android.dmzj.activity.Main
import app.android.dmzj.service.ComicService
import app.android.dmzj.service.Service

class ComicRecommendFragment(activity: Main) : Fragment() {
    private val activity: Main
    private val handler: Handler
    val selfHandler: Handler
    var commend: String = ""
    val ERROR = 101
    val REQUEST_COMMEND_SUCSSCE=102
    lateinit var TT:TextView

    init {
        this.activity = activity
        this.handler = activity.handler
        this.selfHandler = Handler(Handler.Callback { message ->
            when (message.what){
                ERROR->{
                    Service.sendMessage(handler,activity.CHANGE_FRAGMENT_TO_ERROR,message.obj)
                }
                REQUEST_COMMEND_SUCSSCE->{
                    commend = message.obj.toString()
                    TT.text=commend
                }
            }
            return@Callback true
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comic_recommand, container, false)
        ComicService.getComicRecommend(this.selfHandler).start()
        TT = view.findViewById(R.id.TT)
        return view
    }
}