package com.learning.toukan.fragments

import android.media.MediaPlayer
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.RequestManager
import com.learning.toukan.Constants
import com.learning.toukan.R
import com.learning.toukan.models.Sticker
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_store.view.*
import kotlinx.android.synthetic.main.sticker_item.*
import kotlinx.android.synthetic.main.sticker_item.view.*
import org.w3c.dom.Text

class StickerAdapter(val glide: RequestManager, val onStickerClick: (Sticker, Int) -> Unit) : ListAdapter<Sticker, StickerAdapter.StickerViewHolder>(StickerDiffCallback()) {

    var selectedPosition: Int = -1
    var mediaPlayer: MediaPlayer? = null

    inner class StickerViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(
            item: Sticker,
            selected: Boolean,
            glide: RequestManager,
            onStickerClick: (Sticker, Int) -> Unit
        ) = with(itemView) {

            val circularProgressDrawable: CircularProgressDrawable =
                CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            glide.load(item.stickerImage)
                .override(500, 500)
                .fitCenter()
                .placeholder(circularProgressDrawable)
                .into(sticker_image);

//            if (item.category != null) {
//                store_title.text = item.category
//            }
            sticker_name.text = item.pinyin
            sticker_price.text = context.getString(R.string.sticker_cost, item.cost.toString())
            if (sticker_name.text.length > 11) { //"Gōng gòng qì chē"
                sticker_name.setTextSize(13f)
            } else
                sticker_name.setTextSize(18f)

//            if(item != null) {
//                item_selected_english.text = item.english
//            }
//
//            if (selected){
//                sticker_image.setImageBitmap()
//            }


            setOnClickListener {

                val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
                onStickerClick(item, adapterPosition)

                if (mediaPlayer != null){
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(context, Constants.getSoundForId(item.key))
                mediaPlayer?.setOnPreparedListener {
                    it.start()

                }
                sticker_image.startAnimation(shake)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticker_item, parent, false)
        return StickerViewHolder(view)
    }
    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        holder.bind(getItem(position), (position == selectedPosition), glide, onStickerClick)
    }
}
class StickerDiffCallback : DiffUtil.ItemCallback<Sticker>() {
    override fun areItemsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
       return oldItem.stickerId == newItem.stickerId
    }

    override fun areContentsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
       return oldItem == newItem
    }
}