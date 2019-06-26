package di2k.csi.androloginv15k.model

import com.google.gson.annotations.SerializedName

class CSignUp(@field:SerializedName("error")
                val error: Boolean, @field:SerializedName("message")
                val message: String, @field:SerializedName("user")
                val user: User)