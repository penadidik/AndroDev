package di2k.csi.androloginv15k.main

import android.accounts.AccountManager.get
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SessionActivity {
    val LOGGED_IN_PREF = "logged_in_status"

    internal fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(LOGGED_IN_PREF, loggedIn)
        editor.apply()
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    fun getLoggedStatus(context: Context): Boolean {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false)
    }

    fun getValueString(LOGGED_IN_PREF: String): String? {
        return
    }
}
