package com.gulsahsuluk.chocochallange.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat
import java.text.NumberFormat

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun <T> unsynchronizedLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun Any?.runIfNull(block: () -> Unit) {
    if (this == null) block()
}

fun Long.applyThousandsSeparator(separator: Char): String {
    val formatter = NumberFormat.getInstance() as DecimalFormat
    val symbols = formatter.decimalFormatSymbols.apply {
        groupingSeparator = separator
    }
    formatter.decimalFormatSymbols = symbols
    return formatter.format(this)
}