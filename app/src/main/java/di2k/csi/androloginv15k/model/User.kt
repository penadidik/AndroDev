package di2k.csi.androloginv15k.model

class User {
    val id: Int = 0
    var name: String? = null
        private set
    var email: String? = null
        private set
    var password: String = ""
        get() {
            TODO()
        }

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(anInt: Int, name: String?, email: String?) {
        this.name = name
        this.email = email
    }
}