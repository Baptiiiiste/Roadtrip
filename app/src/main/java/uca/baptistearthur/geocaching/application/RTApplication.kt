package uca.baptistearthur.geocaching.application

import android.app.Application
import uca.baptistearthur.geocaching.data.BDD

class RTApplication: Application() {

    val db: BDD by lazy { BDD.getInstance(this) }

}