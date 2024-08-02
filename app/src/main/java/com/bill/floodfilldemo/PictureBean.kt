package com.bill.floodfilldemo

/**
 * Created by Swifty.Wang on 2015/8/12.
 */
class PictureBean {
    var pictures: List<Picture>? = null

    class Picture {
        var id: Int = 0
        var status: Int = 0
        var wvHradio: Float = 0f
        var uri: String? = null //just for sercret garden

        constructor(id: Int, status: Int, wvHradio: Float) {
            this.status = status
            this.id = id
            this.wvHradio = wvHradio
        }

        constructor(uri: String?) {
            this.uri = uri
        }
    }
}
