package rs.pglisovic.jwplayerdemoandroidapp.extensions

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.showAlert(title: CharSequence, message: CharSequence) {
    val builder = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Ok", { dialog, which ->
        })

    val dialog: AlertDialog = builder.create()
    dialog.show()
}