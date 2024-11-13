package com.example.insta.models

class User {
    var image:String?=null
    var name:String?=null
    var email:String?=null
    var password:String?=null

    constructor()

    constructor(password: String?, image: String?, name: String?, email: String?) {
        this.password = password
        this.image = image
        this.name = name
        this.email = email
    }

    constructor(name: String?, email: String?, password: String?) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

}